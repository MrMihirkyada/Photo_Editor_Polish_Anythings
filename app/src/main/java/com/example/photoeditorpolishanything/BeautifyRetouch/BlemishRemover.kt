package com.example.photoeditorpolishanything.BeautifyRetouch

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF

class BlemishRemover
{

    // Method to remove blemishes from the bitmap
    fun removeBlemish(bitmap: Bitmap, blemishRect: RectF): Bitmap
    {
        // Create a mutable bitmap to draw on
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)

        // Define the area to be inpainted
        val blemishRectF = blemishRect
        val blemishArea = Rect(
            blemishRectF.left.toInt(), blemishRectF.top.toInt(),
            blemishRectF.right.toInt(), blemishRectF.bottom.toInt()
        )

        // Extract pixel data from the original bitmap
        val pixels = getPixels(bitmap, blemishArea)

        // Apply blur effect to the pixel data
        val blurredPixels = applyBlurEffectToPixels(pixels, blemishArea.width(), blemishArea.height())

        // Update the output bitmap with blurred pixels
        updateBitmapWithBlurredPixels(outputBitmap, blurredPixels, blemishArea)

        return outputBitmap
    }

    // Get pixels from a region of the bitmap
    private fun getPixels(bitmap: Bitmap, rect: Rect): IntArray {
        val width = rect.width()
        val height = rect.height()
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, rect.left, rect.top, width, height)
        return pixels
    }

    // Apply a simple blur effect to the pixel data
    private fun applyBlurEffectToPixels(pixels: IntArray, width: Int, height: Int): IntArray {
        val blurredPixels = pixels.copyOf()
        // Simple box blur for demonstration. For production, use a more advanced blur algorithm.
        for (y in 1 until height - 1) {
            for (x in 1 until width - 1) {
                var sum = 0
                var count = 0
                for (dy in -1..1) {
                    for (dx in -1..1) {
                        val px = pixels[(y + dy) * width + (x + dx)]
                        sum += px
                        count++
                    }
                }
                val avg = sum / count
                blurredPixels[y * width + x] = avg
            }
        }
        return blurredPixels
    }

    // Update the bitmap with blurred pixels
    private fun updateBitmapWithBlurredPixels(bitmap: Bitmap, pixels: IntArray, rect: Rect) {
        bitmap.setPixels(pixels, 0, rect.width(), rect.left, rect.top, rect.width(), rect.height())
    }
}