package jfp.study.ipc.pool

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.databinding.DataBindingUtil
import jfp.study.ipc.R
import jfp.study.ipc.databinding.ActivityAidlPoolBinding

class AidlPoolActivity : AppCompatActivity() {

    private var poolService: AidlPool? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            poolService = AidlPool.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAidlPoolBinding>(
            this,
            R.layout.activity_aidl_pool
        )
        bindService(Intent(this,PoolService::class.java),connection,Context.BIND_AUTO_CREATE)
        binding.apply {
            btnCompute.setOnClickListener {
                val service =
                    Calculate.Stub.asInterface(poolService?.getBinder(PoolService.POOL_CALCULATE))
                val result = service.add(1, 1)
                Log.i(PoolService.POOL_CALCULATE, "result: $result")
            }
            btnEncrypt.setOnClickListener {
                val service =
                    Password.Stub.asInterface(poolService?.getBinder(PoolService.POOL_ENCRYPT))
                val result = service.encrypt("password")
                Log.i(PoolService.POOL_ENCRYPT, "result: $result")
            }
        }
    }
}