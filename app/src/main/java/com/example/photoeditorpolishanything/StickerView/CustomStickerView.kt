package com.example.photoeditorpolishanything.StickerView

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.photoeditorpolishanything.R

class CustomStickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var stickerImageView: ImageView
    private var deleteButton: ImageView
    private var resizeButton: ImageView
    private var copyButton: ImageView
    private var flipButton: ImageView

    init {
        // Inflate the sticker layout
        LayoutInflater.from(context).inflate(R.layout.sticker_layout, this, true)
        stickerImageView = findViewById(R.id.stickerImageView)
        deleteButton = findViewById(R.id.imgDeleteButton)
        resizeButton = findViewById(R.id.imgResizeButton)
        copyButton = findViewById(R.id.imgCopy)
        flipButton = findViewById(R.id.imgHorizontal)

        setupListeners()
    }

    private fun setupListeners() {
        // Delete sticker
        deleteButton.setOnClickListener {
            (parent as FrameLayout).removeView(this)
        }

        // Implement resize logic
        resizeButton.setOnTouchListener { _, event ->
            // Add resize logic here
            true
        }

        // Implement copy sticker logic
        copyButton.setOnClickListener {
            // Add copy logic here
        }

        // Implement flip sticker logic
        flipButton.setOnClickListener {
            // Add flip logic here
        }

        // Implement drag logic
        stickerImageView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Store the position to drag
                }
                MotionEvent.ACTION_MOVE -> {
                    // Move the sticker based on the touch events
                    v.animate().x(event.rawX).y(event.rawY).setDuration(0).start()
                }
            }
            true
        }
    }

    fun setStickerImage(bitmap: Bitmap) {
        stickerImageView.setImageBitmap(bitmap)
    }
}
