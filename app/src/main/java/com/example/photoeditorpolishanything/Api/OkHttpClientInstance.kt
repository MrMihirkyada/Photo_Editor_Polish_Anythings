package com.example.photoeditorpolishanything.Api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object OkHttpHelper {
    private val client = OkHttpClient()

    fun fetchTemplates(url: String, callback: (CategorysApi?) -> Unit)
    {
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback
        {
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
                        val type = object : TypeToken<CategorysApi>() {}.type
                        val categoryApi: CategorysApi = gson.fromJson(responseBody.charStream(), type)
                        callback(categoryApi)
                    } ?: callback(null)
                }
                else
                {
                    callback(null)
                }
            }
        })
    }
}
