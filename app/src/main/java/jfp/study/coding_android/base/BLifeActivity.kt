package jfp.study.coding_android.base

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.FragmentManager

class BLifeActivity : LifeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnGoto.setOnClickListener {
//            model.liveData.value = "time = ${SystemClock.uptimeMillis()}"
        }
    }
}