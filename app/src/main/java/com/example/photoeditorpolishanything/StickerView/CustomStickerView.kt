package com.example.photoeditorpolishanything.StickerView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.R

//class CustomStickerView @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null
//) : FrameLayout(context, attrs) {
//
//    private var stickerImageView: ImageView
//    private var deleteButton: ImageView
//    private var resizeButton: ImageView
//    private var copyButton: ImageView
//    private var flipButton: ImageView
//    private var currentStickerView: CustomStickerView? = null
//
//    private var dX = 0f
//    private var dY = 0f
//
//    init {
//        // Inflate the sticker layout
//        LayoutInflater.from(context).inflate(R.layout.sticker_layout, this, true)
//        stickerImageView = findViewById(R.id.stickerImageView)
//        deleteButton = findViewById(R.id.imgDeleteButton)
//        resizeButton = findViewById(R.id.imgResizeButton)
//        copyButton = findViewById(R.id.imgCopy)
//        flipButton = findViewById(R.id.imgHorizontal)
//
//        setupListeners()
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    private fun setupListeners() {
//        // Delete sticker
//        deleteButton.setOnClickListener {
//            (parent as FrameLayout).removeView(this)
//        }
//
//        // Implement resize logic
//        resizeButton.setOnTouchListener { _, event ->
//            // Resize logic here (if needed)
//            true
//        }
//
//        // Implement copy sticker logic
//        copyButton.setOnClickListener {
//            // Copy logic here
//        }
//
//        // Implement flip sticker logic
//        flipButton.setOnClickListener {
//            // Flip logic here
//        }
//
//        // Dragging logic for the entire CustomStickerView (the whole sticker view moves)
//        this.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    dX = v.x - event.rawX
//                    dY = v.y - event.rawY
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    v.animate()
//                        .x(event.rawX + dX)
//                        .y(event.rawY + dY)
//                        .setDuration(0)
//                        .start()
//                }
//            }
//            true
//        }
//
//        // Click listener to create a new sticker
//        stickerImageView.setOnClickListener {
//            createNewSticker()
//        }
//    }
//
//    // Function to create a new sticker and display it in a new layout
//    private fun createNewSticker() {
//        // Create a new container (FrameLayout) for the new sticker
//        val newStickerContainer = FrameLayout(context).apply {
//            layoutParams = FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT
//            )
//        }
//    }
//
//
//    // Function to create a new layout for each sticker
//    private fun createNewStickerLayout(imageUrl: String)
//    {
//        // Create a new FrameLayout or RelativeLayout for the new sticker
//        val newStickerLayout = FrameLayout(context).apply {
//            layoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
//        }
//
//        // Create a new CustomStickerView for the sticker
//        val newStickerView = CustomStickerView(context)
//
//        // Load the sticker image into the new CustomStickerView
//        Glide.with(this)
//            .load(imageUrl)
//            .into(newStickerView.findViewById(R.id.stickerImageView))  // Assuming the ImageView ID is stickerImageView
//
//        // Add the new sticker to the newly created layout
//        newStickerLayout.addView(newStickerView)
//
//        // Add the new layout (which contains the sticker) to the parent layout
//        val parentLayout = findViewById<FrameLayout>(R.id.stickerLayout) // The main parent layout that holds all sticker layouts
//        parentLayout.addView(newStickerLayout)
//
//        // Optionally set position or other properties of the new layout
//        newStickerLayout.x = 100f // Example x-position
//        newStickerLayout.y = 200f // Example y-position
//
//        // If needed, you can update the reference for the latest sticker view
//        currentStickerView = newStickerView
//    }
//
//    private fun getStickerImage(): Bitmap  {
//        // Check if the ImageView has a drawable
//        val drawable = stickerImageView.drawable
//        if (drawable != null) {
//            // Create a Bitmap from the drawable
//            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight,
//                Bitmap.Config.ARGB_8888)
//
//            val canvas = Canvas(bitmap)
//            drawable.setBounds(0, 0, canvas.width, canvas.height)
//            drawable.draw(canvas)
//            return bitmap
//        }
//
//        // Return an empty bitmap if no drawable is found
//        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Placeholder bitmap
//    }
//
//
//    // This function sets the sticker image from a Bitmap
//    fun setStickerImage(bitmap: Bitmap) {
//        stickerImageView.setImageBitmap(bitmap)
//    }
//}


class CustomStickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs)
{
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

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners() {
        // Delete sticker
        deleteButton.setOnClickListener {
            // Remove this CustomStickerView from its parent
            (parent as? ViewGroup)?.removeView(this)
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
                    // Capture the sticker's starting position relative to the touch point
                    dX = v.x - event.rawX
                    dY = v.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    // Update the sticker's position by changing its x and y coordinates
                    v.x = event.rawX + dX
                    v.y = event.rawY + dY
                }
                MotionEvent.ACTION_UP -> {
                    // Optional: You could handle touch release here (if needed)
                }
            }
            true // Indicate the touch event is handled
        }

        // Click listener to create a new sticker
        stickerImageView.setOnClickListener {
            createNewSticker()
        }
    }


    // Function to create a new sticker and display it in a new layout
    private fun createNewSticker()
    {
        // Create a new container (FrameLayout) for the new sticker
        val newStickerContainer = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

//    // Function to create a new layout for each sticker
//    private fun createNewStickerLayout(imageUrl: String)
//    {
//        // Step 1: Create a new FrameLayout to hold the new sticker
//        val newStickerLayout = FrameLayout(context).apply {
//            layoutParams = FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT
//            )
//        }
//
//        // Step 2: Create a new CustomStickerView for the sticker
//        val newStickerView = CustomStickerView(context)
//
//        // Step 3: Load the sticker image into the new CustomStickerView
//        Glide.with(this)
//            .load(imageUrl)
//            .into(newStickerView.findViewById(R.id.stickerImageView)) // Assuming the ImageView ID is stickerImageView
//
//        // Step 4: Add the CustomStickerView (sticker) to the newly created FrameLayout
//        newStickerLayout.addView(newStickerView)
//
//        // Step 5: Add the new FrameLayout (which contains the sticker) to the parent layout
//        val parentLayout = findViewById<FrameLayout>(R.id.stickerLayout) // The main parent layout that holds all stickers
//        parentLayout.addView(newStickerLayout)
//
//        // Optional: Set initial position or other properties for the new layout
//        newStickerLayout.x = 100f // Example x-position
//        newStickerLayout.y = 200f // Example y-position
//
//        // Update the reference for the latest sticker view (if needed)
//        currentStickerView = newStickerView
//    }

    private fun getStickerImage(): Bitmap  {
        // Check if the ImageView has a drawable
        val drawable = stickerImageView.drawable
        if (drawable != null) {
            // Create a Bitmap from the drawable
            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888)

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

    // Method to load an image into the sticker from a URL
    fun setStickerImageFromUrl(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(stickerImageView)
    }
}
