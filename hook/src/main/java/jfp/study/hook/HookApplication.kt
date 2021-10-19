package jfp.study.hook

import android.app.ActivityThread
import android.app.Application
import android.app.Instrumentation
import android.os.Handler
import jfp.study.hook.callback.HookCallback
import jfp.study.hook.instrumentation.HookInstrumentation
import jfp.study.hook.plugin.PluginHelper

class HookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PluginHelper.init(this)
        hookInstrumentation()
        hookHandlerCallback()
    }

    private fun hookInstrumentation() {
        val activityThreadClazz = ActivityThread::class.java
        val activityThreadFiled = activityThreadClazz.getDeclaredField("sCurrentActivityThread")
        activityThreadFiled.isAccessible = true
        val activityThread = activityThreadFiled.get(null)

        val instrumentationFiled = activityThreadClazz.getDeclaredField("mInstrumentation")
        instrumentationFiled.isAccessible = true
        val instrumentation = instrumentationFiled.get(activityThread) as Instrumentation
        val hookInstrumentation = HookInstrumentation(instrumentation)
        instrumentationFiled.set(activityThread, hookInstrumentation)
    }

    private fun hookHandlerCallback() {
        val activityThreadClazz = ActivityThread::class.java
        val activityThreadFiled = activityThreadClazz.getDeclaredField("sCurrentActivityThread")
        activityThreadFiled.isAccessible = true
        val activityThread = activityThreadFiled.get(null)

        val handlerFiled = activityThreadClazz.getDeclaredField("mH")
        handlerFiled.isAccessible = true
        val handler = handlerFiled.get(activityThread) as Handler

        val handlerClazz = handler.javaClass.superclass
        val callbackFiled = handlerClazz?.getDeclaredField("mCallback")
        callbackFiled?.isAccessible = true
        callbackFiled?.set(handler, HookCallback())
    }


}