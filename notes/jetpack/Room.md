## 依赖

```groovy
apply plugin: 'kotlin-kapt'
implementation "androidx.room:room-runtime:2.3.0"
kapt "androidx.room:room-compiler:2.3.0"
```

## @Entity

> 用于定义封装实际数据的实体类，每个实体类都会在数据库中对应一张表，并且表中的列是根据实体类中的字段自动生成的。

常用注解

- @Entity 的常用属性
  - tableName 指定对呀的数据库表名
  - primaryKeys 指定主键
  - ignoredColunms 指定忽略的字段，父类的也可以
- @PrimaryKey 指定主键，autoGenerate设置为自增长
- @ColumnInfo 指定字段在数据库中的名称，name 名称 

- @Ignore 表示字段不用存入数据库

```kotlin
// User就是表名，可以单独指定表名，复合主键@Entity(primaryKeys = arrayOf("firstName", "lastName"))
@Entity(tableName = "User")
data class User(
    var firstName: String,
    var lastName: String,
    var age: Int
) {
    // 将id设置为自增长的主键，每个实体至少得有一个主键
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
```

数据必须是public的或者是提供javabean的方法



## @Dao

> 数据访问对象，通常会在这里对数据库的各项操作进行封装，逻辑层主要是和Dao打交道。

```kotlin
/**
 * 如果是作为实体对象来传入的参数，是不需要SQL语句
 * @Update 和 @Delete是根据数据id去执行操作的
 */
@Dao
interface UserDao {

    // 若插入的是多个，则数据为List<Long>
    @Insert
    fun insertUser(user: User): Long

    @Update
    fun updateUser(newUser: User)

    @Query("select * from User")
    fun loadAllUser(): List<User>

    @Query("select * from User where age > :age")
    fun loadUserOlderThan(age: Int): List<User>

    @Delete
    fun deleteUser(user: User)

    @Query("delete from User where lastName = :lastName")
    fun deleteUserByLastName(lastName: String): Int
}
```

## @Database 

> 数据库的关键信息，版本号、包含的实体类，以及对外提供Dao的实例。

```kotlin
@Database(version = 2, entities = [User::class, Book::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun bookDao(): BookDao

    companion object {
        // 数据库名
        private const val DATABASE_NAME = "app_database"
        private var instance: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """create table Book (
                    id integer primary key autoincrement not null,
                    name text not null,pages integer not null
                    )""".trimMargin()
                )
            }
        }

        fun getDatabase(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
//                .allowMainThreadQueries() 设置可以在主现场执行数据库操作
                .addMigrations(MIGRATION_1_2)
                .build()
                .also { instance = it }
        }
    }
}
```

## 参考

[使用 Room 将数据保存到本地数据库  | Android 开发者  | Android Developers (google.cn)](https://developer.android.google.cn/training/data-storage/room?hl=zh-cn)

