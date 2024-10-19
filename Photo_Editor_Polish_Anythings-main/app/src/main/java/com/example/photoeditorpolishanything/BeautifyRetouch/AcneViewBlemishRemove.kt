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
//import android.graphics.RectF
//import android.util.AttributeSet
//import android.widget.PopupWindow
//
//
//class AcneViewBlemishRemove @JvmOverloads constructor(
//    context: Context,
//    attributeSet: AttributeSet? = null
//) : C2680ib(context, attributeSet) {
//
//    var A: Bitmap? = null
//    var B: Int = 0
//    var C: Int = 0
//    var D: Int = 0
//    var E: Int = 0
//    var F: RectF = RectF()
//    var G: Bitmap? = null
//    var H: Canvas? = null
//    var I: Float = 0.0f
//    var J: Float = 0.0f
//    var K: Paint? = null
//    var p: Float = 0.0f
//    var q: Float = 0.0f
//    var r: Boolean = false
//    var s: Paint? = null
//    var t: Boolean = true
//    val u: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
//    var v: Float = 1.0f
//    var w: Boolean = false
//    var x: No? = null
//    var y: a? = null
//    var z: a? = null
//
////    interface a
//
//    override fun b() {
//        o = false
//        d = Matrix()
//        f4930m = Matrix()
//        l = Matrix()
//        x = No(context)
//        val aVar = a(context, this, x!!)
//        z = aVar
//        setOnTouchListener(aVar)
//        isFocusable = true
//
//        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
//        s = paint.apply {
//            style = Paint.Style.STROKE
//            color = Color.WHITE
//            isAntiAlias = true
//            strokeWidth = C1719bW0.c(context) * 2.0f
//        }
//
//        val paint2 = Paint().apply {
//            color = Color.RED
//            style = Paint.Style.FILL
//            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
//        }
//        K = paint2
//    }
//
//
//    fun c(canvas: Canvas) {
//        C2957kl0.a(C4140tx.d("EWMBZWBpV3dkUy52ZQ==", "qNPo62Mm"))
//        val matrix = Matrix()
//        if (canvas.width != B || canvas.height != C) {
//            val width = canvas.width / B.toFloat()
//            matrix.postScale(width, width)
//        }
//        A?.let { canvas.drawBitmap(it, matrix, u) }
//    }
//
//    fun getCircleRadius(): Float {
//        return C1719bW0.c(context) * 13.0f
//    }
//
//    fun getCurrentImage(): Bitmap? {
//        return if (A == null || A!!.isRecycled) null else A
//    }
//
//    fun getImageRect(): RectF {
//        return F
//    }
//
//    fun getMaskCircleRadius(): Float {
//        val values = FloatArray(16)
//        d.getValues(values)
//        return getCircleRadius() / values[0]
//    }
//
//    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        z?.let { aVar ->
//            (aVar.f3849m as? PopupWindow)?.dismiss()
//        }
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        throw UnsupportedOperationException("Method not decompiled: com.camerasideas.collagemaker.photoproc.editorview.blemish.AcneView.onDraw(android.graphics.Canvas):void")
//    }
//
//    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
//        if (width <= 0 || height <= 0 || !o || A == null) return
//
//        f4929a = width
//        b = height
//        val f = width.toFloat()
//        val f2 = height.toFloat()
//        val f3 = B.toFloat()
//        val f4 = C.toFloat()
//        val f5 = if (f / f2 < f3 / f4) f3 / f else f4 / f2
//        x?.setOptimalScale(f5)
//        D = (B / f5).toInt()
//        E = (C / f5).toInt()
//        val f6 = 1.0f / f5
//        d.setScale(f6, f6)
//        val f7 = (f4929a - D) / 2
//        val f8 = (b - E) / 2
//        this.d.postTranslate(7f, 8f)
//        l.postTranslate(7f, 8f)
//        a()
//        F = RectF(0.0f, 0.0f, B.toFloat(), C.toFloat()).apply {
//            d.mapRect(this)
//        }
//        x?.setImageRect(F)
//        if (G == null) {
//            G = Bitmap.createBitmap(B, C, Bitmap.Config.ALPHA_8)
//        }
//        if (H == null) {
//            H = Canvas(G!!)
//        }
//        o = true
//    }
//
//    fun setAnimEndListener(listener: a.b?) {
//        z?.s = listener
//    }
//
//    fun setImage(bitmap: Bitmap?) {
//        bitmap?.let {
//            A = it
//            x?.setBitmap(it)
//            B = it.width
//            C = it.height
//            invalidate()
//        }
//    }
//
//    fun setOnAcneListener(listener: a?) {
//        y = listener
//    }
//}
