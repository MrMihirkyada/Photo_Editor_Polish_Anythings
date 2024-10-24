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
import androidx.core.view.size
import androidx.core.view.updateLayoutParams
import com.example.photoeditorpolishanything.R
import com.example.photoeditorpolishanything.Sticker_Activity.Companion.binding
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

@SuppressLint("ResourceType")
class CustomStickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private var stickerLayout : View
    private var stickerLayoutView : FrameLayout
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
    private var lastTouchAngle = 0f
    var initialRotation = 0f // Store initial rotation angle
    var initialFingerDistance = 0f // Store the distance between fingers when rotation starts

    // Define a padding for the clickable area around the delete button
    val touchAreaPadding = 60 // Adjust this value for the desired clickable area

    private var initialAspectRatio = 0f


    // Add these properties at the class level
    private var previousRotation = 0f
    private var pivotX = 0f
    private var pivotY = 0f


    private var isRotating = false

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
        stickerLayoutView = findViewById(R.id.stickerLayoutview)
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
        stickerMainLayout = findViewById(R.id.sticker_main_layout)
        stickerMainLayout = findViewById(R.id.sticker_main_layout)


//        //      Dragging logic for the entire CustomStickerView (the whole sticker view moves)
//        this.setOnTouchListener { v, event ->
//            if (isStickerRemoved) return@setOnTouchListener true // Prevent interaction if removed
//
////            stickerImageView.setOnTouchListener {
////            }
//            when (event.action)
//            {
//                MotionEvent.ACTION_DOWN -> {
//                    dX = v.x - event.rawX
//                    dY = v.y - event.rawY
//                }
//
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


        val stickerContainer: ViewGroup = findViewById(R.id.stickerLayoutview) // Replace with your actual container ID

        binding.imgEditSelectImagess.setOnClickListener {
            // Iterate through all children of stickerContainer
            for (i in 0 until stickerContainer.childCount) {
                // Get the sticker layout at index i
                val stickerLayout = stickerContainer.getChildAt(i)
                // Set its visibility to GONE
                stickerLayout.visibility = View.GONE
            }
        }


        binding.imgEditSelectImagess.setOnClickListener {
            stickerLayoutView.visibility = View.GONE // Hide each sticker layout
        }


        stickerMainLayout.setOnClickListener {
            if(stickerLayoutView.visibility == View.GONE)
            {
                stickerLayoutView.visibility = View.VISIBLE
            }
            else
            {
                stickerLayoutView.visibility = View.GONE
            }
        }



//        stickerMainLayout.setOnClickListener {
//
//            if(stickerLayoutView.visibility == View.GONE)
//            {
//                stickerLayoutView.visibility = View.VISIBLE
//            }
//            else
//            {
//                stickerLayoutView.visibility = View.GONE
//            }
//        }


        // Delete sticker with extended touch area
        deleteButton.setOnClickListener{ view ->

            // Get the current layout parameters for the button (width and height)
            var widths = deleteButton.layoutParams.width.toFloat()
            var heights = deleteButton.layoutParams.height.toFloat()

            // Get the padding values (left, right, top, and bottom)
            val paddingLeft = deleteButton.paddingLeft
            val paddingRight = deleteButton.paddingRight
            val paddingTop = deleteButton.paddingTop
            val paddingBottom = deleteButton.paddingBottom

            // Adjust the width and height to include padding
            val totalWidthWithPadding = widths + paddingLeft + paddingRight
            val totalHeightWithPadding = heights + paddingTop + paddingBottom

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
            else {
                Log.e("StickerRemoval", "StickerLayout is null.")
            }
        }

//        {
//            // Retrieve the sticker layout (which is the parent of deleteButton)
//            val stickerLayout = view.parent as? FrameLayout
//
//            // Check if the stickerLayout exists
//            if (stickerLayout != null)
//            {
//                // Retrieve the parent of the stickerLayout (usually the main container holding all stickers)
//                val parentViewGroup = stickerLayout.parent as? ViewGroup
//
//                // Check if the parent ViewGroup exists and contains the stickerLayout
//                if (parentViewGroup != null)
//                {
//                    // Set stickerImageView visibility to GONE (or remove it if necessary)
//                    stickerImageView.visibility = View.GONE // This hides the stickerImageView
//
//                    // Alternatively, if you want to completely remove it, use:
//                    // stickerLayout.removeView(stickerImageView)  // This will remove the image from the layout
//
//                    // Now remove the sticker layout from the parent
//                    parentViewGroup.removeView(stickerLayout)
//
//                    // Optionally, clear any references (like tags) to avoid memory leaks
//                    stickerLayout.tag = null
//                }
//                else
//                {
//                    Log.e("StickerRemoval", "Parent ViewGroup is null or doesn't contain stickerLayout.")
//                }
//            }
//            else
//            {
//                Log.e("StickerRemoval", "StickerLayout is null.")
//            }
//
//            // Determine the center of the divider
//            val dividerCenterX = deleteButton.x + deleteButton.width / 2
//
//            // Check if touch is within the wider touch area (30 pixels on each side of the divider)
//            if (event.rawX in (dividerCenterX - 60)..(dividerCenterX + 60)) { // 60 pixels on either side
//                dX = dividerHandle.x - event.rawX
//                return true
//            }
//
//        }


        resizeButton.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    // Record initial values for resize and rotation
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    initialLayoutWidth = stickerMainLayout.width.toFloat()
                    initialLayoutHeight = stickerMainLayout.height.toFloat()
                    initialAspectRatio = initialLayoutWidth / initialLayoutHeight

                    // Calculate the center of the sticker for rotation
                    val centerX = stickerMainLayout.x + stickerMainLayout.width / 2
                    val centerY = stickerMainLayout.y + stickerMainLayout.height / 2

                    // Calculate initial angle between touch point and center
                    lastTouchAngle = getAngleFromPoint(centerX, centerY, event.rawX, event.rawY)

                    // Store original dimensions if not already stored
                    if (originalWidth == 0f)
                    {
                        originalWidth = initialLayoutWidth
                        originalHeight = initialLayoutHeight
                    }

                    // Start rotation mode
                    isRotating = true
                }

                MotionEvent.ACTION_MOVE -> {
                    if (isRotating) {
                        // Calculate the center of the sticker for rotation
                        val centerX = stickerMainLayout.x + stickerMainLayout.width / 2
                        val centerY = stickerMainLayout.y + stickerMainLayout.height / 2

                        // Get the current angle between the touch point and the sticker's center
                        val currentTouchAngle = getAngleFromPoint(centerX, centerY, event.rawX, event.rawY)

                        // Calculate the angle difference for smooth 360-degree rotation
                        val rotationDelta = currentTouchAngle - lastTouchAngle

                        // Update the current rotation
                        currentRotation += rotationDelta

                        // Normalize the rotation to keep it within 0 to 360 degrees
                        currentRotation = (currentRotation + 360) % 360

                        // Apply the rotation to both the layout and image view (sticker)
                        stickerMainLayout.rotation = currentRotation
                        stickerLayoutView.rotation = currentRotation

                        // Save the current angle for the next calculation
                        lastTouchAngle = currentTouchAngle
                    }

                    // Handle resizing logic simultaneously as before
                    val deltaX = event.rawX - initialTouchX
                    val deltaY = event.rawY - initialTouchY

                    val horizontalScale = (initialLayoutWidth + deltaX) / initialLayoutWidth
                    val verticalScale = (initialLayoutHeight + deltaY) / initialLayoutHeight
                    val scale = maxOf(horizontalScale, verticalScale)

                    var currentScale = (initialLayoutWidth * scale) / originalWidth
                    currentScale = currentScale.coerceIn(MIN_SCALE, MAX_SCALE)

                    var newWidth = (originalWidth * currentScale).coerceAtLeast(50f)
                    var newHeight = (newWidth / initialAspectRatio).coerceAtLeast(50f)

                    if (newHeight < 50f)
                    {
                        newHeight = 50f
                        newWidth = newHeight * initialAspectRatio
                    }

                    // Apply the new dimensions
                    stickerMainLayout.updateLayoutParams<LayoutParams>
                    {
                        width = newWidth.toInt()
                        height = newHeight.toInt()
                    }

                    stickerImageView.updateLayoutParams<LayoutParams>
                    {
                        width = newWidth.toInt()
                        height = newHeight.toInt()
                    }
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isRotating = false
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

//      Dragging logic for the entire CustomStickerView (the whole sticker view moves)
        this.setOnTouchListener { v, event ->
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


    // Add this function to calculate angle between two points
    private fun calculateAngle(x: Float, y: Float, pivotX: Float, pivotY: Float): Float {
        val deltaX = x - pivotX
        val deltaY = y - pivotY
        val degrees = Math.toDegrees(atan2(deltaY, deltaX).toDouble())
        return degrees.toFloat()
    }

    // Add this helper function to calculate angle
    private fun getAngleFromPoint(centerX: Float, centerY: Float, touchX: Float, touchY: Float): Float {
        val deltaX = touchX - centerX
        val deltaY = touchY - centerY
        val radians = kotlin.math.atan2(deltaY, deltaX)
        var degrees = Math.toDegrees(radians.toDouble()).toFloat()
        if (degrees < 0) {
            degrees += 360f
        }
        return degrees
    }
}


