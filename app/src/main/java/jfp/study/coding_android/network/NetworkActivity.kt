package jfp.study.coding_android.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import jfp.study.coding_android.R
import kotlinx.coroutines.launch

class NetworkActivity : AppCompatActivity() {

    private val server = NetworkConfig.api.create(NetworkServer::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        lifecycleScope.launch {
            val result = server.getRecentMsg()
            result.result
        }
    }
}