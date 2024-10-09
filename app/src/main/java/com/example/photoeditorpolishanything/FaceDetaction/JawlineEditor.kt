package com.example.photoeditorpolishanything.FaceDetection

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import com.google.mlkit.vision.face.Face

class JawlineEditor {

    /**
     * Adjust the jawline of a detected face in the given bitmap.
     * This method adjusts the jawline geometrically without color modification.
     *
     * @param bitmap The original bitmap containing the face.
     * @param face The detected face object.
     * @param jawlineFactor A factor to control how much to modify the jawline. Positive values for enhancement, negative for reduction.
     * @return A new bitmap with the adjusted jawline.
     */
//    fun adjustJawline(bitmap: Bitmap, face: Face, jawlineFactor: Float): Bitmap {
//        val resultBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
//        val canvas = Canvas(resultBitmap)
//        val paint = Paint()
//        paint.isAntiAlias = true
//
//        // Get the bounding box of the detected face
//        val bounds = face.boundingBox
//
//        // Define the jawline region to adjust
//        val jawlineRect = Rect(
//            bounds.left,
//            bounds.bottom - (bounds.height() / 4),
//            bounds.right,
//            bounds.bottom
//        )
//
//        // Define scale factors based on the jawline factor
//        val scaleFactorX = 1 + jawlineFactor
//        val scaleFactorY = 1 + jawlineFactor
//
//        // Apply scaling transformation to the canvas
//        canvas.save()
//        canvas.scale(scaleFactorX, scaleFactorY, jawlineRect.centerX().toFloat(), jawlineRect.centerY().toFloat())
//        canvas.drawBitmap(bitmap, 0f, 0f, paint)
//        canvas.restore()
//
//        return resultBitmap
//    }


    fun adjustJawline(bitmap: Bitmap, face: Face, jawlineFactor: Int): Bitmap {
        // Create a mutable copy of the original bitmap
        val editedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        // Create a Canvas and Paint for drawing
        val canvas = Canvas(editedBitmap)
        val paint = Paint()

        // Get face bounding box and facial landmarks
        val faceBounds = face.boundingBox
        val faceBitmap = Bitmap.createBitmap(
            editedBitmap,
            faceBounds.left,
            faceBounds.top,
            faceBounds.width(),
            faceBounds.height()
        )

        // Apply jawline adjustments to the faceBitmap
        // Note: Replace the following logic with your actual face adjustment logic.
        applyJawlineAdjustment(faceBitmap, jawlineFactor)

        // Place the adjusted faceBitmap back into the editedBitmap
        canvas.drawBitmap(faceBitmap, faceBounds.left.toFloat(), faceBounds.top.toFloat(), paint)

        return editedBitmap
    }

    private fun applyJawlineAdjustment(faceBitmap: Bitmap, jawlineFactor: Int): Bitmap {
        // Convert bitmap to mutable
        val mutableBitmap = faceBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint()

        // Get bitmap dimensions
        val width = mutableBitmap.width
        val height = mutableBitmap.height

        // Log dimensions for debugging
        Log.d("FaceEditing", "Bitmap width: $width, height: $height")

        // Perform a basic pixel manipulation as a placeholder
        val pixelArray = IntArray(width * height)
        mutableBitmap.getPixels(pixelArray, 0, width, 0, 0, width, height)

        for (i in pixelArray.indices) {
            val x = i % width
            val y = i / width

            // Placeholder adjustment (replace with actual logic)
            if (y > height / 2) {
                val originalColor = pixelArray[i]
                val r = (Color.red(originalColor) * jawlineFactor).toInt().coerceIn(0, 255)
                val g = (Color.green(originalColor) * jawlineFactor).toInt().coerceIn(0, 255)
                val b = (Color.blue(originalColor) * jawlineFactor).toInt().coerceIn(0, 255)
                pixelArray[i] = Color.rgb(r, g, b)
            }
        }

        mutableBitmap.setPixels(pixelArray, 0, width, 0, 0, width, height)

        // Draw bitmap on canvas
        canvas.drawBitmap(mutableBitmap, 0f, 0f, paint)

        // Log completion
        Log.d("FaceEditing", "Jawline adjustment applied.")

        return mutableBitmap
    }



    // Function to adjust face width
    fun adjustFaceWidth(bitmap: Bitmap, face: Face, factor: Float): Bitmap {
        val resultBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(resultBitmap)
        val paint = Paint()
        paint.isAntiAlias = true

        val bounds = face.boundingBox
        val scaleFactor = 1 + factor
        canvas.save()
        canvas.scale(scaleFactor, 1f, bounds.centerX().toFloat(), bounds.centerY().toFloat())
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        canvas.restore()

        return resultBitmap
    }

    // Function to adjust face height
    fun adjustFaceHeight(bitmap: Bitmap, face: Face, factor: Float): Bitmap {
        val resultBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(resultBitmap)
        val paint = Paint()
        paint.isAntiAlias = true

        val bounds = face.boundingBox
        val scaleFactor = 1 + factor
        canvas.save()
        canvas.scale(1f, scaleFactor, bounds.centerX().toFloat(), bounds.centerY().toFloat())
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        canvas.restore()

        return resultBitmap
    }

    fun adjustFaceInBitmap(
        bitmap: Bitmap,
        face: Face,
        scaleFactor: Float
    ): Bitmap {
        // Convert the bitmap to a mutable bitmap for editing
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        // Extract the face's bounding box
        val faceRect = face.boundingBox

        // Create a canvas to draw on the mutable bitmap
        val canvas = Canvas(mutableBitmap)

        // Create a paint object for drawing
        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.TRANSPARENT // You may want to use a different color or blending mode
            style = Paint.Style.FILL
        }

        // Define the new face rectangle size based on the scale factor
        val newWidth = (faceRect.width() * scaleFactor).toInt()
        val newHeight = (faceRect.height() * scaleFactor).toInt()

        // Calculate the new bounding box
        val newFaceRect = Rect(
            faceRect.left - (newWidth - faceRect.width()) / 2,
            faceRect.top - (newHeight - faceRect.height()) / 2,
            faceRect.right + (newWidth - faceRect.width()) / 2,
            faceRect.bottom + (newHeight - faceRect.height()) / 2
        )

        // Ensure the new face rectangle is within the bounds of the original bitmap
        if (newFaceRect.left < 0) newFaceRect.left = 0
        if (newFaceRect.top < 0) newFaceRect.top = 0
        if (newFaceRect.right > bitmap.width) newFaceRect.right = bitmap.width
        if (newFaceRect.bottom > bitmap.height) newFaceRect.bottom = bitmap.height

        // Draw the face area on the canvas (you may need more sophisticated drawing depending on the effect)
        canvas.drawRect(newFaceRect, paint)

        // Return the modified bitmap
        return mutableBitmap
    }

//    fun resizeFace(bitmap: Bitmap, face: Face, scaleFactor: Float): Bitmap {
//        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
//        val faceRect = face.boundingBox
//
//        // Calculate new face dimensions
//        val newWidth = (faceRect.width() * scaleFactor).toInt()
//        val newHeight = (faceRect.height() * scaleFactor).toInt()
//
//        // Create a matrix for scaling
//        val matrix = Matrix()
//        matrix.postScale(scaleFactor, scaleFactor)
//
//        // Create a new bitmap for the scaled face
//        val scaledFaceBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(scaledFaceBitmap)
//        canvas.drawBitmap(mutableBitmap, faceRect, Rect(0, 0, newWidth, newHeight), null)
//
//        // Calculate the position to draw the scaled face back onto the original image
//        val newLeft = faceRect.left + (faceRect.width() - newWidth) / 2
//        val newTop = faceRect.top + (faceRect.height() - newHeight) / 2
//
//        // Draw the scaled face back onto the original image
//        val canvasOriginal = Canvas(mutableBitmap)
//        canvasOriginal.drawBitmap(scaledFaceBitmap, newLeft.toFloat(), newTop.toFloat(), null)
//
//        return mutableBitmap
//    }

    fun resizeFaceInPlace(bitmap: Bitmap, face: Face, scaleFactor: Float): Bitmap {
        val faceRect = face.boundingBox

        // Calculate new face dimensions
        val newWidth = (faceRect.width() * scaleFactor).toInt()
        val newHeight = (faceRect.height() * scaleFactor).toInt()

        // Create a matrix for scaling
        val matrix = Matrix()
        matrix.postScale(scaleFactor, scaleFactor)

        // Create a new bitmap for the scaled face
        val scaledFaceBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)

        // Draw the face region onto the scaled bitmap
        val canvas = Canvas(scaledFaceBitmap)
        canvas.drawBitmap(bitmap, faceRect, Rect(0, 0, newWidth, newHeight), null)

        // Calculate the position to draw the scaled face back onto the original image
        val newLeft = faceRect.left + (faceRect.width() - newWidth) / 2
        val newTop = faceRect.top + (faceRect.height() - newHeight) / 2

        // Draw the scaled face back onto the original image
        canvas.drawBitmap(scaledFaceBitmap, newLeft.toFloat(), newTop.toFloat(), null)

        return bitmap // Return the modified original bitmap
    }

    fun editFace(bitmap: Bitmap, referenceImage: Bitmap, face: Face): Bitmap {
        val effectsMap = mutableMapOf<String, Any>()
        // Populate effectsMap based on referenceImage analysis

        val modifiedBitmap = applyEffects(bitmap.copy(Bitmap.Config.ARGB_8888, true), face, effectsMap)
        return modifiedBitmap
    }

    fun applyEffects(bitmap: Bitmap, face: Face, effectsMap: Map<String, Any>): Bitmap {
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
        }

        val scaleFactor = effectsMap["scaleFactor"] as? Float ?: 1.0f

        // Get the face rectangle
        val faceRect = Rect(face.boundingBox.left, face.boundingBox.top, face.boundingBox.right, face.boundingBox.bottom)

        // Crop the face region from the bitmap
        val faceBitmap = Bitmap.createBitmap(bitmap, faceRect.left, faceRect.top, faceRect.width(), faceRect.height())

        // Create a matrix for scaling
        val matrix = Matrix().apply {
            setScale(scaleFactor, scaleFactor, faceRect.centerX().toFloat(), faceRect.centerY().toFloat())
        }

        // Create a new bitmap to hold the scaled face
        val scaledFaceBitmap = Bitmap.createBitmap(faceRect.width(), faceRect.height(), Bitmap.Config.ARGB_8888)
        val faceCanvas = Canvas(scaledFaceBitmap)
        faceCanvas.drawBitmap(faceBitmap, matrix, paint)

        // Draw the scaled face back onto the original bitmap
        canvas.drawBitmap(scaledFaceBitmap, faceRect.left.toFloat(), faceRect.top.toFloat(), null)

        return bitmap
    }


    fun resizeFace(bitmap: Bitmap, face: Face, scaleFactor: Float): Bitmap {
        val matrix = Matrix().apply {
            setScale(scaleFactor, scaleFactor, face.boundingBox.centerX().toFloat(), face.boundingBox.centerY().toFloat())
        }

        val scaledBitmap = Bitmap.createBitmap(bitmap, face.boundingBox.left, face.boundingBox.top, face.boundingBox.width(), face.boundingBox.height())
        val canvas = Canvas(scaledBitmap)
        canvas.drawBitmap(scaledBitmap, matrix, Paint())

        return bitmap
    }

}
