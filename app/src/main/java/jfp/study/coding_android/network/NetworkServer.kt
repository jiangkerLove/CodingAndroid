package jfp.study.coding_android.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkServer {

    @GET("share/msg/query/new-msg")
    suspend fun getRecentMsg(): NetworkModel<List<Msg>>

    /**
     * {page}/data.json 路径注解@Path\
     * @Headers 可以一次新写入固定的请求头
     * @Header 动态的写入header
     */
    @Headers("token: this is msg token", "Cache-Control: max-age=0")
    @GET("{page}/data.json")
    fun getData(@Path("page") page: Int, @Header("token") token: String): Call<Msg>

    /**
     * http://localhost:8080/test/getParams?name=xxxx&age=18
     */
    @GET("test/getParams")
    fun getParams(@Query("name") name: String, @Query("age") age: Int): Call<NetworkModel<String>>

    /**
     * 会自动把data转化为json格式的文本
     * ResponseBody表示对结果不关心的写法
     */
    @POST("test/postBody")
    fun postBody(@Body data: Msg): Call<ResponseBody>

}