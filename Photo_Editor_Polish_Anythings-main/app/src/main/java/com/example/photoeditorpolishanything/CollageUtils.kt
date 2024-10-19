package com.example.photoeditorpolishanything

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import com.bumptech.glide.Glide

object CollageUtils {
    fun createCollage(imageUris: List<Uri>, context: Context, width: Int, height: Int): Bitmap {
        val collageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(collageBitmap)
        val paint = Paint()

        val size = imageUris.size
        val columnCount = 2 // Adjust this for more columns
        val rowCount = if (size % columnCount == 0) size / columnCount else (size / columnCount) + 1

        val imageWidth = width / columnCount
        val imageHeight = height / rowCount

        for (i in imageUris.indices) {
            val imageUri = imageUris[i]
            val bitmap = Glide.with(context)
                .asBitmap()
                .load(imageUri)
                .submit(imageWidth, imageHeight)
                .get()

            val left = (i % columnCount) * imageWidth
            val top = (i / columnCount) * imageHeight

            canvas.drawBitmap(bitmap, left.toFloat(), top.toFloat(), paint)
        }

        return collageBitmap
    }
}