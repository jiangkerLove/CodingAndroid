package jfp.study.hook.instrumentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import jfp.study.hook.R
import jfp.study.hook.databinding.ActivityInstrumentationBinding

class InstrumentationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityInstrumentationBinding>(
            this,
            R.layout.activity_instrumentation
        )
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this,InstrumentationActivity::class.java))
        }
    }
}