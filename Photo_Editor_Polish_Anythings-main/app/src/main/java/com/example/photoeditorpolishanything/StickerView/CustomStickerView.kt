package com.example.photoeditorpolishanything.StickerView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.R

@SuppressLint("ResourceType")
class CustomStickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private var stickerLayout : FrameLayout
    private var stickerImageView: ImageView
    private var deleteButton: ImageView
    private var resizeButton: ImageView
    private var copyButton: ImageView
    private var flipButton: ImageView
    private var currentStickerView: CustomStickerView? = null

    private var isStickerRemoved = false
    private var dX = 0f
    private var dY = 0f

    private var isFlippedHorizontally = false // Track horizontal flip state
    private var isFlippedVertically = false // Track vertical flip state

    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var initialWidth = 0f
    private var initialHeight = 0f


    init {

        // Dynamically create views instead of using XML
        stickerImageView = ImageView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
        addView(stickerImageView)

        // Inflate the sticker layout
        LayoutInflater.from(context).inflate(R.id.stickerLayout, this, true)
        stickerLayout = findViewById(R.id.stickerLayout)
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
        deleteButton.setOnClickListener { view ->
            // Retrieve the sticker layout (which is the parent of deleteButton)
            val stickerLayout = view.parent as? FrameLayout

            // Check if the stickerLayout exists
            if (stickerLayout != null) {
                // Retrieve the parent of the stickerLayout (usually the main container holding all stickers)
                val parentViewGroup = stickerLayout.parent as? ViewGroup

                // Check if the parent ViewGroup exists and contains the stickerLayout
                if (parentViewGroup != null) {
                    // Remove the sticker layout from the parent
                    parentViewGroup.removeView(stickerLayout)

                    // Optionally, clear any references (like tags) to avoid memory leaks
                    stickerLayout.tag = null
                } else {
                    Log.e("StickerRemoval", "Parent ViewGroup is null or doesn't contain stickerLayout.")
                }
            } else {
                Log.e("StickerRemoval", "StickerLayout is null.")
            }
        }

        // Implement resize logic
        resizeButton.setOnTouchListener { _, event ->
            when (event.action)
            {
                MotionEvent.ACTION_DOWN -> {
                    // Record the initial touch position and size
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    initialWidth = stickerImageView.width.toFloat()
                    initialHeight = stickerImageView.height.toFloat()
                }

                MotionEvent.ACTION_MOVE -> {
                    // Calculate the new width and height based on the drag distance
                    val deltaX = event.rawX - initialTouchX
                    val deltaY = event.rawY - initialTouchY

                    // Calculate new sizes for the sticker
                    val newWidth = initialWidth + deltaX
                    val newHeight = initialHeight + deltaY

                    // Set a minimum size to prevent negative or zero sizes
                    val finalWidth = newWidth.coerceAtLeast(0.1f)
                    val finalHeight = newHeight.coerceAtLeast(0.1f)

                    // Apply scaling to the stickerImageView
                    stickerImageView.scaleX = finalWidth / initialWidth // Maintain aspect ratio
                    stickerImageView.scaleY = finalHeight / initialHeight // Maintain aspect ratio

                    // Update the layout parameters for CustomStickerView
                    val layoutParams = layoutParams as LayoutParams
                    layoutParams.width = (finalWidth * (initialWidth / stickerImageView.width)).toInt()
                    layoutParams.height = (finalHeight * (initialHeight / stickerImageView.height)).toInt()
                    this.layoutParams = layoutParams
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Optionally handle touch release events if needed
                }
            }
            true // Return true to indicate the event was handled
        }

        // Implement copy sticker logic
        copyButton.setOnClickListener {
            // Copy logic here

            copySticker()
        }

        // Implement flip sticker logic
        flipButton.setOnClickListener {
            // Toggle the flip state
            isFlippedHorizontally = !isFlippedHorizontally

            // Apply the scale only to the stickerImageView
            stickerImageView.scaleX = if (isFlippedHorizontally) -1f else 1f // Flip horizontally
        }

        // Dragging logic for the entire CustomStickerView (the whole sticker view moves)
        this.setOnTouchListener { v, event ->
            if (isStickerRemoved) return@setOnTouchListener true // Prevent interaction if removed

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

//        // Click listener to create a new sticker
//        stickerImageView.setOnClickListener {
//            createNewSticker()
//        }
    }


    fun removeSticker() {
        isStickerRemoved = true // Update flag
        this.visibility = View.GONE // Hide or remove from parent
        // Optionally, remove OnTouchListener if necessary
        this.setOnTouchListener(null)
    }

    fun resetSticker() {
        isStickerRemoved = false // Reset flag
        this.visibility = View.VISIBLE // Make sticker visible again if needed
        // Restore OnTouchListener if needed
        this.setOnTouchListener { v, event -> /* your touch logic */ true }
    }


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


//    // Function to create a new layout for each sticker
//    private fun createNewStickerLayout(imageUrl: String)
//    {
//        // Create a new FrameLayout or RelativeLayout for the new sticker
//        val newStickerLayout = FrameLayout(context).apply {
//            layoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
//        }
//
////        // Create a new CustomStickerView for the sticker
////        val newStickerView = CustomStickerView(context)
//
//        // Load the sticker image into the new CustomStickerView
//        Glide.with(this).load(imageUrl).into(newStickerView.findViewById(R.id.stickerImageView))  // Assuming the ImageView ID is stickerImageView
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


    @SuppressLint("ClickableViewAccessibility")
    private fun copySticker() {
        // Create a new instance of CustomStickerView
        val newStickerView = CustomStickerView(context)

        // Copy the current sticker image
        val currentStickerBitmap = getStickerImage()
        newStickerView.setStickerImage(currentStickerBitmap)

        // Get the current layout parameters of the original sticker
        val layoutParams = this.layoutParams as FrameLayout.LayoutParams

        // Set the new sticker's layout params to the same as the original one
        val newLayoutParams = FrameLayout.LayoutParams(
            layoutParams.width, layoutParams.height
        )

        // Set the same position as the original sticker
        newLayoutParams.leftMargin = layoutParams.leftMargin
        newLayoutParams.topMargin = layoutParams.topMargin

        // Assign the layout params to the new sticker
        newStickerView.layoutParams = newLayoutParams

        // Get the parent layout (where the sticker views are added)
        val parentLayout = parent as ViewGroup

        // Add the new sticker to the parent layout
        parentLayout.addView(newStickerView)

        // Optionally, you can reset or update any properties of the new sticker if needed
        newStickerView.resetSticker()

        // Dragging logic for the entire CustomStickerView (the whole sticker view moves)
        newStickerView.setOnTouchListener { v, event ->
            if (isStickerRemoved) return@setOnTouchListener true // Prevent interaction if removed

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
    }

}


