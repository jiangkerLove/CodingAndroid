package jfp.study.coding_android.storage.sqlite

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.contentValuesOf
import androidx.databinding.DataBindingUtil
import jfp.study.coding_android.R
import jfp.study.coding_android.databinding.ActivitySqliteBinding
import java.lang.Exception

class SQLiteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySqliteBinding>(this, R.layout.activity_sqlite)
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 1)
        binding.apply {
            // 创建数据库
            btnCreateTB.setOnClickListener {
                dbHelper.writableDatabase
            }
            // 写入数据
            btnAddData.setOnClickListener {
                val db = dbHelper.writableDatabase
                val value = ContentValues().apply {
                    put("name", "The Da Vinci Code")
                    put("author", "Dan Brown")
                    put("pages", System.currentTimeMillis() % 1000)
                    put("price", System.currentTimeMillis() % 100)
                }
                db.insert("Book", null, value)
            }
            // 更新数据
            btnUpdateData.setOnClickListener {
                val db = dbHelper.writableDatabase
                val value = ContentValues().apply {
                    put("pages", System.currentTimeMillis() % 1000)
                }
                db.update("Book", value, "pages < ?", arrayOf("400"))
            }
            // 删除数据
            btnDeleteData.setOnClickListener {
                val db = dbHelper.writableDatabase
                db.delete("Book", "pages > ?", arrayOf("400"))
            }
            // 查询数据
            btnQueryData.setOnClickListener {
                val db = dbHelper.writableDatabase
                val cursor = db.query(
                    "Book",
                    arrayOf("name", "pages", "price"),
                    "pages > ?",
                    arrayOf("400"),
                    null,
                    null,
                    "price"
                )
                print(cursor)
            }
            // Sql语句方式
            btnSQL.setOnClickListener {
                val db = dbHelper.writableDatabase
                db.execSQL(
                    "insert into Book (name,author,pages,price) values(?,?,?,?)",
                    arrayOf("One day", "Dav", "465", "17.8")
                )
                val cursor =
                    db.rawQuery("select name,pages,price from Book where pages > ? and price > ?", arrayOf("400", "50"))
                print(cursor)
            }
            // 事务操作
            btnTransaction.setOnClickListener {
                val db = dbHelper.writableDatabase
                // 开启事务
                db.beginTransaction()
                try {
                    db.delete("Book", null, null)
                    if (true){
                        throw IllegalArgumentException()
                    }
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
        }
    }

    private fun print(cursor: Cursor) {
        cursor.print {
            val name = getString(getColumnIndex("name"))
            val pages = getInt(getColumnIndex("pages"))
            val price = getDouble(getColumnIndex("price"))
            Log.d("SQLite:", "Query -> name: $name,pages: $pages,price: $price")
        }
    }

    private inline fun Cursor.print(block: Cursor.() -> Unit) {
        if (moveToFirst()) {
            do {
                block()
            } while (moveToNext())
        }
        close()
    }
}