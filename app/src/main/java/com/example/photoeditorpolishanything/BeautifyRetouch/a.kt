//package com.example.photoeditorpolishanything.BeautifyRetouch
//
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BlurMaskFilter
//import android.graphics.Matrix
//import android.graphics.Point
//import android.graphics.PointF
//import android.graphics.PorterDuff
//import android.view.MotionEvent
//import android.view.View
//import android.widget.PopupWindow
//import com.example.photoeditorpolishanything.R
//
//class a(
//    context: Context,
//    val acneView: AcneViewBlemishRemove,
//    val n0: No
//) : View.OnTouchListener {
//
//    private val b: Int
//    private val c: Int
//    private val d: Int
//    private val o: Point
//    private val popupWindow: PopupWindow
//    val f3849m: PopupWindow? = null
//    private var state: C = C.FIRST
//    private var p: Float = 1.0f
//    private val q: PointF = PointF()
//    private val r: PointF = PointF()
//    private val touchRunnable = TouchRunnable()
//    private val delayedInvalidateRunnable = RunnableC1505Zw0(this, 1)
//    var s: b? = null
//
//    init {
//        val c2 = (C1719bW0.c(context) * 56.0f).toInt()
//        b = c2
//        c = c2
//        val i = (context.resources.displayMetrics.density * 15.0f).toInt()
//        d = i
//        o = Point(i, 0)
//        n0.circleRadius = c2
//        val i2 = c2 * 2
//        popupWindow = PopupWindow(n0, i2, i2).apply {
//            animationStyle = R.style.a4l
//        }
//    }
//
//    private inner class TouchRunnable : Runnable {
//        override fun run() {
//            if (popupWindow.isShowing) {
//                n0.removeCallbacks(delayedInvalidateRunnable)
//                n0.I = true
//                n0.H = 1.0f
//                n0.postInvalidate()
//                val i = c
//                if (popupWindow.isShowing) {
//                    popupWindow.update(o.x, i, -1, -1)
//                } else {
//                    popupWindow.showAtLocation(acneView, 51, o.x, i)
//                }
//            }
//        }
//    }
//
//    interface b
//
//    enum class C {
//        FIRST,
//        SECOND,
//        THIRD
//    }
//
//    companion object {
//        fun getDistance(motionEvent: MotionEvent): Float {
//            return if (motionEvent.pointerCount < 2) {
//                0.0f
//            } else {
//                val x = motionEvent.getX(0) - motionEvent.getX(1)
//                val y = motionEvent.getY(0) - motionEvent.getY(1)
//                Math.sqrt((y * y + x * x).toDouble()).toFloat()
//            }
//        }
//    }
//
//    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
//        val x = motionEvent.x
//        val y = motionEvent.y
//        val i = b * 2
//        val i2 = d
//        val f = i + i2
//        if (x < f && y < f) {
//            o.set(view.left + (view.width - f.toInt()), view.top)
//        } else if (x > view.width - f && y < f) {
//            o.set(view.left + i2, view.top)
//        }
//
//        when (motionEvent.actionMasked) {
//            MotionEvent.ACTION_DOWN -> {
//                if (x >= f || y >= f) {
//                    o.set(view.left + i2, view.top)
//                } else {
//                    o.set(view.left + (view.width - f.toInt()), view.top)
//                }
//                state = C.SECOND
//                acneView.r = true
//                acneView.I = x
//                acneView.J = y
//                acneView.invalidate()
//                val rectF = n0.p
//                val f2 = n0.s
//                rectF!!.offsetTo(x - f2, y - f2)
//                n0.C = x
//                n0.D = y
//                view.removeCallbacks(touchRunnable)
//                view.postDelayed(touchRunnable, 100L)
//            }
//            MotionEvent.ACTION_UP -> {
//                view.removeCallbacks(touchRunnable)
//                if (state == C.SECOND) {
//                    if (acneView.getImageRect() == null || !acneView.getImageRect()!!.contains(motionEvent.x, motionEvent.y)) {
//                        acneView.r = false
//                        popupWindow.dismiss()
//                    } else {
//                        val fArr = floatArrayOf(x, y)
//                        val fArr2 = floatArrayOf(x, y)
//                        val matrix = Matrix()
//                        acneView.l.invert(matrix)
//                        matrix.mapPoints(fArr)
//                        matrix.reset()
//                        acneView.d.invert(matrix)
//                        matrix.mapPoints(fArr2)
//                        val f3 = fArr[0] / acneView.D
//                        val f4 = fArr[1] / acneView.E
//                        if (f3 in 0.0f..1.0f && f4 in 0.0f..1.0f) {
//                            val maskCircleRadius = acneView.getMaskCircleRadius()
//                            acneView.K!!.maskFilter = BlurMaskFilter(maskCircleRadius / 4.0f, BlurMaskFilter.Blur.NORMAL)
//                            acneView.H!!.drawColor(-1)
//                            acneView.H!!.drawCircle(fArr2[0], fArr2[1], maskCircleRadius, acneView.K)
//                            val aVar = acneView.y
//                            if (aVar is ViewOnClickListenerC4201uP) {
//                                val bitmap = acneView.G
//                                val viewOnClickListenerC4201uP = aVar
//                                if (!viewOnClickListenerC4201uP.V0) {
//                                    viewOnClickListenerC4201uP.V0 = true
//                                    viewOnClickListenerC4201uP.R0.visibility = View.VISIBLE
//                                    C1407Xz0.a {
//                                        val viewOnClickListenerC4201uP2 = viewOnClickListenerC4201uP
//                                        val l0 = viewOnClickListenerC4201uP2.U0
//                                        if (l0 != null) {
//                                            if (l0.f1168a <= 0) {
//                                                l0.f1168a = AcneProcessor.a(l0.e)
//                                            }
//                                            val createBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
//                                            if (l0.c == null) {
//                                                l0.c = l0.b.e()
//                                            }
//                                            if (AcneProcessor.c(l0.f1168a, l0.c, bitmap, createBitmap) == 0) {
//                                                l0.b.f(createBitmap)
//                                            }
//                                            createBitmap.recycle()
//                                            l0.d = l0.c
//                                            l0.c = l0.b.e()
//                                        }
//                                        viewOnClickListenerC4201uP2.X0.post(RunnableC1982dA(viewOnClickListenerC4201uP2, 1))
//                                        val canvas = viewOnClickListenerC4201uP2.Q0.H
//                                        canvas?.drawColor(0, PorterDuff.Mode.CLEAR)
//                                    }
//                                }
//                            }
//                        }
//                        val matrix2 = Matrix()
//                        acneView.d.invert(matrix2)
//                        matrix2.mapPoints(floatArrayOf(x, y))
//                        if (popupWindow.isShowing) {
//                            acneView.r = false
//                        }
//                    }
//                }
//                acneView.t = false
//                acneView.invalidate()
//                state = C.FIRST
//            }
//            MotionEvent.ACTION_MOVE -> {
//                if (state == C.THIRD) {
//                    val distance = getDistance(motionEvent)
//                    if (distance > 10.0f) {
//                        acneView.t = true
//                        acneView.invalidate()
//                        val scale = distance / p
//                        val pointF2 = q
//                        pointF2.set((motionEvent.getX(1) + motionEvent.getX(0)) / 2.0f, (motionEvent.getY(1) + motionEvent.getY(0)) / 2.0f)
//                        val f6 = (r.x + pointF2.x) / 2.0f
//                        val f7 = (r.y + pointF2.y) / 2.0f
//                        acneView.p = f6
//                        acneView.q = f7
//                        acneView.c *= scale
//                        acneView.d.postScale(scale, scale, f6, f7)
//                        acneView.f4930m.postScale(scale, scale, f6, f7)
//                        acneView.l.postScale(scale, scale, f6, f7)
//                        val f8 = pointF2.x - r.x
//                        val f9 = pointF2.y - r.y
//                        acneView.d.postTranslate(f8, f9)
//                        acneView.f4930m.postTranslate(f8, f9)
//                        acneView.l.postTranslate(f8, f9)
//                        acneView.invalidate()
//                        p = distance
//                        r.set(pointF2)
//                    }
//                } else if (state == C.SECOND) {
//                    acneView.r = true
//                    acneView.I = x
//                    acneView.J = y
//                    acneView.invalidate()
//                    acneView.t = true
//                    acneView.invalidate()
//                    val rectF2 = n0.p
//                    val f10 = n0.s
//                    rectF2!!.offsetTo(x - f10, y - f10)
//                    n0.C = x
//                    n0.D = y
//                    n0.invalidate()
//                    popupWindow.update(r.x.toInt(), c, -1, -1)
//                }
//            }
//            MotionEvent.ACTION_POINTER_DOWN -> {
//                view.removeCallbacks(touchRunnable)
//                popupWindow.dismiss()
//                acneView.r = false
//                val distance = getDistance(motionEvent)
//                p = distance
//                if (distance > 10.0f) {
//                    r.set((motionEvent.getX(1) + motionEvent.getX(0)) / 2.0f, (motionEvent.getY(1) + motionEvent.getY(0)) / 2.0f)
//                    state = C.THIRD
//                } else {
//                    state = C.FIRST
//                }
//            }
//            MotionEvent.ACTION_POINTER_UP -> {
//                acneView.invalidate()
//            }
//            MotionEvent.ACTION_CANCEL -> {
//                view.removeCallbacks(touchRunnable)
//                acneView.r = false
//                popupWindow.dismiss()
//                acneView.t = false
//                acneView.invalidate()
//                state = C.FIRST
//            }
//        }
//        return true
//    }
//}