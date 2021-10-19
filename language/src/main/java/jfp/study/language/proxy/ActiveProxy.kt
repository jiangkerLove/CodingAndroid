package jfp.study.language.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy


interface Log {
    fun print()
}

class InvokeLog(
    private val log: Log
) : InvocationHandler {

    override fun invoke(p0: Any?, method: Method, params: Array<out Any>?): Any {
        // doSome before
        val obj = method.invoke(log, params)
        // doSome after
        return obj
    }

}

fun main() {
    val handler = InvokeLog(object :Log{
        override fun print() {

        }
    })
    val proxy = Proxy.newProxyInstance(handler.javaClass.classLoader, arrayOf(Log::class.java),handler) as Log
    proxy.print()
}
