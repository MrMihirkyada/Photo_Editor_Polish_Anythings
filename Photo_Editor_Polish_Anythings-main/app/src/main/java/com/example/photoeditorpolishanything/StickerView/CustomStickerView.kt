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
import androidx.core.view.updateLayoutParams
import com.example.photoeditorpolishanything.R
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

@SuppressLint("ResourceType")
class CustomStickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private var stickerLayout : View
    private var stickerImageView: ImageView
    private var deleteButton: ImageView
    private var resizeButton: ImageView
    private var copyButton: ImageView
    private var flipButton: ImageView
    private var currentStickerView: CustomStickerView? = null
    private lateinit var stickerMainLayout: FrameLayout


    private var isStickerRemoved = false
    private var dX = 0f
    private var dY = 0f

    private var isFlippedHorizontally = false // Track horizontal flip state
    private var isFlippedVertically = false // Track vertical flip state

    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var initialWidth = 0f
    private var initialHeight = 0f
    var initialLayoutWidth = 0f
    var initialLayoutHeight = 0f
    var initialImageWidth = 0f // Add this to store stickerImageView's initial width
    var initialImageHeight = 0f // Add this to store stickerImageView's initial height

    private var initialAngle = 0f
    private var startRotation = 0f


    private var lastAngle = 0f
    private var lastScale = 1f
    private var isMultiTouch = false

    private  val MIN_SCALE = 0.5f  // 50% of original size
    private  val MAX_SCALE = 3.0f  // 300% of original size
    private var originalWidth = 0f
    private var originalHeight = 0f

    var currentRotation = 0f // Track current rotation angle
    var initialRotation = 0f // Store initial rotation angle
    var initialFingerDistance = 0f // Store the distance between fingers when rotation starts

    // Define a padding for the clickable area around the delete button
    val touchAreaPadding = 60 // Adjust this value for the desired clickable area

    private var initialAspectRatio = 0f


    private lateinit var stickerContainer: FrameLayout



    init {

        // Dynamically create views instead of using XML
        stickerImageView = ImageView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        addView(stickerImageView)

        // Inflate the sticker layout
        LayoutInflater.from(context).inflate(R.layout.sticker_layout, this, true)
        stickerLayout = findViewById(R.id.stickerLayout)
        deleteButton = findViewById(R.id.imgDeleteButton)
        resizeButton = findViewById(R.id.imgResizeButton)
        copyButton = findViewById(R.id.imgCopy)
        flipButton = findViewById(R.id.imgHorizontal)

        LayoutInflater.from(context).inflate(R.layout.sticker_view_layout, this, true)
        stickerImageView = findViewById(R.id.stickerImageView)

        stickerContainer = findViewById(R.id.stickerLayout)

//        initializeView()
        setupListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners()
    {

//        stickerImageView.setOnClickListener {
//            // Show the layout if it is hidden, otherwise hide it
//            if (deleteButton.visibility == View.GONE  &&
//                resizeButton.visibility == View.GONE &&
//                copyButton.visibility == View.GONE &&
//                flipButton.visibility == View.GONE)
//            {
//                deleteButton.visibility = View.VISIBLE
//                resizeButton.visibility = View.VISIBLE
//                copyButton.visibility = View.VISIBLE
//                flipButton.visibility = View.VISIBLE
//            } else {
//                deleteButton.visibility = View.GONE
//                resizeButton.visibility = View.GONE
//                copyButton.visibility = View.GONE
//                flipButton.visibility = View.GONE
//            }
//        }



        // Delete sticker
        deleteButton.setOnTouchListener(object : OnTouchListener{
            override fun onTouch(view : View?, event: MotionEvent?): Boolean {

                // Retrieve the sticker layout (which is the parent of deleteButton)
                val stickerLayout = view!!.parent as? FrameLayout

                // Check if the stickerLayout exists
                if (stickerLayout != null)
                {
                    // Retrieve the parent of the stickerLayout (usually the main container holding all stickers)
                    val parentViewGroup = stickerLayout.parent as? ViewGroup

                    // Check if the parent ViewGroup exists and contains the stickerLayout
                    if (parentViewGroup != null)
                    {
                        // Set stickerImageView visibility to GONE (or remove it if necessary)
                        stickerImageView.visibility = View.GONE // This hides the stickerImageView

                        // Alternatively, if you want to completely remove it, use:
                        // stickerLayout.removeView(stickerImageView)  // This will remove the image from the layout

                        // Now remove the sticker layout from the parent
                        parentViewGroup.removeView(stickerLayout)

                        // Optionally, clear any references (like tags) to avoid memory leaks
                        stickerLayout.tag = null
                    }
                    else
                    {
                        Log.e("StickerRemoval", "Parent ViewGroup is null or doesn't contain stickerLayout.")
                    }
                }
                else
                {
                    Log.e("StickerRemoval", "StickerLayout is null.")
                }


                // Determine the center of the divider
                val dividerCenterX = deleteButton.x + deleteButton.width / 2

                // Check if touch is within the wider touch area (30 pixels on each side of the divider)
                if (event!!.rawX in (dividerCenterX - 60)..(dividerCenterX + 60)) { // 60 pixels on either side
                    dX = deleteButton.x - event.rawX
                    return true
                }
                return false
            }
        })

        /*{
            // Retrieve the sticker layout (which is the parent of deleteButton)
            val stickerLayout = view.parent as? FrameLayout

            // Check if the stickerLayout exists
            if (stickerLayout != null)
            {
                // Retrieve the parent of the stickerLayout (usually the main container holding all stickers)
                val parentViewGroup = stickerLayout.parent as? ViewGroup

                // Check if the parent ViewGroup exists and contains the stickerLayout
                if (parentViewGroup != null)
                {
                    // Set stickerImageView visibility to GONE (or remove it if necessary)
                    stickerImageView.visibility = View.GONE // This hides the stickerImageView

                    // Alternatively, if you want to completely remove it, use:
                    // stickerLayout.removeView(stickerImageView)  // This will remove the image from the layout

                    // Now remove the sticker layout from the parent
                    parentViewGroup.removeView(stickerLayout)

                    // Optionally, clear any references (like tags) to avoid memory leaks
                    stickerLayout.tag = null
                }
                else
                {
                    Log.e("StickerRemoval", "Parent ViewGroup is null or doesn't contain stickerLayout.")
                }
            }
            else
            {
                Log.e("StickerRemoval", "StickerLayout is null.")
            }

            // Determine the center of the divider
            val dividerCenterX = deleteButton.x + deleteButton.width / 2

            // Check if touch is within the wider touch area (30 pixels on each side of the divider)
            if (event.rawX in (dividerCenterX - 60)..(dividerCenterX + 60)) { // 60 pixels on either side
                dX = dividerHandle.x - event.rawX
                return true
            }

        }*/


        stickerMainLayout = findViewById(R.id.sticker_main_layout)

//        resizeButton.setOnTouchListener { _, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    // Record initial values
//                    initialTouchX = event.rawX
//                    initialTouchY = event.rawY
//                    initialLayoutWidth = stickerMainLayout.width.toFloat()
//                    initialLayoutHeight = stickerMainLayout.height.toFloat()
//                    initialAspectRatio = initialLayoutWidth / initialLayoutHeight
//
//                    // Store original dimensions if not already stored
//                    if (originalWidth == 0f) {
//                        originalWidth = initialLayoutWidth
//                        originalHeight = initialLayoutHeight
//                    }
//                }
//
//                MotionEvent.ACTION_MOVE -> {
//                    // Calculate drag distance
//                    val deltaX = event.rawX - initialTouchX
//                    val deltaY = event.rawY - initialTouchY
//
//                    // Determine which dimension should drive the resize
//                    val horizontalScale = (initialLayoutWidth + deltaX) / initialLayoutWidth
//                    val verticalScale = (initialLayoutHeight + deltaY) / initialLayoutHeight
//
//                    // Use the larger scale factor to maintain aspect ratio
//                    val scale = maxOf(horizontalScale, verticalScale)
//
//                    // Calculate the current scale relative to original size
//                    var currentScale = (initialLayoutWidth * scale) / originalWidth
//
//                    // Clamp the scale within limits
//                    currentScale = currentScale.coerceIn(MIN_SCALE, MAX_SCALE)
//
//                    // Calculate new dimensions based on clamped scale
//                    var newWidth = (originalWidth * currentScale).coerceAtLeast(50f)
//                    var newHeight = (newWidth / initialAspectRatio).coerceAtLeast(50f)
//
//                    // If height-driven resize would exceed minimum width, recalculate based on width
//                    if (newHeight < 50f) {
//                        newHeight = 50f
//                        newWidth = newHeight * initialAspectRatio
//                    }
//
//                    // Optional: Add visual feedback for scale limits
//                    if (currentScale >= MAX_SCALE || currentScale <= MIN_SCALE) {
//                        // You could add a subtle animation or color change here
//                        resizeButton.alpha = 0.5f
//                    } else {
//                        resizeButton.alpha = 1.0f
//                    }
//
//                    // Update main layout
//                    stickerMainLayout.updateLayoutParams<LayoutParams> {
//                        width = newWidth.toInt()
//                        height = newHeight.toInt()
//                    }
//
//                    // Update image view to match
//                    stickerImageView.updateLayoutParams<LayoutParams> {
//                        width = newWidth.toInt()
//                        height = newHeight.toInt()
//                    }
//                }
//
//                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                    // Reset any visual feedback
//                    resizeButton.alpha = 1.0f
//                }
//            }
//            true
//        }




        resizeButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Record initial values
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    initialLayoutWidth = stickerMainLayout.width.toFloat()
                    initialLayoutHeight = stickerMainLayout.height.toFloat()
                    initialAspectRatio = initialLayoutWidth / initialLayoutHeight

                    // Store original dimensions if not already stored
                    if (originalWidth == 0f) {
                        originalWidth = initialLayoutWidth
                        originalHeight = initialLayoutHeight
                    }

                        // Determine the center of the divider
                        val dividerCenterX = stickerMainLayout.x + stickerMainLayout.width / 2

                        // Check if touch is within the wider touch area (30 pixels on each side of the divider)
                        if (event.rawX in (dividerCenterX - 60)..(dividerCenterX + 60)) { // 60 pixels on either side
                            dX = stickerMainLayout.x - event.rawX
                        }
                }

                MotionEvent.ACTION_MOVE -> {
                    if (event.pointerCount == 1) {
                        // Existing resize logic
                        val deltaX = event.rawX - initialTouchX
                        val deltaY = event.rawY - initialTouchY

                        // Determine which dimension should drive the resize
                        val horizontalScale = (initialLayoutWidth + deltaX) / initialLayoutWidth
                        val verticalScale = (initialLayoutHeight + deltaY) / initialLayoutHeight

                        // Use the larger scale factor to maintain aspect ratio
                        val scale = maxOf(horizontalScale, verticalScale)

                        // Calculate the current scale relative to original size
                        var currentScale = (initialLayoutWidth * scale) / originalWidth

                        // Clamp the scale within limits
                        currentScale = currentScale.coerceIn(MIN_SCALE, MAX_SCALE)

                        // Calculate new dimensions based on clamped scale
                        var newWidth = (originalWidth * currentScale).coerceAtLeast(50f)
                        var newHeight = (newWidth / initialAspectRatio).coerceAtLeast(50f)

                        // If height-driven resize would exceed minimum width, recalculate based on width
                        if (newHeight < 50f) {
                            newHeight = 50f
                            newWidth = newHeight * initialAspectRatio
                        }

                        // Optional: Add visual feedback for scale limits
                        resizeButton.alpha = if (currentScale >= MAX_SCALE || currentScale <= MIN_SCALE) 0.5f else 1.0f

                        // Update main layout
                        stickerMainLayout.updateLayoutParams<LayoutParams> {
                            width = newWidth.toInt()
                            height = newHeight.toInt()
                        }

                        // Update image view to match
                        stickerImageView.updateLayoutParams<LayoutParams> {
                            width = newWidth.toInt()
                            height = newHeight.toInt()
                        }
                    } else if (event.pointerCount == 2) {
                        // Handle rotation with two fingers
                        val rotationAngle = calculateRotation(event)
                        currentRotation += rotationAngle
                        stickerMainLayout.rotation = currentRotation
                    }
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Reset any visual feedback
                    resizeButton.alpha = 1.0f
                }
            }
            true
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

//         Dragging logic for the entire CustomStickerView (the whole sticker view moves)
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


    private fun getStickerImage(): Bitmap  {
        // Check if the ImageView has a drawable
        val drawable = stickerImageView.drawable
        if (drawable != null) {
            // Create a Bitmap from the drawable
            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

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
    private fun copySticker()
    {
        // Create a new instance of CustomStickerView
        val newStickerView = CustomStickerView(context)

        // Copy the current sticker image
        val currentStickerBitmap = getStickerImage()
        newStickerView.setStickerImage(currentStickerBitmap)

        // Get the current layout parameters of the original sticker
        val layoutParams = this.layoutParams as LayoutParams

        // Set the new sticker's layout params to the same as the original one
        val newLayoutParams = LayoutParams(layoutParams.width, layoutParams.height)

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

            when (event.action)
            {
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

    // Helper function to smoothly constrain resizing
    private fun getConstrainedSize(size: Float, minSize: Float): Float {
        return if (size < minSize) {
            minSize + (1 - (minSize / size).coerceAtMost(1f)) * (size - minSize)
        } else {
            size
        }
    }


    // Helper function to calculate angle between two fingers
    private fun getTwoFingerAngle(event: MotionEvent): Float {
        val deltaX = event.getX(0) - event.getX(1)
        val deltaY = event.getY(0) - event.getY(1)
        return Math.toDegrees(atan2(deltaY.toDouble(), deltaX.toDouble())).toFloat()
    }

    // Helper function to calculate distance between two fingers
    private fun getTwoFingerDistance(event: MotionEvent): Float {
        val deltaX = event.getX(0) - event.getX(1)
        val deltaY = event.getY(0) - event.getY(1)
        return sqrt(deltaX * deltaX + deltaY * deltaY)
    }

    // Helper function to update layout dimensions
    private fun updateLayoutDimensions(width: Int, height: Int) {
        stickerMainLayout.updateLayoutParams<LayoutParams> {
            this.width = width
            this.height = height
        }

        stickerImageView.updateLayoutParams<LayoutParams> {
            this.width = width
            this.height = height
        }
    }

    // Function to calculate rotation angle based on the positions of the two fingers
    private fun calculateRotation(event: MotionEvent): Float {
        val x1 = event.getX(0)
        val y1 = event.getY(0)
        val x2 = event.getX(1)
        val y2 = event.getY(1)

        // Calculate the angle of the two fingers
        val angle = Math.toDegrees(atan2((y2 - y1).toDouble(), (x2 - x1).toDouble())).toFloat()
        return angle - initialRotation // Adjust with the initial rotation
    }

}


