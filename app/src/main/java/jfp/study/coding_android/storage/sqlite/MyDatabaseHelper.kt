package jfp.study.coding_android.storage.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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