package com.example.photoeditorpolishanything

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Function to fetch images from an album
    fun fetchImagesFromAlbum(albumName: String, context: Context) : List<Uri>
    {
        val imageList = mutableListOf<Uri>()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(albumName)

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
                imageList.add(uri)
            }
        }

        return imageList
    }

    // Function to fetch album images using Kotlin coroutines (preferred method)
    fun fetchAlbumImages(context: DashboardActivity, callback: (List<Uri>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val images = getImagesFromMediaStore(context)

            // Switch to Main thread to send the result back to UI
            withContext(Dispatchers.Main) {
                callback(images)
            }
        }
    }

    // Function to query the MediaStore and retrieve image URIs
    fun getImagesFromMediaStore(context: DashboardActivity): List<Uri> {
        val images = mutableListOf<Uri>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID)

        val cursor = context.contentResolver.query(uri,projection,null,null,
            MediaStore.Images.Media.DATE_ADDED + " DESC")

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext())
            {
                val id = it.getLong(idColumn)
                val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                images.add(imageUri)
            }
        }

        return images
    }