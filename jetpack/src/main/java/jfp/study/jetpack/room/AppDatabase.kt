package jfp.study.jetpack.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// 默认是不可以在主现场执行操作的
@Database(version = 2, entities = [User::class, Book::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun bookDao(): BookDao

    companion object {
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