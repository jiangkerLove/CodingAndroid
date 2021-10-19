package jfp.study.calculate

import jfp.study.calculate.sort.Sorts
import org.junit.Test
import java.util.*


class SortTest {

    @Test
    fun sort_test() {
        val random = Random()
        val array = IntArray(20000) { random.nextInt(500000) }
        computeTime(array,"bubbleSort"){
            Sorts.bubbleSort(it)
        }
        computeTime(array,"selectionSort"){
            Sorts.selectionSort(it)
        }
        computeTime(array,"shellSort"){
            Sorts.shellSort(it)
        }
        computeTime(array,"quickSort"){
            Sorts.quickSort(it)
        }
        computeTime(array,"insertSort"){
            Sorts.insertSort(it)
        }
        computeTime(array,"heapSort"){
            Sorts.heapSort(it)
        }
    }

    private fun isSorted(array: IntArray): Boolean {
        for (index in 1 until array.size) {
            if (array[index] < array[index - 1]) return false
        }
        return true
    }

    private inline fun computeTime(array: IntArray,name: String, block: (IntArray) -> Unit) {
        val newArray = array.copyOf(array.size)
        val start = System.currentTimeMillis()
        block(newArray)
        println("$name: ${ System.currentTimeMillis() - start}")
    }
}