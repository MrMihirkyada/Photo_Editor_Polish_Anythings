//package com.example.photoeditorpolishanything.BeautifyRetouch
//
//import android.graphics.BlurMaskFilter
//import android.content.Context
//import android.graphics.*
//import android.view.MotionEvent
//import android.view.View
//import android.widget.PopupWindow
//import com.example.photoeditorpolishanything.R // Ensure correct import for R
//
//
//
//class AcneTouchListener(
//    context: Context,
//    private val acneView: AcneViewBlemishRemove,
//    private val n0: No
//) : View.OnTouchListener {
//
//    private val b: Int
//    private val c: Int
//    private val d: Int
//    private val f3849m: PopupWindow
//    private val o: Point
//    private var f3848a: C = C.INITIAL
//    private var p: Float = 1.0f
//    private val q = PointF()
//    private val r = PointF()
//    private val t = RunnableC0085a()
//    private val u = RunnableC1505Zw0(this, 1)
//    var s: B? = null
//    var D: Float = 0.0f
//    var E: Float = 0.0f
//    var maskCircleRadius: Float = 0.0f
//    var K: Paint = Paint()
//    var H: Canvas = Canvas()
//    var G: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
//    var y: ViewOnClickListenerC4201uP? = null
//
//    init {
//        val density = context.resources.displayMetrics.density
//        b = (56.0f * density).toInt()
//        c = b
//        d = (15.0f * density).toInt()
//        o = Point(d, 0)
//        n0.setCircleRadius(b)
//        val popupSize = b * 2
//        f3849m = PopupWindow(n0, popupSize, popupSize).apply {
//            animationStyle = R.style.a4l
//        }
//    }
//
//    private class RunnableC0085a : Runnable {
//        override fun run() {
//            val aVar = this@AcneTouchListener
//            if (aVar.f3849m.isShowing) {
//                aVar.n0.removeCallbacks(aVar.u)
//                val n0 = aVar.n0
//                n0.I = true
//                n0.H = 1.0f
//                n0.postInvalidate()
//                if (aVar.f3849m.isShowing) {
//                    aVar.f3849m.update(aVar.o.x, aVar.c, -1, -1)
//                } else {
//                    aVar.f3849m.showAtLocation(aVar.acneView, 51, aVar.o.x, aVar.c)
//                }
//            }
//        }
//    }
//
//    interface B
//
//    enum class C {
//        INITIAL, SECONDARY, TERTIARY;
//
//        companion object {
//            fun valueOf(value: String): C = enumValueOf(value)
//            fun values(): Array<C> = enumValues()
//        }
//    }
//
//    private fun getDistance(event: MotionEvent): Float {
//        return if (event.pointerCount < 2) {
//            0.0f
//        } else {
//            val x = event.getX(0) - event.getX(1)
//            val y = event.getY(0) - event.getY(1)
//            Math.sqrt((x * x + y * y).toDouble()).toFloat()
//        }
//    }
//
//    override fun onTouch(view: View, event: MotionEvent): Boolean {
//        val x = event.x
//        val y = event.y
//        val doubleB = b * 2
//        val dOffset = d.toFloat()
//        val popupSize = doubleB + d
//        val imageRect = acneView.getImageRect()
//
//        when (event.actionMasked) {
//            MotionEvent.ACTION_DOWN -> {
//                if (x >= popupSize || y >= popupSize) {
//                    o.set(view.left + d, view.top)
//                } else {
//                    o.set(view.left + (view.width - popupSize.toInt()), view.top)
//                }
//                f3848a = C.SECONDARY
//                acneView.r = true
//                acneView.I = x
//                acneView.J = y
//                acneView.invalidate()
//                n0.p.offsetTo(x - n0.s, y - n0.s)
//                n0.C = x
//                n0.D = y
//                view.removeCallbacks(t)
//                view.postDelayed(t, 100L)
//            }
//            MotionEvent.ACTION_UP -> {
//                view.removeCallbacks(t)
//                if (f3848a == C.SECONDARY) {
//                    if (imageRect == null || !imageRect.contains(event.x, event.y)) {
//                        acneView.r = false
//                        f3849m.dismiss()
//                    } else {
//                        handleImageTouch(event, x, y)
//                    }
//                }
//                acneView.t = false
//                acneView.invalidate()
//                f3848a = C.INITIAL
//            }
//            MotionEvent.ACTION_MOVE -> {
//                if (f3848a == C.TERTIARY) {
//                    handlePinchZoom(event, x, y)
//                } else if (f3848a == C.SECONDARY) {
//                    acneView.r = true
//                    acneView.I = x
//                    acneView.J = y
//                    acneView.invalidate()
//                    acneView.t = true
//                    acneView.invalidate()
//                    n0.p.offsetTo(x - n0.s, y - n0.s)
//                    n0.C = x
//                    n0.D = y
//                    n0.invalidate()
//                    f3849m.update(o.x, c, -1, -1)
//                }
//            }
//            MotionEvent.ACTION_POINTER_DOWN -> {
//                view.removeCallbacks(t)
//                f3849m.dismiss()
//                acneView.r = false
//                p = getDistance(event)
//                if (p > 10.0f) {
//                    r.set((event.getX(1) + event.getX(0)) / 2.0f, (event.getY(1) + event.getY(0)) / 2.0f)
//                    f3848a = C.TERTIARY
//                } else {
//                    f3848a = C.INITIAL
//                }
//            }
//            MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
//                acneView.postInvalidate()
//                f3848a = C.INITIAL
//                acneView.r = false
//                f3849m.dismiss()
//                acneView.t = false
//                acneView.invalidate()
//            }
//        }
//        return true
//    }
//
//    private fun handleImageTouch(event: MotionEvent, x: Float, y: Float) {
//        val fArr = floatArrayOf(x, y)
//        val fArr2 = floatArrayOf(x, y)
//        val matrix = Matrix()
//        acneView.l.invert(matrix)
//        matrix.mapPoints(fArr)
//        matrix.reset()
//        acneView.d.invert(matrix)
//        matrix.mapPoints(fArr2)
//        val fx = fArr[0] / acneView.D
//        val fy = fArr[1] / acneView.E
//
//        if (fx in 0.0f..1.0f && fy in 0.0f..1.0f) {
//            val maskCircleRadius = acneView.maskCircleRadius
//            acneView.K.setMaskFilter(BlurMaskFilter(maskCircleRadius / 4.0f, BlurMaskFilter.Blur.NORMAL))
//            acneView.H.drawColor(-1)
//            acneView.H.drawCircle(fArr2[0], fArr2[1], maskCircleRadius, acneView.K)
//            acneView.y?.let { acneViewModel ->
//                val bitmap = acneView.G
//                if (acneViewModel is ViewOnClickListenerC4201uP) {
//                    acneViewModel.applyEdits(bitmap)
//                }
//            }
//            matrix.mapPoints(floatArrayOf(x, y))
//        }
//        acneView.r = false
//    }
//
//    private fun handlePinchZoom(event: MotionEvent, x: Float, y: Float) {
//        val distance = getDistance(event)
//        if (distance > 10.0f) {
//            acneView.t = true
//            acneView.invalidate()
//            val scaleFactor = distance / p
//            q.set((event.getX(1) + event.getX(0)) / 2.0f, (event.getY(1) + event.getY(0)) / 2.0f)
//            val midX = (r.x + q.x) / 2.0f
//            val midY = (r.y + q.y) / 2.0f
//            acneView.applyScaling(scaleFactor, midX, midY)
//            p = distance
//            r.set(q)
//        }
//    }
//}