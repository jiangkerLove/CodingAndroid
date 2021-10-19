package jfp.study.jetpack.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    var name: String,
    var pages: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id :Long = 0
}