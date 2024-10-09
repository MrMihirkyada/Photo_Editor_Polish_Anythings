package com.example.photoeditorpolishanything.FaceDetaction

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF

fun adjustEyeSize(bitmap: Bitmap, leftEye: PointF, rightEye: PointF, scaleFactor: Float): Bitmap {
    val resultBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
    val canvas = Canvas(resultBitmap)
    val paint = Paint()

    // Calculate eye center points and distances
    val eyeDistance = Math.sqrt(Math.pow((rightEye.x - leftEye.x).toDouble(), 2.0) + Math.pow((rightEye.y - leftEye.y).toDouble(), 2.0)).toFloat()
    val eyeRadius = eyeDistance / 4

    // Create transformation matrix
    val matrix = Matrix()
    matrix.setScale(scaleFactor, scaleFactor, leftEye.x, leftEye.y)

    // Draw left eye with scaling
    canvas.drawBitmap(resultBitmap, matrix, paint)

    matrix.setScale(scaleFactor, scaleFactor, rightEye.x, rightEye.y)

    // Draw right eye with scaling
    canvas.drawBitmap(resultBitmap, matrix, paint)

    return resultBitmap
}
