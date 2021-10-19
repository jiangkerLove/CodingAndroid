package jfp.study.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    // 指定数据库中字段的名字
    @ColumnInfo(name = "firstName") var firstName: String,
    var lastName: String,
    var age: Int
) {
    // 将id设置为自增长的主键
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}