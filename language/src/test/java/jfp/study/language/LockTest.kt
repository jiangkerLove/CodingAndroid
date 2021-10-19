package jfp.study.language

import org.junit.Test
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.locks.LockSupport
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

class LockTest {

    @Test
    fun lock_support(){

        val thread = thread {
            LockSupport.unpark(Thread.currentThread())
            println("start")
            LockSupport.park()
            LockSupport.unpark(Thread.currentThread())
            println("end")
            LockSupport.park()
            println("end")
        }

        Thread.sleep(1000)
    }

    fun lock_cycle(){
        val lock = CyclicBarrier(3)
        val reentrantLock = ReentrantLock()
    }
}