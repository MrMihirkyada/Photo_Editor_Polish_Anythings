
package com.example.photoeditorpolishanything.Api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object OkHttpHelpers {
    private val client = OkHttpClient()

    fun fetchSticker(url: String, callback: (StickersApi?) -> Unit) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    response.body?.let { responseBody ->
                        val responseString = responseBody.string()
                        Log.e("ApiResponse", "Raw response: $responseString")

                        val gson = Gson()
                        val type = object : TypeToken<StickersApi>() {}.type
                        val stickerApiResponse: StickersApi = gson.fromJson(responseString, type)
                        callback(stickerApiResponse)
                    } ?: callback(null)
                } else {
                    callback(null)
                }
            }
        })
    }
}
