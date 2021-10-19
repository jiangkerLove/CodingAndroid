package jfp.study.language

import org.junit.Test
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine

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

    }
}