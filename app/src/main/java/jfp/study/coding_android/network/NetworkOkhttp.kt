package jfp.study.coding_android.network

import okhttp3.*
import okio.BufferedSink
import java.io.IOException

/**
 * TODO: 类的一句话描述
 * <p>
 * TODO：类的功能和使用详细描述
 * </p>
 * Create Date 2021/7/3
 */
object NetworkOkhttp {

    private val client = OkHttpClient()

    /**
     * http://localhost:8080/test/getParams?name=xxxx&age=18
     */
    fun syncGetParams(url: String, name: String, age: Int): String {
        try {
            val urlBuilder = HttpUrl.parse("${NetworkConfig.SHARE_BASE_URL}$url")!!.newBuilder()
            urlBuilder.addQueryParameter("name", name)
            urlBuilder.addQueryParameter("age", age.toString())
            val request = Request.Builder()
                .url(urlBuilder.build())
                .build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val result: String? = response.body()?.string() // 消息较小时
//                val resultSource: BufferedSource? = response.body()?.source() // 大于1M时
//                val resultStream: InputStream? = response.body()?.byteStream() // 大于1M时
                return result ?: "empty error"
            }
        } catch (e: Exception) {
            return e.stackTraceToString()
        }
        return "null"
    }


    /**
     * body中携带数据，异步上传
     */
    fun asyncPostBody(url: String, name: String, age: Int, callback: (String?) -> Unit) {
        try {
            val jsonBody = MediaType.parse("application/json; charset=utf-8")
            val builder = RequestBody.create(
                jsonBody, """
                {
                    "name":"jiangker",
                    "age": 18
                }
            """.trimIndent()
            )
            val requestBody = FormBody.Builder()
                .add("name", name)
                .add("age", age.toString())
                .build()
            val request = Request.Builder()
                .url("${NetworkConfig.SHARE_BASE_URL}$url")
                .post(builder)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    // 子线程的回调
                    val result = response.body()?.string()
                    callback(result)
                }
            })
        } catch (e: Exception) {

        }
    }

    private fun createHeader(headers: HashMap<String, String>): Headers {
        // 第一种方式，直接对request添加header
        val rqeustBuilder = Request.Builder()
        if (headers.isNotEmpty()) {
            headers.forEach {
                // 可以添加重复的key-value
                rqeustBuilder.addHeader(it.key, it.value)
                // 会替换相同的key值
//                rqeustBuilder.header(it.key,it.value)
            }
        }
        // 创建新的header
        val headerBuilder = Headers.Builder()
        if (headers.isNotEmpty()) {
            headers.forEach {
                headerBuilder.add(it.key, it.value)
            }
        }
        return headerBuilder.build()
    }

    private fun createBody(params: HashMap<String, String>) {
        val requestBody = object : RequestBody() {
            override fun contentType() = MediaType.parse("charset=utf-8")

            override fun writeTo(sink: BufferedSink) {
                sink.writeUtf8("")
            }
        }
    }

    private fun okHttp(receiver: String, sender: String, content: String) {
        try {
            val requestBody = FormBody.Builder()
                .add("receiver", receiver)
                .add("sender", sender)
                .add("content", content)
                .build()
            val request = Request.Builder().url("${NetworkConfig.SHARE_BASE_URL}/share/msg/save")
                .post(requestBody)// 设置为post请求，默认为get
                .build()
            val response = client.newCall(request).execute()
            val result = response.body()?.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}