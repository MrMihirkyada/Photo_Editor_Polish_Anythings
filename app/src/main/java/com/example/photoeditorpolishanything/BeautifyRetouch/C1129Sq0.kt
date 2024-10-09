//package com.example.photoeditorpolishanything.BeautifyRetouch
//
//import android.content.Context
//import android.content.res.TypedArray
//import android.graphics.RectF
//import android.util.AttributeSet
//import android.util.TypedValue
//
//class C1129Sq0 {
//
//    var f2020a: C3071le = C3735qn0()
//    var b: C3071le = C3735qn0()
//    var c: C3071le = C3735qn0()
//    var d: C3071le = C3735qn0()
//    var e: InterfaceC0209Bl = r(0.0f)
//    var f: InterfaceC0209Bl = r(0.0f)
//    var g: InterfaceC0209Bl = r(0.0f)
//    var h: InterfaceC0209Bl = r(0.0f)
//    var i: C1034Qv = C1034Qv()
//    var j: C1034Qv = C1034Qv()
//    var k: C1034Qv = C1034Qv()
//    var l: C1034Qv = C1034Qv()
//
//    class a {
//        var f2021a: C3071le = C3735qn0()
//        var b: C3071le = C3735qn0()
//        var c: C3071le = C3735qn0()
//        var d: C3071le = C3735qn0()
//        var e: InterfaceC0209Bl = r(0.0f)
//        var f: InterfaceC0209Bl = r(0.0f)
//        var g: InterfaceC0209Bl = r(0.0f)
//        var h: InterfaceC0209Bl = r(0.0f)
//        var i: C1034Qv = C1034Qv()
//        var j: C1034Qv = C1034Qv()
//        var k: C1034Qv = C1034Qv()
//        var l: C1034Qv = C1034Qv()
//
//        companion object {
//            fun b(c3071le: C3071le): Float {
//                return when (c3071le) {
//                    is C3735qn0 -> c3071le.f5797m
//                    is C2319fo -> c3071le.f4611m
//                    else -> -1.0f
//                }
//            }
//        }
//
//        fun a(): C1129Sq0 {
//            return C1129Sq0().apply {
//                f2020a = this@a.f2021a
//                b = this@a.b
//                c = this@a.c
//                d = this@a.d
//                e = this@a.e
//                f = this@a.f
//                g = this@a.g
//                h = this@a.h
//                i = this@a.i
//                j = this@a.j
//                k = this@a.k
//                l = this@a.l
//            }
//        }
//    }
//
//    interface b {
//        fun a(interfaceC0209Bl: InterfaceC0209Bl): InterfaceC0209Bl
//    }
//
//    companion object {
//        fun a(context: Context, i: Int, i2: Int, rVar: r): a {
//            var contextThemeWrapper = ContextThemeWrapper(context, i)
//            if (i2 != 0) {
//                contextThemeWrapper = ContextThemeWrapper(contextThemeWrapper, i2)
//            }
//            val obtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(C2953kj0.z)
//            try {
//                val i3 = obtainStyledAttributes.getInt(0, 0)
//                val i4 = obtainStyledAttributes.getInt(3, i3)
//                val i5 = obtainStyledAttributes.getInt(4, i3)
//                val i6 = obtainStyledAttributes.getInt(2, i3)
//                val i7 = obtainStyledAttributes.getInt(1, i3)
//                val c = c(obtainStyledAttributes, 5, rVar)
//                val c2 = c(obtainStyledAttributes, 8, c)
//                val c3 = c(obtainStyledAttributes, 9, c)
//                val c4 = c(obtainStyledAttributes, 7, c)
//                val c5 = c(obtainStyledAttributes, 6, c)
//                val aVar = a()
//                val b2 = C2686ie.b(i4)
//                aVar.f2021a = b2
//                val b3 = a.b(b2)
//                if (b3 != -1.0f) {
//                    aVar.e = r(b3)
//                }
//                aVar.e = c2
//                val b4 = C2686ie.b(i5)
//                aVar.b = b4
//                val b5 = a.b(b4)
//                if (b5 != -1.0f) {
//                    aVar.f = r(b5)
//                }
//                aVar.f = c3
//                val b6 = C2686ie.b(i6)
//                aVar.c = b6
//                val b7 = a.b(b6)
//                if (b7 != -1.0f) {
//                    aVar.g = r(b7)
//                }
//                aVar.g = c4
//                val b8 = C2686ie.b(i7)
//                aVar.d = b8
//                val b9 = a.b(b8)
//                if (b9 != -1.0f) {
//                    aVar.h = r(b9)
//                }
//                aVar.h = c5
//                return aVar
//            } finally {
//                obtainStyledAttributes.recycle()
//            }
//        }
//
//        fun b(context: Context, attributeSet: AttributeSet, i: Int, i2: Int): a {
//            val rVar = r(0)
//            val obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C2953kj0.t, i, i2)
//            val resourceId = obtainStyledAttributes.getResourceId(0, 0)
//            val resourceId2 = obtainStyledAttributes.getResourceId(1, 0)
//            obtainStyledAttributes.recycle()
//            return a(context, resourceId, resourceId2, rVar)
//        }
//
//        fun c(typedArray: TypedArray, i: Int, interfaceC0209Bl: InterfaceC0209Bl): InterfaceC0209Bl {
//            val peekValue = typedArray.peekValue(i) ?: return interfaceC0209Bl
//            return when (peekValue.type) {
//                TypedValue.TYPE_DIMENSION -> r(TypedValue.complexToDimensionPixelSize(peekValue.data, typedArray.resources.displayMetrics))
//                TypedValue.TYPE_FRACTION -> C4889zk0(peekValue.getFraction(1.0f, 1.0f))
//                else -> interfaceC0209Bl
//            }
//        }
//    }
//
//    fun d(rectF: RectF): Boolean {
//        val z = this.l::class.java == C1034Qv::class.java &&
//                this.j::class.java == C1034Qv::class.java &&
//                this.i::class.java == C1034Qv::class.java &&
//                this.k::class.java == C1034Qv::class.java
//
//        val a2 = this.e.a(rectF)
//        return z &&
//                (this.f.a(rectF) == a2) &&
//                (this.h.a(rectF) == a2) &&
//                (this.g.a(rectF) == a2) &&
//                (this.b is C3735qn0 && this.f2020a is C3735qn0 &&
//                        this.c is C3735qn0 && this.d is C3735qn0)
//    }
//
//    fun e(): a {
//        return a().apply {
//            f2021a = C3735qn0()
//            b = C3735qn0()
//            c = C3735qn0()
//            d = C3735qn0()
//            e = r(0.0f)
//            f = r(0.0f)
//            g = r(0.0f)
//            h = r(0.0f)
//            i = C1034Qv()
//            j = C1034Qv()
//            k = C1034Qv()
//        }.apply {
//            f2021a = this@C1129Sq0.f2020a
//            b = this@C1129Sq0.b
//            c = this@C1129Sq0.c
//            d = this@C1129Sq0.d
//            e = this@C1129Sq0.e
//            f = this@C1129Sq0.f
//            g = this@C1129Sq0.g
//            h = this@C1129Sq0.h
//            i = this@C1129Sq0.i
//            j = this@C1129Sq0.j
//            k = this@C1129Sq0.k
//            l = this@C1129Sq0.l
//        }
//    }
//
//    fun f(bVar: b): C1129Sq0 {
//        val e = e()
//        e.e = bVar.a(this.e)
//        e.f = bVar.a(this.f)
//        e.h = bVar.a(this.h)
//        e.g = bVar.a(this.g)
//        return e.a()
//    }
//}
//
//
//class C1034Qv {
//    fun a(f: Float, f2: Float, f3: Float, c2326fr0: C2326fr0) {
//        c2326fr0.d(f, 0.0f)
//    }
//}