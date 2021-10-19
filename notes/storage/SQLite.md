## 1. 创建数据库

Android提供一个SQLiteOpenHelper抽象类来协助我们使用SQLite，使用时需要继承至SQLiteOpenHelper并实现其中的两个抽象方法onCreate()、onUpgrade()。SQLiteOpenHelper中还有两个非常重要的方法：getWritableDatabase()和getReadableDatabase()。这两个方法都可以创建或打开一个现有的数据库（有则打开，无则创建），并返回一个可对数据库进行读写操作的对象。不同的是当数据库不可写入时，getReadableDatabase()会以只读的形式打开，而getWritableDatabase()方法会出现异常。

数据类型

| 类型       | 字段    |
| ---------- | ------- |
| 整型       | integer |
| 浮点型     | real    |
| 文本类型   | text    |
| 二进制类型 | blob    |

```kotlin
/**
 * @param name 数据库名称
 * @param version 数据库版本
 */
class MyDatabaseHelper(
    context: Context,
    name: String,
    version: Int
) : SQLiteOpenHelper(context, name, null, version) {
    
    private val createBook = """
        create table Book ( id integer primary key autoincrement,
        author text, price real, pages integer, name text)
    """.trimIndent()

    // 若数据库不存在则触发
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createBook)
    }

    // 当数据库版本升级时触发
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}

// 使用
class SQLiteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySqliteBinding>(this, R.layout.activity_sqlite)
        val dbHelper = MyDatabaseHelper(this,"BookStore.db",1)
        binding.apply {
            btnCreateTB.setOnClickListener {
                // 创建数据库对象
                val db = dbHelper.writableDatabase
            }
        }
    }
}
```

## 2. 插入数据

```kotlin
val db = dbHelper.writableDatabase
val value = ContentValues().apply {
    put("name","The Da Vinci Code")
    put("author","Dan Brown")
    put("pages","454")
    put("price","17")
}
db.insert("Book",null,value)
```

## 3. 更新数据

update的第三四个参数作为限制参数，否则会把所有行都进行更新，“?”为占位符，对应后面的列表数据

```kotlin
val db = dbHelper.writableDatabase
val value = ContentValues().apply {
    put("author","Dan Brown - ${System.currentTimeMillis()/1_000_000}")
}
db.update("Book",value,"name = ?", arrayOf("The Da Vinci Code"))
```

## 4. 删除数据

第二个和第三个参数同为限制参数

```kotlin
val db = dbHelper.writableDatabase
db.delete("Book","pages > ?", arrayOf("400"))
```

## 5. 查询数据

query()方法参数

| 方法参数      | 对应SQL部分               | 描述                         |
| ------------- | ------------------------- | ---------------------------- |
| table         | from table_name           | 查询的表名                   |
| columns       | select column1，column2   | 查询的列名                   |
| selection     | where colum = value       | 指定查询的约束条件           |
| selectionArgs |                           | 为where的占位符提供参数      |
| groupBy       | group by column           | 指定需要group by 的列        |
| having        | having column = value     | 对group by后的结果进一步约束 |
| orderBy       | order by column1，column2 | 指定查询结果的排序方式       |

```kotlin
val db = dbHelper.writableDatabase
val cursor = db.query(
    table = "Book",
    columns = arrayOf("name", "pages", "price"),
    selection="pages > ?",
    selectionArgs = arrayOf("400"),
    groupBy = null,
    having = null,
    orderBy ="price"
)
if (cursor.moveToFirst()) {
    do {
        val name = cursor.getString(cursor.getColumnIndex("name"))
        val pages = cursor.getInt(cursor.getColumnIndex("pages"))
        val price = cursor.getDouble(cursor.getColumnIndex("price"))
        Log.d("SQLite:", "Query -> name: $name,pages: $pages,price: $price")
    } while (cursor.moveToNext())
}
cursor.close()
```

## 6. 使用SQL操作数据库

```kotlin
val db = dbHelper.writableDatabase
// 增删改使用
db.execSQL(
    "insert into Book (name,author,pages,price) values(?,?,?,?)",
    arrayOf("One day", "Dav", "465", "17.8")
)
// 查询使用
val cursor = db.rawQuery("select name,pages,price from Book where pages > ? and price > ?", arrayOf("400", "50"))
```

## 7. 事务操作

> 事务的特性就是可以保证让一系列的操作要么全部完成，要么一个都不会完成

```kotlin
// 事务操作
btnTransaction.setOnClickListener {
    val db = dbHelper.writableDatabase
    // 开启事务
    db.beginTransaction()
    try {
        db.delete("Book", null, null)
        val value = contentValuesOf(
            "name" to "The Da Vinci Code",
            "author" to "Dan Brown",
            "pages" to System.currentTimeMillis() % 1000,
            "price" to System.currentTimeMillis() % 100
        )
        db.insert("Book", null, value)
        // 事务执行成功
        db.setTransactionSuccessful()
    } catch (e: Exception) {
    } finally {
        // 结束事务
        db.endTransaction()
    }
}
```

