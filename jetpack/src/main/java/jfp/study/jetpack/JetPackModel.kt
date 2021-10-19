package jfp.study.jetpack

import androidx.lifecycle.*

class JetPackModel : ViewModel() {
    private val value1 = MutableLiveData<Int>()
    private val value2 = MutableLiveData<Int>()

    val showValue1: LiveData<String> = LiveDataTransform.collect(value1, value2) { str1, str2 ->
        "LiveData clickTime: $str1 <-> $str2"
    }

    val showValue2: LiveData<String> = Transformations.map(value1) {
        "now value is $it"
    }

    private val selectType = MutableLiveData(true)

    val showValue3: LiveData<Int> = Transformations.switchMap(selectType) {
        if (it) value1 else value2
    }

    fun additionValue1() {
        val value = value1.value ?: 0
        value1.value = value + 1
    }

    fun additionValue2() {
        val value = value2.value ?: 0
        value2.value = value + 1
    }
}