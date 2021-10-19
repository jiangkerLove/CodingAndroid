package jfp.study.ipc.pool

import android.app.Service
import android.content.Intent
import android.os.IBinder

class PoolService : Service() {

    companion object {
        const val POOL_CALCULATE = "Calculate"
        const val POOL_ENCRYPT = "Encrypt"
    }

    private val calculate = object : Calculate.Stub() {
        override fun add(a: Int, b: Int) = a + b

        override fun mix(a: Int, b: Int) = a + b shl 1

    }

    private val encrypt = object : Password.Stub() {
        override fun encrypt(str: String?) = POOL_ENCRYPT + str
    }

    override fun onBind(intent: Intent) = object : AidlPool.Stub() {
        override fun getBinder(id: String?): IBinder? = when(id){
            POOL_CALCULATE -> calculate
            POOL_ENCRYPT -> encrypt
            else -> null
        }
    }
}