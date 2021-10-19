package jfp.study.common

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import kotlin.concurrent.thread

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun handler_test() {
        val message = Message.obtain()
        Looper.prepare()
        val handler = Handler()
        handler.sendMessageDelayed(message,1000)
        thread {
            Looper.prepare()
            val threadHandler = Handler()
            message.isAsynchronous = true
            // 会出错 This message is already in use.
            threadHandler.sendMessageDelayed(message,1000)
        }
    }

    @Test
    fun idea_handler(){
        Looper.prepare()
        Looper.myQueue().addIdleHandler {
            println("abc")
            return@addIdleHandler true
        }
    }
}