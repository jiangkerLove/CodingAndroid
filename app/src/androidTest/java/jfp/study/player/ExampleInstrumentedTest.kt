package jfp.study.player

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import jfp.study.coding_android.network.NetworkOkhttp
import jfp.study.coding_android.storage.JsonObj

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("jfp.study.coding_android", appContext.packageName)
    }

    @Test
    fun testSyncGetParams() {
        val result = NetworkOkhttp.syncGetParams("test/getParams", "jiangker", 18)
        println(result)
        assertEquals(
            """
           {"code":1,"message":"ok","result":"getRequest: 18 -- jiangker"}
            """.trimIndent(),
            result
        )
    }

    @Test
    fun testAsyncPostBody() {
        NetworkOkhttp.asyncPostBody("test/postBody", "jiangker", 18) { result ->
            println(result)
            assertEquals(
                """
           {"code":1,"message":"ok","result":"getRequest: 18 -- jiangker"}
            """.trimIndent(),
                result
            )
        }
    }


    @Test
    fun parseJsonObj() {
        val jsonData = """
            [{"code":1,"message":"ok","result":"getRequest: 18 -- jiangker"},
            {"code":1,"message":"ok","result":"getRequest: 19 -- love"},
            {"code":1,"message":"ok","result":"getRequest: 20 -- kotlin"}]
        """.trimIndent()
        val jsonResult = JsonObj.parseJsonWithJsonObject(jsonData)
        val gsonResult = JsonObj.parseJsonWithGson(jsonData)
        assertEquals(jsonResult[0], gsonResult[0])
        assertEquals(jsonResult[1], gsonResult[1])
        assertEquals(jsonResult[2], gsonResult[2])
    }
}