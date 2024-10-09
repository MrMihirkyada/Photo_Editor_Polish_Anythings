package com.example.photoeditorpolishanything.Template

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class RoundedRectImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs)
{
    private val path = Path()
    private val rect = RectF()

    override fun onDraw(canvas: Canvas)
    {
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        val radius = 30f // Example corner radius
        path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas.clipPath(path)
        super.onDraw(canvas)
    }
}
