package jfp.study.router

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import jfp.study.router.RouterApp.Companion.TAG


@Interceptor(priority = 1)
class MainInterceptor : IInterceptor{

    override fun init(context: Context?) {

    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        Log.i(TAG, "process: $postcard")
        callback?.onContinue(postcard)
    }

}