package com.example.photoeditorpolishanything.Template

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircularImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs)
{
    private val path = Path()

    override fun onDraw(canvas: Canvas)
    {
        val radius = Math.min(width, height) / 2.0f
        path.reset()
        path.addCircle(width / 2.0f, height / 2.0f, radius, Path.Direction.CCW)
        canvas.clipPath(path)
        super.onDraw(canvas)
    }
}