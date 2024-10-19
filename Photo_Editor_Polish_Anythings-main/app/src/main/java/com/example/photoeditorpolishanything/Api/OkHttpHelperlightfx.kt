package com.example.photoeditorpolishanything.Api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object OkHttpHelperlightfx {
    private val client = OkHttpClient()

    fun fetchlightfx(url: String, callback: (LightFxApi?) -> Unit) {

        val request = Request.Builder().url(url).build()

        val baseurl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/lightfx/"

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException)
            {
                e.printStackTrace()
                callback(null)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response)
            {
                if (response.isSuccessful)
                {
                    response.body?.let { responseBody ->
                        val gson = Gson()
                        val type = object : TypeToken<LightFxApi>() {}.type
                        val LightFxApi: LightFxApi = gson.fromJson(responseBody.charStream(), type)
                        callback(LightFxApi)
                    } ?: callback(null)
                } else {
                    callback(null)
                }
            }
        })
    }
}

//
//object OkHttpHelperlightfx {
//    private val client = OkHttpClient()
//
//    fun fetchlightfx(url: String, callback: (LightFXApi?) -> Unit) {
//        val request = Request.Builder().url(url).build()
//
//        client.newCall(request).enqueue(object : okhttp3.Callback {
//            override fun onFailure(call: okhttp3.Call, e: IOException) {
//                e.printStackTrace()
//                callback(null)
//            }
//
//            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
//                if (response.isSuccessful) {
//                    response.body?.let { responseBody ->
//                        val gson = Gson()
//                        val type = object : TypeToken<LightFXApi>() {}.type
//                        val lightFxApi: LightFXApi = gson.fromJson(responseBody.charStream(), type)
//                        Log.e("APIResponse", "LightFxApi: $lightFxApi")
//                        callback(lightFxApi)
//                    } ?: callback(null)
//                }
//                else
//                {
//                    Log.e("APIResponse", "Response not successful: ${response.message}")
//                    callback(null)
//                }
//            }
//        })
//    }
//}
