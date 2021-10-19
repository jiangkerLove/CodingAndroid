package jfp.study.calculate.sort

object Sorts {

    fun bubbleSort(array: IntArray) {
        for (i in array.indices) {
            for (r in 1 until array.size - i) {
                if (array[r] < array[r - 1]) {
                    val temp = array[r]
                    array[r] = array[r - 1]
                    array[r - 1] = temp
                }
            }
        }
    }

    fun selectionSort(array: IntArray) {
        for (i in array.indices) {
            var index = i
            for (r in i + 1 until array.size) {
                if (array[index] > array[r]) {
                    index = r
                }
            }
            val temp = array[index]
            array[index] = array[i]
            array[i] = temp
        }
    }

    fun insertSort(array: IntArray) {
        for (i in array.indices) {
            val value = array[i]
            var r = i
            while (r > 0 && value < array[r - 1]) {
                array[r] = array[r - 1]
                r--
            }
            array[r] = value
        }
    }

    fun shellSort(array: IntArray) {
        var h = 0
        while (h < array.size / 3) h = 3 * h + 1
        while (h > 0) {
            for (i in h until array.size) {
                val value = array[i]
                var index = i
                while (index >= h && value < array[index - h]) {
                    array[index] = array[index - h]
                    index -= h
                }
                array[index] = value
            }
            h /= 3
        }
    }

    fun mergeSort(array: IntArray) {
        divide(array, 0, array.size - 1)
    }

    private fun divide(array: IntArray, start: Int, end: Int) {
        if (start >= end) return
        val mid = start + (end - start) / 2
        divide(array, start, mid)
        divide(array, mid + 1, end)
        merge(array, start, mid, end)
    }

    private fun merge(array: IntArray, start: Int, mid: Int, end: Int) {
        if (array[mid] < array[mid + 1]) return
        for (i in mid + 1..end) {
            val value = array[i]
            var index = i
            while (index > start && value < array[index - 1]) {
                array[index] = array[index - 1]
                index--
            }
            array[index] = value
        }
    }

    fun quickSort(array: IntArray) {
        quickSort(array, 0, array.size - 1)
    }

    private fun quickSort(array: IntArray, start: Int, end: Int) {
        if (start >= end) return
        val index = partition(array, start, end)
        quickSort(array, start, index - 1)
        quickSort(array, index + 1, end)
    }

    private fun partition(array: IntArray, start: Int, end: Int): Int {
        val value = array[start]
        var index = start
        for (i in index + 1..end) {
            if (value > array[i]) {
                index++
                val temp = array[i]
                array[i] = array[index]
                array[index] = temp
            }
        }
        val temp = array[start]
        array[start] = array[index]
        array[index] = temp
        return index
    }

    fun heapSort(array: IntArray) {
        for (i in array.indices) {
            siftUp(array, i)
        }
        for (i in array.size - 1 downTo 1) {
            heapify(array, i)
        }
    }

    private fun siftUp(array: IntArray, end: Int) {
        val value = array[end]
        var index = end
        while (index > 0 && value > array[(index - 1) ushr 1]) {
            val temp = array[(index - 1) ushr 1]
            array[(index - 1) ushr 1] = array[index]
            array[index] = temp
            index = (index - 1) ushr 1
        }
        array[index] = value
    }

    private fun heapify(array: IntArray, end: Int) {
        val temp = array[0]
        array[0] = array[end]
        array[end] = temp
        var index = 0
        while ((index shl 1) + 1 < end) {
            val leftIndex = (index shl 1) + 1
            val rightIndex = (index + 1) shl 1
            val maxIndex =
                if (rightIndex < end && array[rightIndex] > array[leftIndex]) rightIndex else leftIndex
            if (array[maxIndex] <= array[index]) break
            val change = array[maxIndex]
            array[maxIndex] = array[index]
            array[index] = change
            index = maxIndex
        }
    }
}