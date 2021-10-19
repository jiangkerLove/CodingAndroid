package jfp.study.calculate.map

/**
 * 采用两个数组，一个数组存储key的hash值，另外一个数组再对应位置存储key以及value
 */
class ArrayMap(capacity: Int) {

    private var mHashes = IntArray(capacity)
    private var mArray: Array<Any?> = arrayOfNulls(capacity)

}