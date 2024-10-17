package com.example.photoeditorpolishanything.Album

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.photoeditorpolishanything.DashboardActivity

class AlbumFetcher(val context: Context) {

    fun fetchAlbums(): List<DashboardActivity.Album> {
        val albumList = mutableListOf<DashboardActivity.Album>()
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME, // Album name
            MediaStore.Images.Media.BUCKET_ID,           // Album ID
            MediaStore.Images.Media._ID                  // Image ID
        )

        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC"

        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, sortOrder)

        val albumMap = mutableMapOf<String, MutableList<Long>>()

        cursor?.use {
            val bucketNameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val bucketIdColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID)
            val imageIdColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (it.moveToNext()) {
                val albumName = it.getString(bucketNameColumn) ?: "Unknown Album"
                val imageId = it.getLong(imageIdColumn)

                // Use album name instead of ID to ensure proper names
                albumMap.getOrPut(albumName) { mutableListOf() }.add(imageId)
            }
        }

        // Process the albumMap to generate album list
        albumMap.forEach { (albumName, imageIds) ->
            val firstImageId = imageIds.first()
            val thumbnailUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, firstImageId.toString())
            val photoCount = imageIds.size

            albumList.add(DashboardActivity.Album(albumName, thumbnailUri, photoCount))
        }

        return albumList
    }
}

