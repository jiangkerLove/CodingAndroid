package jfp.study.player

import org.junit.Test

import org.junit.Assert.*
import kotlin.concurrent.thread

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun threadTest() {
        val thread = object : Thread() {
            override fun run() {
                // 设置中断标志之后，sleep会直接抛出异常退出
                interrupt()
                val time = System.currentTimeMillis()
                try {
                    sleep(1000)
                } catch (e: Exception) {
                }
                println(System.currentTimeMillis() - time)
            }
        }
        thread.start()
    }
}