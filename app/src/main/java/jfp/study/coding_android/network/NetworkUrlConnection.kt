package jfp.study.coding_android.network

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

/**
 * TODO: 类的一句话描述
 * <p>
 * TODO：类的功能和使用详细描述
 * </p>
 * Create Date 2021/7/5
 */
class NetworkUrlConnection {

    private fun httpUrl() {
        var connection: HttpURLConnection? = null
        try {
            val url = URL("http://39.97.68.81:8080/share/msg/query/new-msg")
            connection = url.openConnection() as HttpURLConnection
            val response = StringBuilder()
            connection.connectTimeout = 8000
            connection.readTimeout = 8000
            val input = connection.inputStream
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    response.append(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }
    }

}