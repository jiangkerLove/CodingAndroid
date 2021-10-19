package jfp.study.language

import org.junit.Assert
import org.junit.Test
import kotlin.concurrent.thread

class ThreadLocalTest {

    @Test
    fun testName(){
        val localName = ThreadLocal<String>()
        val name = "jiangker"
        localName.set(name)
        Assert.assertEquals(name,localName.get())
        localName.remove()
        thread {
            Assert.assertEquals(null,localName.get())
        }
        Assert.assertEquals(name,localName.get())
        localName.remove()
    }

}