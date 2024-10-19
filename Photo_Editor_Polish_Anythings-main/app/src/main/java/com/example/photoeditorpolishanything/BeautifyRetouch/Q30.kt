//package com.example.photoeditorpolishanything.BeautifyRetouch
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Matrix
//import android.graphics.Paint
//import android.graphics.PorterDuff
//import android.graphics.PorterDuffXfermode
//import android.graphics.Rect
//import android.graphics.RectF
//import android.view.View
//
//open class Q30(context: Context) : View(context) {
//    val A: RectF = RectF()
//    var B: Float = 0.0f
//    var C: Float = 0.0f
//    var D: Float = 0.0f
//    var E: Bitmap? = null
//    var F: Matrix = Matrix()
//
//    var f1725a: Float = 1.0f
//    var b: Float = 1.0f
//    var c: Float = 0.0f
//    var d: Float = 0.0f
//    var l: Float = 0.0f
//
//    var f1726m: Float = 0.0f
//    var n: Float = 0.0f
//    var o: Float = 0.0f
//    var p: RectF? = null
//    val q: RectF = RectF()
//    val r: RectF = RectF()
//    var s: Int = 0
//    val t: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
//    val u: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
//    val v: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
//    val w: Matrix = Matrix()
//    var x: RectF? = null
//    val y: RectF = RectF()
//    val z: Rect = Rect()
//
//    init {
//        u.apply {
//            isAntiAlias = true
//            isFilterBitmap = true
//            color = Color.WHITE
//            style = Paint.Style.FILL
//            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
//        }
//
//        v.apply {
//            style = Paint.Style.FILL
//            color = Color.WHITE
//        }
//
//        t.apply {
//            style = Paint.Style.STROKE
//            color = Color.WHITE
//            val scale = C1719bW0.c(context) * 6.0f
//            B = scale
//            strokeWidth = scale
//            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
//        }
//    }
//
//    fun a() {
//        E?.let {
//            l = (it.width / b) * f1725a
//            f1726m = (it.height / b) * f1725a
//        }
//    }
//
//    open fun b(canvas: Canvas) {}
//
//    override fun onDraw(canvas: Canvas) {
//        E?.takeIf { !it.isRecycled }?.let { bitmap ->
//            w.reset()
//            F.invert(w)
//            c = 0.0f
//            d = 0.0f
//            val rectF = p ?: return
//            val f = rectF.top
//            val f2 = r.top
//            if (f < f2) {
//                d = f2 - f
//            }
//            val f3 = rectF.bottom
//            val f4 = r.bottom
//            if (f3 > f4) {
//                d = f4 - f3
//            }
//            val f5 = rectF.left
//            val f6 = r.left
//            if (f5 < f6) {
//                c = f6 - f5
//            }
//            val f7 = rectF.right
//            val f8 = r.right
//            if (f7 > f8) {
//                c = f8 - f7
//            }
//            q.set(rectF)
//            q.offset(c, d)
//            y.let {
//                w.mapRect(it, q)
//                val i = it.left.toInt()
//                val i2 = it.top.toInt()
//                val i3 = it.right.toInt()
//                val i4 = it.bottom.toInt()
//                z.set(i, i2, i3, i4)
//            }
//            val f9 = l
//            val f10 = s.toFloat()
//            val f11 = f10 * 2.0f
//            A.let { rectF5 ->
//                if (f9 < f11) {
//                    val f12 = f9 / 2.0f
//                    x?.let { rectF6 ->
//                        rectF5.set(f10 - f12, rectF6.top, f12 + f10, rectF6.bottom)
//                    }
//                } else {
//                    val f13 = f1726m
//                    if (f13 < f11) {
//                        x?.let { rectF7 ->
//                            val f14 = f13 / 2.0f
//                            rectF5.set(rectF7.left, f10 - f14, rectF7.right, f14 + f10)
//                        }
//                    } else {
//                        x?.let { rectF5.set(it) }
//                    }
//                }
//            }
//            n = (width / 2) - c
//            o = (height / 2) - d
//            val f15 = s * 2.0f
//            if (l < f15) {
//                n = (C - ((r.right + r.left) / 2.0f)) + (width / 2)
//                o = (height / 2) - d
//            } else if (f1726m < f15) {
//                n = (width / 2) - c
//                o = (D - ((r.bottom + r.top) / 2.0f)) + (height / 2)
//            }
//            x?.let { rectF8 ->
//                canvas.drawRoundRect(rectF8, B, B, v)
//                canvas.drawBitmap(bitmap, z, A, u)
//                b(canvas)
//                canvas.drawRoundRect(rectF8, B, B, t)
//            }
//        }
//    }
//
//    fun setBitmap(bitmap: Bitmap?) {
//        E = bitmap
//        postInvalidate()
//    }
//
//    fun setCircleRadius(f: Float) {
//        s = f.toInt()
//        val f2 = f * 2.0f
//        x = RectF(0.0f, 0.0f, f2, f2)
//        p = RectF(0.0f, 0.0f, f2, f2)
//    }
//
//    fun setImageRect(rectF: RectF) {
//        r.set(rectF)
//    }
//
//    fun setMatrix(matrix: Matrix) {
//        F = matrix
//        postInvalidate()
//    }
//
//    fun setOptimalScale(f: Float) {
//        b = f
//        a()
//    }
//
//    fun setScale(f: Float) {
//        f1725a = f
//        a()
//    }
//}