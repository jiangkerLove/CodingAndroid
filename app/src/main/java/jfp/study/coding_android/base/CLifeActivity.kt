package jfp.study.coding_android.base

import android.content.Intent
import android.os.Bundle

class CLifeActivity : LifeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            btnGoto.setOnClickListener {
                startActivity(Intent(this@CLifeActivity,ALifeActivity::class.java))
            }
        }
    }

}