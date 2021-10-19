package jfp.study.language

import org.junit.Test
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

class ThreadTest {

    @Test
    fun newThread() {
        /**
         * 继承至Thread的实现
         */
        class MyThread(
            val boolean: Boolean
        ) : Thread() {
            override fun run() {
                super.run()
                assert(boolean)
            }
        }

        val thread = MyThread(true)
        thread.start()
    }

    @Test
    fun newRunnable() {
        class MyRunnable(
            val boolean: Boolean
        ) : Runnable{
            override fun run() {
                assert(boolean)
            }
        }
        val thread = Thread(MyRunnable(true))
        thread.start()
    }

    @Test
    fun callablePool(){
        class MyCall : Callable<Boolean>{
            override fun call(): Boolean {
                return true
            }
        }
        val threadPool = Executors.newSingleThreadExecutor()
        val feature = threadPool.submit(MyCall())
        assert(feature.get())
    }

    @Test
    fun newFuture(){
        class MyCall : Callable<Boolean>{
            override fun call(): Boolean {
                return true
            }
        }
        val task = FutureTask<Boolean>(MyCall())
        val thread = Thread(task)
        thread.start()
        assert(task.get())
    }


}