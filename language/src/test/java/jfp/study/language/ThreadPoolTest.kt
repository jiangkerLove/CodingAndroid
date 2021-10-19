package jfp.study.language

import org.junit.Test
import java.util.concurrent.Executors

class ThreadPoolTest {

    @Test
    fun workStealing(){
        val pool = Executors.newWorkStealingPool()
    }
}