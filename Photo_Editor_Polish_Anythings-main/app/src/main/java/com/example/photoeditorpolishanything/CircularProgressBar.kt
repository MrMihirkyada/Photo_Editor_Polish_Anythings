package com.example.photoeditorpolishanything

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 25 // Default progress

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        // Draw background circle
        paint.color = Color.GRAY
        canvas.drawCircle(width / 2, height / 2, width / 2 - paint.strokeWidth, paint)

        // Draw progress arc
        paint.color = Color.BLUE
        val rect = RectF(paint.strokeWidth, paint.strokeWidth, width - paint.strokeWidth, height - paint.strokeWidth)
        canvas.drawArc(rect, -90f, 360 * (progress / 100f), false, paint)

        // Draw progress text
        paint.color = Color.WHITE
        paint.textSize = 64f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("$progress%", width / 2, height / 2 + 32f, paint)
    }

    // Public method to update progress
    fun setProgress(value: Int) {
        progress = value
        invalidate() // Re-draw the view
    }
}
