package com.example.photoeditorpolishanything

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

class AlbumFetcher(val context: Context) {

    fun fetchAlbums(): List<DashboardActivity.Album> {
        val albumList = mutableListOf<DashboardActivity.Album>()
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media._ID,
            "count(" + MediaStore.Images.Media.BUCKET_ID + ") as photo_count"
        )
        val selection = "1) GROUP BY (${MediaStore.Images.Media.BUCKET_ID}"
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC"

        val cursor = context.contentResolver.query(uri, projection, selection, null, sortOrder)

        cursor?.use {
            val bucketNameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val photoCountColumn = it.getColumnIndexOrThrow("photo_count")
            val imageIdColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (it.moveToNext()) {
                val albumName = it.getString(bucketNameColumn)
                val photoCount = it.getInt(photoCountColumn)
                val imageId = it.getLong(imageIdColumn)

                // Get the thumbnail URI for the album's first image
                val thumbnailUri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    imageId.toString()
                )
                albumList.add(DashboardActivity.Album(albumName, thumbnailUri, photoCount))
            }
        }
        return albumList
    }
}
