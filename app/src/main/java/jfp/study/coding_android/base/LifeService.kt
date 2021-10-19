package jfp.study.coding_android.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import jfp.study.coding_android.base.LifeActivity.Companion.TAG

class LifeService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: $this")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: $this")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: $this")
        super.onDestroy()
    }
}