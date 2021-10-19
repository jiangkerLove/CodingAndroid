package jfp.study.coding_android.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import jfp.study.coding_android.R
import jfp.study.coding_android.databinding.ActivityLifeBinding

class ALifeActivity : LifeActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnGoto.setOnClickListener {
            startActivity(Intent(this,BLifeActivity::class.java))
        }
    }

}