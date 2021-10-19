package jfp.study.calculate

import org.junit.Test

class ArrayListTest {

    @Test
    fun sublist_test() {
        val list = mutableListOf(1, 2, 3, 4, 5, 6)
        val childList = list.subList(1, 3)
        childList.add(1,5)
        list.add(1,4)
        //java.util.ConcurrentModificationException
        childList.add(1,3)
    }
}