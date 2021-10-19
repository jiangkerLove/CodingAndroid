package jfp.study.coding_android.storage

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

object JsonObj {

    data class Result(
        val code: Int,
        val result: String,
        val message: String
    )

    /**
     * [{"code":1,"message":"ok","result":"getRequest: 18 -- jiangker"},
     * {"code":1,"message":"ok","result":"getRequest: 19 -- love"},
     * {"code":1,"message":"ok","result":"getRequest: 20 -- kotlin"}]
     */
    fun parseJsonWithJsonObject(jsonData: String): List<Result> {
        val list = mutableListOf<Result>()
        try {
            val jsonArray = JSONArray(jsonData)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val code = jsonObject.getInt("code")
                val message = jsonObject.getString("message")
                val result = jsonObject.getString("result")
                list.add(Result(code, result, message))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }


    /**
     * [{"code":1,"message":"ok","result":"getRequest: 18 -- jiangker"},
     * {"code":1,"message":"ok","result":"getRequest: 19 -- love"},
     * {"code":1,"message":"ok","result":"getRequest: 20 -- kotlin"}]
     */
    fun parseJsonWithGson(jsonData: String): List<Result> {
        val gson = Gson()
        val typeOf = object : TypeToken<List<Result>>() {}.type
//        object :TypeToken<Map<Int,Long>>(){}.type
        return gson.fromJson(jsonData, typeOf)
    }

}