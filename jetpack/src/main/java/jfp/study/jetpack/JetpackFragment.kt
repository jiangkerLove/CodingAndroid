package jfp.study.jetpack

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class JetpackFragment :Fragment(){
    private val model by lazy {
        val model = ViewModelProvider(this)[JetPackModel::class.java]
    }
}