package jfp.study.coding_android.concurrent

import java.util.concurrent.FutureTask

object ThreadTest {

    fun runThread() {
        object : Thread() {
            override fun run() {
                super.run()
                println("thread run")
            }
        }.start()
    }

    fun runRunnable() {
        Thread {
            println("thread run")
        }.start()
    }

    fun runFeature() {
        // 是可以抛出异常的
        FutureTask { return@FutureTask "futureTask" }.get()
    }
}