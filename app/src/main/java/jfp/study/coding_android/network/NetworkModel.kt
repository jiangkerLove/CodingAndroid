package jfp.study.coding_android.network

class NetworkModel<M>(
    val code: Int = 0,
    val message: String = "OK",
    val result: M? = null
)