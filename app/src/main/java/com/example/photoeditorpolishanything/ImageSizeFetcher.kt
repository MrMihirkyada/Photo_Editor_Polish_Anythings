package com.example.photoeditorpolishanything

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.text.DecimalFormat
import java.util.concurrent.ConcurrentHashMap

class ImageSizeFetcher {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .build()
    private val df = DecimalFormat("#.##")
    private val cache = ConcurrentHashMap<String, Long>()

    suspend fun fetchImageSizes(baseUrl: String, data: List<String?>?): String {
        if (data.isNullOrEmpty()) return "0 KB"

        val totalSize = withContext(Dispatchers.IO) {
            data.mapNotNull { url ->
                url?.let { async { fetchImageSize(baseUrl + it) } }
            }.awaitAll().sum()
        }
        return formatSize(totalSize)
    }

    private suspend fun fetchImageSize(url: String): Long {
        return cache[url] ?: run {
            try {
                val request = Request.Builder().url(url).build()
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful)
                    {
                        0L
                    }
                    else
                    {
                        val size = response.body?.contentLength() ?: 0L
                        if (size > 0)
                        {
                            cache[url] = size
                        }
                        size
                    }
                }
            }
            catch (e: Exception)
            {
                0L
            }
        }
    }

    private fun formatSize(size: Long): String {
        return when {
            size >= 1024 * 1024 -> "${df.format(size / 1024.0 / 1024.0)} MB"
            size >= 1024 -> "${df.format(size / 1024.0)} KB"
            else -> "$size Bytes"
        }
    }
}
