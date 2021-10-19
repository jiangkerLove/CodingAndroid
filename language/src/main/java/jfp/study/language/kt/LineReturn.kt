package jfp.study.language.kt


// inline相当于把方法拷贝过去替换，所以可以实现方法返回以及局部返回
// 普通的高阶函数，只能进行局部返回，而不能进行方法返回。

// 内敛函数的参数不能传递给高阶作为参数，因为非内敛函数不能进行方法返回，所以crossinline表示没有方法返回。

fun main() {
    normalFun { return@normalFun }
    inlineFun { return@inlineFun }
    noinlineFun { return@noinlineFun }
}


fun normalFun(block: () -> Unit) {
    highFun(block)
    inLineHighFun(block)
}


inline fun inlineFun(block: () -> Unit) {
//    内敛函数的
//    highFun(block)
    inLineHighFun(block)
}


//相对于不内敛
inline fun noinlineFun(noinline block: () -> Unit) {
    highFun(block)
    inLineHighFun(block)
}


inline fun crossInlineFun(crossinline block: () -> Unit) {
//    highFun(block)
    inLineHighFun(block)
}

fun highFun(block: () -> Unit) {

}

inline fun inLineHighFun(block: () -> Unit) {

}