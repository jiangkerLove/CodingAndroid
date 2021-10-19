package jfp.study.calculate.list

import java.util.*


class ArrayList<E> constructor(initialCapacity: Int) : MutableIterable<E> {

    companion object {
        private const val DEFAULT_CAPACITY = 10
        private const val MAX_ARRAY_SIZE = Int.MAX_VALUE - 8

        private val EMPTY_ELEMENTDATA = emptyArray<Any?>()
    }

    // 保证迭代过程数据没被改变
    @Transient
    private var modCount = 0

    // 通常这个有许多多余的元素，所以重写writeObject方法，节约资源
    @Transient
    private var elementData = EMPTY_ELEMENTDATA

    private var size = 0

    init {
        elementData = when {
            initialCapacity > 0 -> arrayOfNulls<Any?>(initialCapacity)
            initialCapacity == 0 -> EMPTY_ELEMENTDATA
            else -> throw IllegalArgumentException("")
        }
    }

    /**
     * ArrayList并不会自动缩容，缩小为满的数组
     */
    fun trimToSize() {
        elementData = if (size == 0) EMPTY_ELEMENTDATA else elementData.copyOf(size)
    }

    fun add(e: E): Boolean {
        ensureCapacityInternal(size + 1)
        elementData[size++] = e
        return true
    }

    @Suppress("UNCHECKED_CAST")
    operator fun get(index: Int): E {
        if (index >= size) throw IndexOutOfBoundsException("$index")
        return elementData[index] as E
    }

    fun removeAt(index: Int): E {
        if (index >= size) throw IndexOutOfBoundsException("$index")

        modCount++
        val oldValue = elementData[index] as E

        val numMoved = size - index - 1
        if (numMoved > 0) System.arraycopy(
            elementData, index + 1, elementData, index,
            numMoved
        )
        elementData[--size] = null // clear to let GC do its work
        return oldValue
    }

    /**
     * 如果是空的，则使用默认的10或需求容量的最大值作为初始容量
     */
    private fun ensureCapacityInternal(minCapacity: Int) {
        var capacity = minCapacity
        if (elementData === EMPTY_ELEMENTDATA) {
            capacity = maxOf(DEFAULT_CAPACITY, minCapacity)
        }
        ensureExplicitCapacity(capacity)
    }

    /**
     * 如果所需容量大于现在的容量
     */
    private fun ensureExplicitCapacity(minCapacity: Int) {
        modCount++
        if (minCapacity - elementData.size > 0)
            grow(minCapacity)
    }


    private fun grow(minCapacity: Int) {
        val oldCapacity = elementData.size
        // 扩容为原来的1.5倍
        var newCapacity = oldCapacity + (oldCapacity shr 1)
        // 新的容量取大一点的值
        if (newCapacity - minCapacity < 0) newCapacity = minCapacity
        // 如果比最大限制还大了
        if (newCapacity - MAX_ARRAY_SIZE > 0) newCapacity = hugeCapacity(minCapacity)
        elementData = elementData.copyOf(newCapacity)
    }

    private fun hugeCapacity(minCapacity: Int): Int {
        if (minCapacity < 0) throw OutOfMemoryError()
        // 如果超过最大限制，则使用最大值，没有则使用最大限制
        return if (minCapacity > MAX_ARRAY_SIZE) Int.MAX_VALUE else MAX_ARRAY_SIZE
    }

    override fun iterator(): MutableIterator<E> {
        return Itr()
    }

    /**
     * kotlin 中 Iterator是不可变的，没有remove方法
     * kotlin 中 MutableIterator才对应Java的Iterator
     */
    inner class Itr<E> : MutableIterator<E> {

        private var expectedModCount = modCount

        private var limit = size

        // 下一个元素
        private var cursor = 0

        // 当前元素
        private var lastRet = -1

        override fun hasNext() = cursor < limit

        // 获取下一个元素
        override fun next(): E {
            if (modCount != expectedModCount) throw ConcurrentModificationException()
            val i = cursor
            if (i >= limit) throw NoSuchElementException()
            val elementData = elementData
            if (i >= elementData.size) throw ConcurrentModificationException()
            cursor = i + 1
            lastRet = i
            return elementData[i] as E
        }

        // remove当前元素
        override fun remove() {
            check(lastRet >= 0)
            if (modCount != expectedModCount) throw ConcurrentModificationException()
            try {
                removeAt(lastRet)
                cursor = lastRet
                lastRet = -1
                expectedModCount = modCount
                limit--
            } catch (ex: java.lang.IndexOutOfBoundsException) {
                throw ConcurrentModificationException()
            }
        }

    }

}
