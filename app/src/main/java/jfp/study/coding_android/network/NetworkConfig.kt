package jfp.study.coding_android.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConfig {

//    const val SHARE_BASE_URL = "http://39.97.68.81:8080/"
    const val SHARE_BASE_URL = "http://192.168.2.100:8080/"

    private var instance: Retrofit? = null

    val api: Retrofit = instance ?: synchronized(this) {
        instance ?: Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SHARE_BASE_URL).build().also {
                instance = it
            }
    }

}