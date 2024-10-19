package com.example.photoeditorpolishanything

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Vibrator
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class RulerScaleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    // Existing properties
    private val paint = Paint()
    private val majorTickLength = 15f.dpToPx(context)
    private val minorTickLength = 5f.dpToPx(context)
    private val midMinorTickLength = 10f.dpToPx(context) // Slightly longer tick
    private val labelTextSize = 10f.dpToPx(context)
    private val paddingHorizontal = 15f.dpToPx(context)
    private val majorTickStrokeWidth = 1.5f.dpToPx(context) // Major tick thickness
    private val minorTickStrokeWidth = 1f.dpToPx(context) // Minor tick thickness
    private var baseLineY: Float = 0.0f
    private var selectedIndex: Int = 0 // -1 means no selection
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    // Listener for index changes
    interface OnSelectedIndexChangeListener {
        fun onSelectedIndexChange(index: Int)
    }

    private var listener: OnSelectedIndexChangeListener? = null

    fun setOnSelectedIndexChangeListener(listener: OnSelectedIndexChangeListener) {
        this.listener = listener
    }

    init {
        paint.textSize = labelTextSize
        setPadding(paddingHorizontal.toInt(), 0, paddingHorizontal.toInt(), 0)
    }

    fun setSelectedIndex(index: Int) {
        selectedIndex = index.coerceIn(-50, 50) // Ensure the index is within the range
        if (selectedIndex.toDouble() == 0.0) {
            // Vibrate for 100 milliseconds
            vibrator.vibrate(30)
        }
        listener?.onSelectedIndexChange(selectedIndex) // Notify listener
        invalidate() // Redraw the view
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
            val tickSpacing = (width - paddingLeft - paddingRight) / 100f
            val index = ((event.x - paddingLeft) / tickSpacing).toInt() - 50
            setSelectedIndex(index)

            // Notify the listener of the index change
            listener?.onSelectedIndexChange(index)

            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredHeight = 50f.dpToPx(context) // Desired height in dp
        val width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, desiredHeight.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.WHITE // Default color for lines and text
        baseLineY = height / 2f
        val tickSpacing = (width - paddingLeft - paddingRight) / 100f
        paint.strokeWidth = minorTickStrokeWidth
        canvas.drawLine(paddingLeft.toFloat(), baseLineY, width - paddingRight.toFloat(), baseLineY, paint)
        for (i in -50..50) {
            val x = paddingLeft + (i + 50) * tickSpacing
            val (tickLength, strokeWidth) = when {
                i % 10 == 0 -> majorTickLength to majorTickStrokeWidth // Major tick
                i % 5 == 0 -> midMinorTickLength to minorTickStrokeWidth // Slightly longer minor tick at halfway
                else -> minorTickLength to minorTickStrokeWidth // Regular minor tick
            }
            paint.color = if (i == selectedIndex) Color.BLUE else Color.WHITE
            paint.strokeWidth = strokeWidth
            canvas.drawLine(x, baseLineY - tickLength, x, baseLineY + tickLength, paint)
            if (i % 10 == 0) {
                val label = (i / 2).toString() // Adjust label to match major ticks
                val textWidth = paint.measureText(label)
                canvas.drawText(label, x - textWidth / 2f, baseLineY + majorTickLength + labelTextSize, paint)
            }
        }
    }


    // Extension function to convert dp to pixels
    fun Float.dpToPx(context: Context): Float {
        return this * context.resources.displayMetrics.density
    }

}