package com.example.photoeditorpolishanything

import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.example.photoeditorpolishanything.DashboardActivity.Companion.context

class ImagePreloadModelProvider(private val imageList: List<Uri>) : ListPreloader.PreloadModelProvider<Uri> {
    override fun getPreloadItems(position: Int): List<Uri> {
        return listOf(imageList[position])
    }

    override fun getPreloadRequestBuilder(item: Uri): RequestBuilder<*>
    {
        return Glide.with(context!!).load(item).override(300, 300) // Preload with the same size used in the adapter
    }
}
