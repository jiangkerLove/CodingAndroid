package jfp.study.photo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import jfp.study.photo.databinding.ActivityPhotoBinding
import jfp.study.photo.expand.displayByUrl

class PhotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        val binding =
            DataBindingUtil.setContentView<ActivityPhotoBinding>(this, R.layout.activity_photo)
        binding.apply {
            btnLoading.setOnClickListener {
                ivContent.displayByUrl(
                    Config.getPhotoUrl(
                        (System.currentTimeMillis() % Config.PHOTO_MAX_INDEX * 2).toInt()
                    )
                )
            }
            btnClear.setOnClickListener {
                Glide.with(this@PhotoActivity)
                    .clear(ivContent)
            }
        }
    }
}