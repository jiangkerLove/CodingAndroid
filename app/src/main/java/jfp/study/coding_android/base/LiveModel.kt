package jfp.study.coding_android.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveModel : ViewModel() {

    val liveData : MutableLiveData<String> = MutableLiveData("base")

}