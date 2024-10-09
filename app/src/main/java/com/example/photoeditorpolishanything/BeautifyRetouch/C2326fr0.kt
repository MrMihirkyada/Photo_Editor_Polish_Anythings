//package com.example.photoeditorpolishanything.BeautifyRetouch
//
//import android.graphics.*
//import kotlin.math.abs
//import kotlin.math.max
//import kotlin.math.min
//import kotlin.math.pow
//
//
//class C2326fr0 {
//
//    @Deprecated
//    var f4613a: Float = 0.0f
//
//    @Deprecated
//    var b: Float = 0.0f
//
//    @Deprecated
//    var c: Float = 0.0f
//
//    @Deprecated
//    var d: Float = 0.0f
//
//    @Deprecated
//    var e: Float = 0.0f
//
//    @Deprecated
//    var f: Float = 0.0f
//
//    private val g: ArrayList<e> = ArrayList()
//    private val h: ArrayList<f> = ArrayList()
//
//    abstract class e {
//
//        protected val f4614a: Matrix = Matrix()
//
//        abstract fun a(matrix: Matrix, path: Path)
//    }
//
//    abstract class f {
//
//        companion object {
//            val b: Matrix = Matrix()
//        }
//
//        protected val f4615a: Matrix = Matrix()
//
//        abstract fun a(matrix: Matrix, c0973Pq0: C0973Pq0, i: Int, canvas: Canvas)
//    }
//
//    class a(val c: c) : f() {
//
//        override fun a(matrix: Matrix, c0973Pq0: C0973Pq0, i: Int, canvas: Canvas) {
//            val f = c.f
//            val f2 = c.g
//            val rectF = RectF(c.b, c.c, c.d, c.e)
//            val z = f2 < 0.0f
//            val path = c0973Pq0.g
//            val iArr = C0973Pq0.k
//
//            if (z) {
//                iArr[0] = 0
//                iArr[1] = c0973Pq0.f
//                iArr[2] = c0973Pq0.e
//                iArr[3] = c0973Pq0.d
//            } else {
//                path.rewind()
//                path.moveTo(rectF.centerX(), rectF.centerY())
//                path.arcTo(rectF, f, f2)
//                path.close()
//                val f3 = -i.toFloat()
//                rectF.inset(f3, f3)
//                iArr[0] = 0
//                iArr[1] = c0973Pq0.d
//                iArr[2] = c0973Pq0.e
//                iArr[3] = c0973Pq0.f
//            }
//
//            val width = rectF.width() / 2.0f
//            if (width <= 0.0f) {
//                return
//            }
//
//            val f4 = 1.0f - (i / width)
//            val fArr = C0973Pq0.l
//            fArr[1] = f4
//            fArr[2] = (1.0f - f4) / 2.0f + f4
//            val radialGradient = RadialGradient(rectF.centerX(), rectF.centerY(), width, iArr, fArr, Shader.TileMode.CLAMP)
//            val paint = c0973Pq0.b
//            paint.shader = radialGradient
//
//            canvas.save()
//            canvas.concat(matrix)
//            canvas.scale(1.0f, rectF.height() / rectF.width())
//
//            if (!z) {
//                canvas.clipPath(path, Region.Op.DIFFERENCE)
//                canvas.drawPath(path, c0973Pq0.h)
//            }
//
//            canvas.drawArc(rectF, f, f2, true, paint)
//            canvas.restore()
//        }
//    }
//
//    class b(val c: d, val d: Float, val e: Float) : f() {
//
//        override fun a(matrix: Matrix, c0973Pq0: C0973Pq0, i: Int, canvas: Canvas) {
//            val f = c.c
//            val f2 = this.e
//            val f3 = c.b
//            val f4 = this.d
//            val rectF = RectF(0.0f, 0.0f, Math.hypot((f - f2).toDouble(), (f3 - f4).toDouble()).toFloat(), 0.0f)
//            val matrix2 = f4615a
//            matrix2.set(matrix)
//            matrix2.preTranslate(f4, f2)
//            matrix2.preRotate(b())
//            rectF.bottom += i
//            rectF.offset(0.0f, -i)
//            val iArr = C0973Pq0.i
//            iArr[0] = c0973Pq0.f
//            iArr[1] = c0973Pq0.e
//            iArr[2] = c0973Pq0.d
//            val paint = c0973Pq0.c
//            paint.shader = LinearGradient(rectF.left, rectF.top, rectF.left, rectF.bottom, iArr, C0973Pq0.j, Shader.TileMode.CLAMP)
//
//            canvas.save()
//            canvas.concat(matrix2)
//            canvas.drawRect(rectF, paint)
//            canvas.restore()
//        }
//
//        fun b(): Float {
//            val dVar = c
//            return Math.toDegrees(Math.atan((dVar.c - e) / (dVar.b - d))).toFloat()
//        }
//    }
//
//    class c(val b: Float, val c: Float, val d: Float, val e: Float) : e() {
//
//        var f: Float = 0.0f
//        var g: Float = 0.0f
//
//        override fun a(matrix: Matrix, path: Path) {
//            val matrix2 = f4614a
//            matrix.invert(matrix2)
//            path.transform(matrix2)
//            val rectF = h
//            rectF.set(this.b, this.c, this.d, this.e)
//            path.arcTo(rectF, this.f, this.g, false)
//            path.transform(matrix)
//        }
//
//        companion object {
//            val h: RectF = RectF()
//        }
//    }
//
//    class d : e() {
//
//        var b: Float = 0.0f
//        var c: Float = 0.0f
//
//        override fun a(matrix: Matrix, path: Path) {
//            val matrix2 = f4614a
//            matrix.invert(matrix2)
//            path.transform(matrix2)
//            path.lineTo(this.b, this.c)
//            path.transform(matrix)
//        }
//    }
//
//    init {
//        e(0.0f, 270.0f, 0.0f)
//    }
//
//    fun a(f2: Float, f3: Float, f4: Float, f5: Float, f6: Float, f7: Float) {
//        val cVar = c(f2, f3, f4, f5)
//        cVar.f = f6
//        cVar.g = f7
//        g.add(cVar)
//        val aVar = a(cVar)
//        val f8 = f6 + f7
//        val z = f7 < 0.0f
//        val f9 = if (z) (f6 + 180.0f) % 360.0f else f8
//        b(f6)
//        h.add(aVar)
//        e = if (z) (180.0f + f8) % 360.0f else f8
//        val d2 = f8.toDouble()
//        c = (((f4 - f2) / 2.0f) * Math.cos(Math.toRadians(d2))).toFloat() + (f2 + f4) * 0.5f
//        d = (((f5 - f3) / 2.0f) * Math.sin(Math.toRadians(d2))).toFloat() + (f3 + f5) * 0.5f
//    }
//
//    fun b(f2: Float) {
//        val f3 = e
//        if (f3 == f2) {
//            return
//        }
//        val f4 = (f2 - f3 + 360.0f) % 360.0f
//        if (f4 > 180.0f) {
//            return
//        }
//        val f5 = c
//        val f6 = d
//        val cVar = c(f5, f6, f5, f6)
//        cVar.f = e
//        cVar.g = f4
//        h.add(a(cVar))
//        e = f2
//    }
//
//    fun c(matrix: Matrix, path: Path) {
//        for (i in g.indices) {
//            g[i].a(matrix, path)
//        }
//    }
//
//    fun d(f2: Float, f3: Float) {
//        val eVar = d()
//        eVar.b = f2
//        eVar.c = f3
//        g.add(eVar)
//        val bVar = b(eVar, c, d)
//        val b2 = bVar.b() + 270.0f
//        val b3 = bVar.b() + 270.0f
//        b(b2)
//        h.add(bVar)
//        e = b3
//        c = f2
//        d = f3
//    }
//
//    fun e(f2: Float, f3: Float, f4: Float) {
//        f4613a = 0.0f
//        b = f2
//        c = 0.0f
//        d = f2
//        e = f3
//        f = (f3 + f4) % 360.0f
//        g.clear()
//        h.clear()
//    }
//
//    private fun c(f2: Float, f3: Float, f4: Float, f5: Float): c {
//        return c(f2, f3, f4, f5)
//    }
//
//    private fun a(cVar: c): a {
//        return a(cVar)
//    }
//
//    private fun d(): d {
//        return d()
//    }
//
//    private fun b(cVar: d, c: Float, d: Float): b {
//        return b(cVar, c, d)
//    }
//}
//
//
//class C0973Pq0 {
//    /* renamed from: a, reason: collision with root package name */
//    val f1695a: Paint
//    val b: Paint
//    val c: Paint
//    var d: Int
//    var e: Int
//    var f: Int
//    val g = Path()
//    val h: Paint
//
//    init {
//        val paint = Paint()
//        h = paint
//        val paint2 = Paint()
//        f1695a = paint2
//        d = C0411Fi.h(-16777216, 68)
//        e = C0411Fi.h(-16777216, 20)
//        f = C0411Fi.h(-16777216, 0)
//        paint2.setColor(d)
//        paint.setColor(0)
//        val paint3 = Paint(4)
//        b = paint3
//        paint3.style = Paint.Style.FILL
//        c = Paint(paint3)
//    }
//
//    companion object {
//        val i = IntArray(3)
//        val j = floatArrayOf(0.0f, 0.5f, 1.0f)
//        val k = IntArray(4)
//        val l = floatArrayOf(0.0f, 0.0f, 0.5f, 1.0f)
//    }
//}
//
//
//object C0411Fi {
//    /* renamed from: a, reason: collision with root package name */
//    val f612a = ThreadLocal<DoubleArray>()
//    fun a(i: Int, i2: Int, i3: Int, fArr: FloatArray) {
//        val f: Float
//        val abs: Float
//        val f2 = i / 255.0f
//        val f3 = i2 / 255.0f
//        val f4 = i3 / 255.0f
//        val max =
//            max(f2.toDouble(), max(f3.toDouble(), f4.toDouble())).toFloat()
//        val min =
//            min(f2.toDouble(), min(f3.toDouble(), f4.toDouble())).toFloat()
//        val f5 = max - min
//        val f6 = (max + min) / 2.0f
//        if (max == min) {
//            f = 0.0f
//            abs = 0.0f
//        } else {
//            f =
//                if (max == f2) (f3 - f4) / f5 % 6.0f else if (max == f3) (f4 - f2) / f5 + 2.0f else 4.0f + (f2 - f3) / f5
//            abs = (f5 / (1.0f - abs((2.0f * f6 - 1.0f).toDouble()))).toFloat()
//        }
//        var f7 = f * 60.0f % 360.0f
//        if (f7 < 0.0f) {
//            f7 += 360.0f
//        }
//        fArr[0] = if (f7 < 0.0f) 0.0f else min(f7.toDouble(), 360.0).toFloat()
//        fArr[1] = if (abs < 0.0f) 0.0f else min(abs.toDouble(), 1.0).toFloat()
//        fArr[2] = if (f6 >= 0.0f) min(f6.toDouble(), 1.0).toFloat() else 0.0f
//    }
//
//    fun b(d: Double, d2: Double, d3: Double): Int {
//        val d4 = (-0.4986 * d3 + (-1.5372 * d2 + 3.2406 * d)) / 100.0
//        val d5 = (0.0415 * d3 + (1.8758 * d2 + -0.9689 * d)) / 100.0
//        val d6 = (1.057 * d3 + (-0.204 * d2 + 0.0557 * d)) / 100.0
//        val pow = if (d4 > 0.0031308) d4.pow(0.4166666666666667) * 1.055 - 0.055 else d4 * 12.92
//        val pow2 = if (d5 > 0.0031308) d5.pow(0.4166666666666667) * 1.055 - 0.055 else d5 * 12.92
//        val pow3 = if (d6 > 0.0031308) d6.pow(0.4166666666666667) * 1.055 - 0.055 else d6 * 12.92
//        val round = Math.round(pow * 255.0).toInt()
//        val min = if (round < 0) 0 else min(round.toDouble(), 255.0).toInt()
//        val round2 = Math.round(pow2 * 255.0).toInt()
//        val min2 = if (round2 < 0) 0 else min(round2.toDouble(), 255.0).toInt()
//        val round3 = Math.round(pow3 * 255.0).toInt()
//        return Color.rgb(min, min2, if (round3 >= 0) min(round3.toDouble(), 255.0) else 0)
//    }
//
//    fun c(i: Int, i2: Int): Double {
//        var i = i
//        require(Color.alpha(i2) == 255) {
//            "background can not be translucent: #" + Integer.toHexString(
//                i2
//            )
//        }
//        if (Color.alpha(i) < 255) {
//            i = f(i, i2)
//        }
//        val d = d(i) + 0.05
//        val d2 = d(i2) + 0.05
//        return max(d, d2) / min(d, d2)
//    }
//
//    fun d(i: Int): Double {
//        val threadLocal = f612a
//        var dArr = threadLocal.get()
//        if (dArr == null) {
//            dArr = DoubleArray(3)
//            threadLocal.set(dArr)
//        }
//        val red = Color.red(i)
//        val green = Color.green(i)
//        val blue = Color.blue(i)
//        require(dArr.size == 3) { "outXyz must have a length of 3." }
//        val d = red / 255.0
//        val pow = if (d < 0.04045) d / 12.92 else ((d + 0.055) / 1.055).pow(2.4)
//        val d2 = green / 255.0
//        val pow2 = if (d2 < 0.04045) d2 / 12.92 else ((d2 + 0.055) / 1.055).pow(2.4)
//        val d3 = blue / 255.0
//        val pow3 = if (d3 < 0.04045) d3 / 12.92 else ((d3 + 0.055) / 1.055).pow(2.4)
//        dArr[0] = (0.1805 * pow3 + 0.3576 * pow2 + 0.4124 * pow) * 100.0
//        val d4 = (0.0722 * pow3 + 0.7152 * pow2 + 0.2126 * pow) * 100.0
//        dArr[1] = d4
//        dArr[2] = (pow3 * 0.9505 + pow2 * 0.1192 + pow * 0.0193) * 100.0
//        return d4 / 100.0
//    }
//
//    fun e(f: Float, i: Int, i2: Int): Int {
//        var i3 = 255
//        require(Color.alpha(i2) == 255) {
//            "background can not be translucent: #" + Integer.toHexString(
//                i2
//            )
//        }
//        val d = f.toDouble()
//        if (c(h(i, 255), i2) < d) {
//            return -1
//        }
//        var i4 = 0
//        var i5 = 0
//        while (i5 <= 10 && i3 - i4 > 1) {
//            val i6 = (i4 + i3) / 2
//            if (c(h(i, i6), i2) < d) {
//                i4 = i6
//            } else {
//                i3 = i6
//            }
//            i5++
//        }
//        return i3
//    }
//
//    fun f(i: Int, i2: Int): Int {
//        val alpha = Color.alpha(i2)
//        val alpha2 = Color.alpha(i)
//        val i3 = 255 - (255 - alpha2) * (255 - alpha) / 255
//        return Color.argb(
//            i3,
//            g(Color.red(i), alpha2, Color.red(i2), alpha, i3),
//            g(Color.green(i), alpha2, Color.green(i2), alpha, i3),
//            g(
//                Color.blue(i), alpha2, Color.blue(i2), alpha, i3
//            )
//        )
//    }
//
//    fun g(i: Int, i2: Int, i3: Int, i4: Int, i5: Int): Int {
//        return if (i5 == 0) {
//            0
//        } else ((255 - i2) * (i3 * i4) + i * 255 * i2) / (i5 * 255)
//    }
//
//    fun h(i: Int, i2: Int): Int {
//        require(!(i2 < 0 || i2 > 255)) { "alpha must be between 0 and 255." }
//        return i and 16777215 or (i2 shl 24)
//    }
//}