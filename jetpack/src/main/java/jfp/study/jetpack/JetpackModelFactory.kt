package jfp.study.jetpack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JetpackModelFactory(val value:String) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val constructor =modelClass.getDeclaredConstructor(String::class.java)
        constructor.isAccessible = true
        return constructor.newInstance(value)
    }
}