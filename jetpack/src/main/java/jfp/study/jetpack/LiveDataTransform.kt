package jfp.study.jetpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

object LiveDataTransform {
    fun <L, R, T> collect(data1: LiveData<L>, data2: LiveData<R>, collect: (L?, R?) -> T): LiveData<T> {
        return MediatorLiveData<T>().apply {
            addSource(data1) {
                value = collect(it, data2.value)
            }
            addSource(data2) {
                value = collect(data1.value, it)
            }
        }
    }
}