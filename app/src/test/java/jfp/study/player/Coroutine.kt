package jfp.study.player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.junit.Test
import kotlin.coroutines.*

class Coroutine {

    @Test
    fun test(){
        val continuation = suspend {

            ""
        }.createCoroutine(object : Continuation<String> {
            override fun resumeWith(result: Result<String>) {

            }

            override val context: CoroutineContext
                get() = EmptyCoroutineContext
        })
        CoroutineScope(EmptyCoroutineContext).launch {
            isActive
        }
    }

    suspend fun run(){
        suspendCoroutine<String> {
            it.resumeWith()
        }
    }
}