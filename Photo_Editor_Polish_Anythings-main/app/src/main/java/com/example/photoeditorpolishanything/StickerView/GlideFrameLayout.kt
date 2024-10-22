//package com.example.photoeditorpolishanything.StickerView
//
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.drawable.Drawable
//import android.util.AttributeSet
//import android.widget.FrameLayout
//
//// First, create a custom FrameLayout that can display images
//class GlideFrameLayout @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : FrameLayout(context, attrs, defStyleAttr) {
//
//    init {
//        // Make sure the FrameLayout can draw
//        setWillNotDraw(false)
//    }
//
//    private var drawable: Drawable? = null
//
//    fun setDrawable(drawable: Drawable?) {
//        this.drawable = drawable
//        invalidate()
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        drawable?.let {
//            it.setBounds(0, 0, width, height)
//            it.draw(canvas)
//        }
//    }
//}
