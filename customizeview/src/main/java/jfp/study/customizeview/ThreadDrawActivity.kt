package jfp.study.customizeview

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import jfp.study.customizeview.databinding.ActivityThreadDrawBinding
import jfp.study.customizeview.slide.DisPatchLayout.Companion.TAG
import java.util.*
import kotlin.concurrent.thread

class ThreadDrawActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityThreadDrawBinding>(
            this,
            R.layout.activity_thread_draw
        )
        var handler: Handler? = null
        thread {
            Looper.prepare()
            Looper.myQueue().addIdleHandler {
                Log.i(TAG, "onCreate: IdleHandler")
                return@addIdleHandler true
            }
            handler = object :Handler(){
                override fun handleMessage(msg: Message) {
                    Log.i(TAG, "handleMessage: ")
                }
            }
//            val clazz = MessageQueue::class.java
//            val method = clazz.getDeclaredMethod("postSyncBarrier")
//            method.isAccessible = false
//            method.invoke(Looper.myQueue())
            Looper.loop()
        }
        binding.drawText.setOnClickListener {
            val mesg = Message.obtain()
            mesg.isAsynchronous = true
            handler?.sendMessage(mesg)
            val stack = LinkedList<String>()
        }
    }
}