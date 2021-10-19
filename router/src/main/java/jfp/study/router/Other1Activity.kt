package jfp.study.router

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import jfp.study.router.databinding.ActivityMainBinding

@Route(path = "/other/route1")
class Other1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.apply {
            tvFile.text = "/other/route1"
            btnGoNext.setOnClickListener {
                ARouter.getInstance().build("/main/route2")
                    .navigation(this@Other1Activity, object : NavigationCallback {
                        override fun onFound(postcard: Postcard?) {

                        }

                        override fun onLost(postcard: Postcard?) {
                        }

                        override fun onArrival(postcard: Postcard?) {
                        }

                        override fun onInterrupt(postcard: Postcard?) {

                        }

                    })
            }
        }
    }
}