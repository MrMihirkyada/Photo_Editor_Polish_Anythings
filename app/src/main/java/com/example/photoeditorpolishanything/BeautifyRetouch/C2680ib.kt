//package com.example.photoeditorpolishanything.BeautifyRetouch
//
//import android.content.Context
//import android.graphics.Matrix
//import android.util.AttributeSet
//import android.view.View
//
//open class C2680ib(context: Context, attrs: AttributeSet?) : View(context, attrs) {
//
//    var f4929a: Int = 0
//    var b: Int = 0
//    var c: Float = 1.0f
//    var d: Matrix = Matrix()
//    var l: Matrix = Matrix()
//    var f4930m: Matrix = Matrix()
//    var n: FloatArray? = null
//    var o: Boolean = false
//
//    init {
//        b()
//    }
//
//    private fun getGlScale(): Float {
//        return n?.get(0) ?: 1.0f
//    }
//
//    private fun getGlTranslateX(): Float {
//        return n?.get(12) ?: 0.0f
//    }
//
//    private fun getGlTranslateY(): Float {
//        return n?.get(13) ?: 0.0f
//    }
//
//    fun a() {
//        n?.let {
//            d.postScale(getGlScale(), getGlScale(), f4929a / 2f, b / 2f)
//            l.postScale(getGlScale(), getGlScale(), f4929a / 2f, b / 2f)
//            f4930m.postScale(getGlScale(), getGlScale(), f4929a / 2f, b / 2f)
//            d.postTranslate(getGlTranslateX() * f4929a / 2f, -getGlTranslateY() * b / 2f)
//            l.postTranslate(getGlTranslateX() * f4929a / 2f, -getGlTranslateY() * b / 2f)
//            f4930m.postTranslate(getGlTranslateX() * f4929a / 2f, -getGlTranslateY() * b / 2f)
//            c = getGlScale()
//        } ?: run {
//            c = 1.0f
//        }
//    }
//
//    open fun b() {
//        // Placeholder method
//    }
//
//    fun getScale(): Float {
//        return c
//    }
//
//    fun setImgProjection(fArr: FloatArray?) {
//        n = fArr
//    }
//}