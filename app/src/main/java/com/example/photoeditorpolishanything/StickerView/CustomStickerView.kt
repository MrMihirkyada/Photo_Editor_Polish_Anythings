package com.example.photoeditorpolishanything.StickerView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
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
    private var currentStickerView: CustomStickerView? = null


    private var dX = 0f
    private var dY = 0f

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
            // Resize logic here (if needed)
            true
        }

        // Implement copy sticker logic
        copyButton.setOnClickListener {
            // Copy logic here
        }

        // Implement flip sticker logic
        flipButton.setOnClickListener {
            // Flip logic here
        }

        // Dragging logic for the entire CustomStickerView (the whole sticker view moves)
        this.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = v.x - event.rawX
                    dY = v.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    v.animate()
                        .x(event.rawX + dX)
                        .y(event.rawY + dY)
                        .setDuration(0)
                        .start()
                }
            }
            true
        }

        // Click listener to create a new sticker
        stickerImageView.setOnClickListener {
            createNewSticker()
        }
    }

    // Function to create a new sticker and display it in a new layout
    private fun createNewSticker() {
        // Create a new container (FrameLayout) for the new sticker
        val newStickerContainer = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // Create a new instance of CustomStickerView for the sticker itself
        val newSticker = CustomStickerView(context)

        // Set the same image as the current sticker (optional)
        newSticker.setStickerImage(getStickerImage()) // Define this method to return the current sticker image if needed

        // Add the new sticker to the newly created container (the FrameLayout)
        newStickerContainer.addView(newSticker)

        // Add the new sticker container (which contains the sticker) to the main layout
        val mainLayout = findViewById<FrameLayout>(R.id.stickerLayout) // Replace with the ID of your main layout
        mainLayout.addView(newStickerContainer)

        // Optionally, you can set initial position or size of the new sticker container
        newStickerContainer.x = 100f // Example position, change as needed
        newStickerContainer.y = 200f // Example position, change as needed
    }


    // Function to create a new layout for each sticker
    private fun createNewStickerLayout(imageUrl: String) {
        // Create a new FrameLayout or RelativeLayout for the new sticker
        val newStickerLayout = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // Create a new CustomStickerView for the sticker
        val newStickerView = CustomStickerView(context)

        // Load the sticker image into the new CustomStickerView
        Glide.with(this)
            .load(imageUrl)
            .into(newStickerView.findViewById(R.id.stickerImageView))  // Assuming the ImageView ID is stickerImageView

        // Add the new sticker to the newly created layout
        newStickerLayout.addView(newStickerView)

        // Add the new layout (which contains the sticker) to the parent layout
        val parentLayout = findViewById<FrameLayout>(R.id.stickerLayout) // The main parent layout that holds all sticker layouts
        parentLayout.addView(newStickerLayout)

        // Optionally set position or other properties of the new layout
        newStickerLayout.x = 100f // Example x-position
        newStickerLayout.y = 200f // Example y-position

        // If needed, you can update the reference for the latest sticker view
        currentStickerView = newStickerView
    }





    private fun getStickerImage(): Bitmap {
        // Check if the ImageView has a drawable
        val drawable = stickerImageView.drawable
        if (drawable != null) {
            // Create a Bitmap from the drawable
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        // Return an empty bitmap if no drawable is found
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Placeholder bitmap
    }


    // This function sets the sticker image from a Bitmap
    fun setStickerImage(bitmap: Bitmap) {
        stickerImageView.setImageBitmap(bitmap)
    }
}
