package jfp.study.coding_android.network

class Msg(
    val receiver: String = "",
    val sender: String = "",
    val content: String = "",
    val platform: String = "",
    val type: Int = 0
//    val receiveTime: Date = Date()
)