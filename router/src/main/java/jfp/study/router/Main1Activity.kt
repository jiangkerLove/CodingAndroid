package jfp.study.router

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import jfp.study.router.RouterApp.Companion.TAG
import jfp.study.router.databinding.ActivityMainBinding

@Route(path = "/main/route1")
class Main1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.apply {
            tvFile.text = "/main/route1"
            btnGoNext.setOnClickListener {
                ARouter.getInstance().build("/main/route2")
                    .navigation(this@Main1Activity, object : NavigationCallback {
                        override fun onFound(postcard: Postcard?) {
                            Log.i(TAG, "onFound: ${postcard.toString()}")
                        }

                        override fun onLost(postcard: Postcard?) {
                            Log.i(TAG, "onLost: ${postcard.toString()}")
                        }

                        override fun onArrival(postcard: Postcard?) {
                            Log.i(TAG, "onArrival: ${postcard.toString()}")
                        }

                        override fun onInterrupt(postcard: Postcard?) {
                            Log.i(TAG, "onInterrupt: ${postcard.toString()}")
                        }

                    })
            }
        }
    }
}