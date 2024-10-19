package com.example.photoeditorpolishanything

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CustomSeekBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val lineHeight = 7f.dpToPx(context) // Height of the horizontal line
    private val tickLength = 7f.dpToPx(context) // Length of the tick at position 0
    private val buttonRadius = 10f.dpToPx(context) // Radius of the round button
    private var baseLineY: Float = 0.0f
    private var buttonPosition = 0 // Initial position of the button (from -50 to 50)
    private val minPosition = -50
    private val maxPosition = 50

    interface OnSeekBarChangeListener {
        fun onProgressChanged(progress: Int)
        fun onStartTrackingTouch()
        fun onStopTrackingTouch()
    }

    private var listener: OnSeekBarChangeListener? = null

    fun setOnSeekBarChangeListener(listener: OnSeekBarChangeListener) {
        this.listener = listener
    }

    // Padding values
    private val paddingHorizontal = 20f.dpToPx(context)

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        setPadding(paddingHorizontal.toInt(), 0, paddingHorizontal.toInt(), 0) // Set padding
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredHeight = 50f.dpToPx(context) // Desired height in dp
        val width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, desiredHeight.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        baseLineY = height / 2f

        // Draw the horizontal line in #636262
        paint.color = Color.parseColor("#636262") // Set line color
        val rect = RectF(paddingLeft.toFloat(), baseLineY - lineHeight / 2, width - paddingRight, baseLineY + lineHeight / 2)
        canvas.drawRoundRect(rect, lineHeight / 2, lineHeight / 2, paint)

        // Draw the filled portion from 0 to the button position
        paint.color = Color.WHITE
        val filledStartX = calculateXPosition(0)
        val filledEndX = calculateXPosition(buttonPosition)

        if (buttonPosition >= 0) {
            // Draw white from 0 to button position in the positive range
            canvas.drawRect(filledStartX, baseLineY - lineHeight / 2, filledEndX, baseLineY + lineHeight / 2, paint)
        } else {
            // Draw white from 0 to button position in the negative range
            canvas.drawRect(filledEndX, baseLineY - lineHeight / 2, filledStartX, baseLineY + lineHeight / 2, paint)
        }

        // Draw the tick line at position 0 in white
        paint.color = Color.WHITE // Color for the tick line
        paint.strokeWidth = 2f.dpToPx(context) // Thickness of the tick line
        val tickX = width / 2 // Center position
        canvas.drawLine(tickX, baseLineY - tickLength, tickX, baseLineY + tickLength, paint)

        // Draw the round button in white
        val positionX = calculateXPosition(buttonPosition)
        paint.color = Color.WHITE // Color for the button
        canvas.drawCircle(positionX, baseLineY, buttonRadius, paint)
    }

    private fun calculateXPosition(index: Int): Float {
        val tickSpacing = (width - paddingLeft - paddingRight) / 100f // Space between ticks
        return paddingLeft + (index + 50) * tickSpacing // Convert index to x position
    }

    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        if (event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_DOWN) {
            // Calculate the new position based on touch
            val tickSpacing = (width - paddingLeft - paddingRight) / 100f
            val newIndex = ((event.x - paddingLeft) / tickSpacing).toInt() - 50
            buttonPosition = newIndex.coerceIn(minPosition, maxPosition) // Constrain within -50 to 50
            invalidate() // Redraw the view

            // Notify listener about the progress change
            listener?.onProgressChanged(buttonPosition)

            return true
        }
        return super.onTouchEvent(event)
    }

    private fun Float.dpToPx(context: Context): Float {
        return this * context.resources.displayMetrics.density
    }
}
