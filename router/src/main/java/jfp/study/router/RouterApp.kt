package jfp.study.router

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

class RouterApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

     companion object{
         const val TAG = "ARouter Tag"
     }
}