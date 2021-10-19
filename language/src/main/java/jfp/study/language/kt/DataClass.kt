package jfp.study.language.kt

data class DataClass @JvmOverloads constructor(
    val str1: String = "jiangker",
    var str2: String? = "",
    var int1: Int = 1,
    var int2: Int? = 2,
    val list: List<Int>
)
