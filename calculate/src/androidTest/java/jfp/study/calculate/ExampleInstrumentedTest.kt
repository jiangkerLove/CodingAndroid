package jfp.study.calculate

import android.util.ArrayMap
import android.util.LruCache
import android.util.SparseArray
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert

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
        assertEquals("jfp.study.jetpack.test", appContext.packageName)
    }

    @Test
    fun sparseArray_test() {
        val array = SparseArray<String>()
        array.put(10, "android")
        array.put(1, "adroid")
        array.append(5, "android")
        assertEquals("android", array.get(5))
    }

    @Test
    fun array_map_test(){
        class Sparse(val value: Int, val name: String) {
            override fun hashCode() = value
            override fun equals(other: Any?): Boolean {
                return other is Sparse && other.value == value && other.name == name
            }
        }

        val map = ArrayMap<Sparse, String>()
        map[Sparse(100,"jiang")] = "jiang"
        map[Sparse(100,"fang")] = "fang"
        // hash值相同并不会覆盖，必须要key等于
        assert(map[Sparse(100,"")] == "jiang")
    }

    @Test
    fun lruCache_test() {
        val list = LruCache<Int, String>(3)
        list.put(1, "ab")
        list.put(2, "cd")
        list.put(3, "ef")
        list.put(1, "gh")
        val value = list.get(1)
    }


}