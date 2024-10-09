package com.example.photoeditorpolishanything

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

class CustomImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private val templateImage = Template_image()  // Instantiate the Template_image class

    private val whiteSpaces = mutableListOf<WhiteSpace>()
    private var selectedWhiteSpace: WhiteSpace? = null

    fun setBitmap(bitmap: Bitmap) {
        whiteSpaces.clear()
        val detectedSpaces = templateImage.detectWhiteSpaces(bitmap)  // Call the detectWhiteSpaces method
        detectedSpaces.forEach { rect ->
            whiteSpaces.add(WhiteSpace(rect))
        }
        invalidate()
    }

    init {
        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                handleTouch(event.x.toInt(), event.y.toInt())
            }
            true
        }
    }

    private fun handleTouch(x: Int, y: Int) {
        for (whiteSpace in whiteSpaces) {
            if (whiteSpace.rect.contains(x, y)) {
                selectedWhiteSpace = whiteSpace

                // Check if context is actually of type MainActivity
                if (context is TemplatesActivity) {
                    (context as TemplatesActivity).openGallery() // Trigger image selection
                }
                break
            }
        }
    }

    fun setImageForWhiteSpace(bitmap: Bitmap) {
        selectedWhiteSpace?.image = bitmap
        invalidate() // Redraw the view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (whiteSpace in whiteSpaces) {
            whiteSpace.image?.let {
                canvas.drawBitmap(it, null, whiteSpace.rect, null)
            }
        }
    }

    // Inner class to represent a white space and the image placed in it
    private data class WhiteSpace(val rect: Rect, var image: Bitmap? = null)
}