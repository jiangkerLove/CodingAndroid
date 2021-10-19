package jfp.study.customizeview

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ViewAnimator
import androidx.databinding.DataBindingUtil
import jfp.study.customizeview.databinding.ActivityLayoutAnimatorBinding
import jfp.study.customizeview.slide.DisPatchLayout.Companion.TAG

class LayoutAnimatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLayoutAnimatorBinding>(
            this,
            R.layout.activity_layout_animator
        )
        binding.apply {
            this.includeHeader.ivHeader.setOnClickListener {
                Log.i(TAG, "onCreate: ")
            }
        }
    }
}