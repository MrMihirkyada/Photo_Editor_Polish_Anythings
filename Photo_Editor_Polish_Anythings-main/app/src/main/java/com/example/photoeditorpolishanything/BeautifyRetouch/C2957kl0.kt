//package com.example.photoeditorpolishanything.BeautifyRetouch
//
//
//object C2957kl0 {
//    /* renamed from: a, reason: collision with root package name */
//    var f5195a: StringBuilder? = null
//    var b: StringBuilder? = null
//    fun a(str: String?) {
//        if (f5195a == null) {
//            return
//        }
//        C4670y20.b("ReportUtils", str!!)
//        synchronized(C2957kl0::class.java) {
//            try {
//                val sb = f5195a
//                sb!!.append(str)
//                sb.append("|")
//                if (f5195a!!.length > 8192) {
//                    val sb2 = f5195a
//                    sb2!!.delete(0, sb2.length - 8192)
//                }
//                val sb3 = b
//                sb3!!.append(str)
//                sb3.append("|")
//                if (b!!.length > 32768) {
//                    val sb4 = b
//                    sb4!!.delete(0, sb4.length - 32768)
//                }
//            } catch (th: Throwable) {
//                throw th
//            }
//        }
//    }
//}