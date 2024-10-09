/*
package com.example.photoeditorpolishanything.BeautifyRetouch

import android.graphics.PointF
import java.nio.charset.StandardCharsets
import java.util.Base64.Decoder
import java.util.Random
import kotlin.math.acos
import kotlin.math.sqrt


object C4140tx {
    */
/* renamed from: a, reason: collision with root package name *//*

    val f6170a: MZ? = null
    val b: MZ? = null

    init {
        val i = 1
        f6170a = MZ("REMOVED_TASK", i)
        b = MZ("CLOSED_EMPTY", i)
    }

    */
/* JADX WARN: Type inference failed for: r0v0, types: [FZ, ux0] *//*

    fun a(): C4270ux0 {
        return FZ(null)
    }

    */
/* JADX WARN: Code restructure failed: missing block: B:29:0x0022, code lost:

        if (r1 < (-1.0d)) goto L4;
     *//*

    */
/*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    *//*

    fun b(r5: PointF?, r6: PointF?): Float {
        */
/*
            float r0 = r5.x
            float r1 = r6.x
            float r0 = r0 - r1
            float r5 = r5.y
            float r6 = r6.y
            float r5 = r5 - r6
            double r1 = (double) r0
            float r6 = r0 * r0
            float r3 = r5 * r5
            float r3 = r3 + r6
            double r3 = (double) r3
            double r3 = java.lang.Math.sqrt(r3)
            double r1 = r1 / r3
            r3 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r6 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r6 <= 0) goto L1e
        L1c:
            r1 = r3
            goto L25
        L1e:
            r3 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            int r6 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r6 >= 0) goto L25
            goto L1c
        L25:
            double r1 = java.lang.Math.asin(r1)
            r3 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r1 = r1 * r3
            r3 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            double r1 = r1 / r3
            float r6 = (float) r1
            r1 = 0
            int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r2 < 0) goto L44
            int r3 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r3 > 0) goto L44
            r5 = 1135869952(0x43b40000, float:360.0)
            float r1 = r5 - r6
            goto L60
        L44:
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 > 0) goto L4e
            int r3 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r3 > 0) goto L4e
            float r1 = -r6
            goto L60
        L4e:
            r3 = 1127481344(0x43340000, float:180.0)
            if (r0 > 0) goto L59
            int r0 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r0 < 0) goto L59
        L56:
            float r1 = r6 + r3
            goto L60
        L59:
            if (r2 < 0) goto L60
            int r5 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r5 < 0) goto L60
            goto L56
        L60:
            return r1
        *//*

        throw UnsupportedOperationException("Method not decompiled: defpackage.C4140tx.b(android.graphics.PointF, android.graphics.PointF):float")
    }

    */
/* JADX WARN: Code restructure failed: missing block: B:28:0x001a, code lost:

        if (r5 < (-1.0d)) goto L4;
     *//*

    */
/*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    *//*

    fun c(r3: Float, r4: Float, r5: Float, r6: Float): Float {
        */
/*
            float r3 = r3 - r5
            float r4 = r4 - r6
            double r5 = (double) r3
            float r0 = r3 * r3
            float r1 = r4 * r4
            float r1 = r1 + r0
            double r0 = (double) r1
            double r0 = java.lang.Math.sqrt(r0)
            double r5 = r5 / r0
            r0 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r2 <= 0) goto L16
        L14:
            r5 = r0
            goto L1d
        L16:
            r0 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            int r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r2 >= 0) goto L1d
            goto L14
        L1d:
            double r5 = java.lang.Math.asin(r5)
            r0 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r5 = r5 * r0
            r0 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            double r5 = r5 / r0
            float r5 = (float) r5
            r6 = 0
            int r0 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            r1 = 1119092736(0x42b40000, float:90.0)
            if (r0 < 0) goto L3c
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 > 0) goto L3c
            float r6 = r5 - r1
            goto L59
        L3c:
            int r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r3 > 0) goto L49
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 > 0) goto L49
            r3 = 1132920832(0x43870000, float:270.0)
            float r6 = r5 + r3
            goto L59
        L49:
            if (r3 > 0) goto L52
            int r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r3 < 0) goto L52
        L4f:
            float r6 = r1 - r5
            goto L59
        L52:
            if (r0 < 0) goto L59
            int r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r3 < 0) goto L59
            goto L4f
        L59:
            return r6
        *//*

        throw UnsupportedOperationException("Method not decompiled: defpackage.C4140tx.c(float, float, float, float):float")
    }

    fun d(str: String, str2: String?): String {
        return String(
            if (!Decoder.f4358a) str.toByteArray() else Decoder.decodeBytesNative(
                str,
                str2
            ), StandardCharsets.UTF_8
        )
    }

    */
/* JADX WARN: Code restructure failed: missing block: B:28:0x0079, code lost:

        if (r12.y < 0.0f) goto L16;
     *//*

    */
/*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    *//*

    fun e(r12: PointF?): Float {
        */
/*
            float r0 = r12.y
            double r0 = (double) r0
            r2 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r0 = java.lang.Math.pow(r0, r2)
            float r4 = r12.x
            double r4 = (double) r4
            double r4 = java.lang.Math.pow(r4, r2)
            float r6 = r12.x
            double r6 = (double) r6
            double r6 = java.lang.Math.pow(r6, r2)
            float r8 = r12.y
            double r8 = (double) r8
            double r8 = java.lang.Math.pow(r8, r2)
            double r8 = r8 + r6
            float r6 = r12.x
            r7 = 0
            int r10 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r10 == 0) goto L7c
            float r11 = r12.y
            int r11 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r11 == 0) goto L7c
            double r10 = r8 + r4
            double r10 = r10 - r0
            double r0 = java.lang.Math.sqrt(r8)
            double r0 = r0 * r2
            double r2 = java.lang.Math.sqrt(r4)
            double r2 = r2 * r0
            double r10 = r10 / r2
            double r0 = java.lang.Math.acos(r10)
            r2 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r0 = r0 * r2
            r4 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            double r0 = r0 / r4
            float r4 = r12.x
            int r5 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r5 <= 0) goto L5b
            float r6 = r12.y
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 <= 0) goto L5b
            r12 = 1135869952(0x43b40000, float:360.0)
            float r0 = (float) r0
            float r12 = r12 - r0
            goto La6
        L5b:
            int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r4 >= 0) goto L68
            float r6 = r12.y
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 <= 0) goto L68
            double r0 = r0 + r2
        L66:
            float r12 = (float) r0
            goto La6
        L68:
            if (r4 >= 0) goto L73
            float r4 = r12.y
            int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r4 >= 0) goto L73
            double r2 = r2 - r0
            float r12 = (float) r2
            goto La6
        L73:
            if (r5 <= 0) goto L84
            float r12 = r12.y
            int r12 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r12 >= 0) goto L84
            goto L66
        L7c:
            if (r10 <= 0) goto L86
            float r0 = r12.y
            int r0 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r0 != 0) goto L86
        L84:
            r12 = r7
            goto La6
        L86:
            int r0 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r0 >= 0) goto L93
            float r0 = r12.y
            int r0 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r0 != 0) goto L93
            r12 = 1127481344(0x43340000, float:180.0)
            goto La6
        L93:
            float r12 = r12.y
            int r0 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r0 <= 0) goto L9e
            if (r10 != 0) goto L9e
            r12 = 1132920832(0x43870000, float:270.0)
            goto La6
        L9e:
            int r12 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r12 >= 0) goto L84
            if (r10 != 0) goto L84
            r12 = 1119092736(0x42b40000, float:90.0)
        La6:
            int r0 = (int) r12
            r1 = 360(0x168, float:5.04E-43)
            if (r0 < r1) goto Lac
            goto Lad
        Lac:
            r7 = r12
        Lad:
            return r7
        *//*

        throw UnsupportedOperationException("Method not decompiled: defpackage.C4140tx.e(android.graphics.PointF):float")
    }

    fun f(f: Float, f2: Float, f3: Float, f4: Float): Float {
        val f5 = f - f3
        val f6 = f2 - f4
        return sqrt((f6 * f6 + f5 * f5).toDouble()).toFloat()
    }

    fun g(i: Int, i2: Int): Int {
        return Random().nextInt(i2 - i) + i
    }

    fun h(pointF: PointF, pointF2: PointF, pointF3: PointF, pointF4: PointF): Float {
        val pointF5 = PointF(pointF2.x - pointF.x, pointF2.y - pointF.y)
        val pointF6 = PointF(pointF4.x - pointF3.x, pointF4.y - pointF3.y)
        return Math.toDegrees(acos(((pointF5.y * pointF6.y + pointF5.x * pointF6.x) / (pointF6.length() * pointF5.length())).toDouble()))
            .toFloat()
    }

    fun i(f: Float, f2: Float, f3: Float, f4: Float, f5: Float, f6: Float): Double {
        val f7 = f - f3
        val f8 = f2 - f4
        val sqrt = sqrt((f8 * f8 + f7 * f7).toDouble())
        val f9 = f - f5
        val f10 = f2 - f6
        val sqrt2 = sqrt((f10 * f10 + f9 * f9).toDouble())
        val f11 = f3 - f5
        val f12 = f4 - f6
        val sqrt3 = sqrt((f12 * f12 + f11 * f11).toDouble())
        if (sqrt3 <= 1.0E-6 || sqrt2 <= 1.0E-6) {
            return 0.0
        }
        if (sqrt <= 1.0E-6) {
            return sqrt2
        }
        val d = sqrt3 * sqrt3
        val d2 = sqrt * sqrt
        val d3 = sqrt2 * sqrt2
        if (d >= d2 + d3) {
            return sqrt2
        }
        if (d3 >= d2 + d) {
            return sqrt3
        }
        val d4 = (sqrt + sqrt2 + sqrt3) / 2.0
        return sqrt((d4 - sqrt3) * ((d4 - sqrt2) * ((d4 - sqrt) * d4))) * 2.0 / sqrt
    }
}*/
