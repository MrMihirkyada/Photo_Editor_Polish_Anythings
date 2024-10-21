package com.example.photoeditorpolishanything.StickerView

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.photoeditorpolishanything.R

class Sticker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private lateinit var stickerImageView: ImageView
    private lateinit var deleteButton: ImageView
    private lateinit var resizeButton: ImageView

    private var dX = 0f
    private var dY = 0f
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var scaleFactor = 1f
    private var rotationAngle = 0f

    init {
        inflate(context, R.layout.sticker_view_layout, this) // Inflate sticker layout
        initViews()
    }

    private fun initViews() {
        stickerImageView = findViewById(R.id.stickerImageView)
        deleteButton = findViewById(R.id.imgDeleteButton)
        resizeButton = findViewById(R.id.imgResizeButton)

        setupDeleteButton()
        setupResizeAndRotateListener()
        setupStickerDragListener()
    }

    private fun setupDeleteButton() {
        deleteButton.setOnClickListener {
            (parent as FrameLayout).removeView(this) // Remove the sticker from the container
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupResizeAndRotateListener() {
        resizeButton.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {                                             
                    // Calculate new scale
                    val scaleFactor = event.rawX / initialTouchX
                    stickerImageView.scaleX = scaleFactor
                    stickerImageView.scaleY = scaleFactor

                    // Rotate the sticker
                    val rotationAngle = event.rawY - initialTouchY
                    stickerImageView.rotation = rotationAngle
                }
            }
            true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupStickerDragListener() {
        stickerImageView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = v.x - event.rawX
                    dY = v.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    v.animate().x(event.rawX + dX).y(event.rawY + dY).setDuration(0).start()
                }
            }
            true
        }
    }
}
