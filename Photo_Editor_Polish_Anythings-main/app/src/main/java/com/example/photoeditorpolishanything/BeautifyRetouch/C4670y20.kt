//package com.example.photoeditorpolishanything.BeautifyRetouch
//
//import android.util.Log
//import okhttp3.HttpUrl
//import java.io.File
//import java.io.FileOutputStream
//import java.io.IOException
//
//
//class C4670y20 {
//    /* renamed from: a, reason: collision with root package name */
//    var f6603a: FileOutputStream? = null
//    var b: File? = null
//    var c: StringBuilder? = null
//
//    init {
//        Log.e(HttpUrl.FRAGMENT_ENCODE_SET, "Log instance=" + f)
//        if (d) {
//            a()
//        }
//    }
//
//    fun a() {
//        if (b != null || f6603a != null || g == null || h == null) {
//            return
//        }
//        try {
//            i = g()
//            b = File(f())
//            f6603a = FileOutputStream(b, true)
//            c = StringBuilder()
//        } catch (e2: Exception) {
//            e2.printStackTrace()
//            b = null
//            val fileOutputStream = f6603a
//            if (fileOutputStream != null) {
//                try {
//                    fileOutputStream.close()
//                } catch (e3: IOException) {
//                    e3.printStackTrace()
//                }
//                f6603a = null
//            }
//            d = false
//        }
//    }
//
//    companion object {
//        var d = true
//        var e = true
//        var f: C4670y20? = null
//        var g: String? = null
//        var h: String? = null
//        var i = 0
//        var j = false
//        fun b(str: String, str2: String) {
//            h(6, str, str2)
//        }
//
//        fun c(str: String, str2: String, th: Throwable?) {
//            h(
//                6, str, """
//     $str2
//     ${Log.getStackTraceString(th)}
//     """.trimIndent()
//            )
//        }
//
//        fun d() {
//            var sb: StringBuilder
//            var fileOutputStream: FileOutputStream
//            val e2 = e()
//            if (e2 == null || e2.c.also { sb = it!! } == null || e2.f6603a.also {
//                    fileOutputStream =
//                        it!!
//                } == null) {
//                return
//            }
//            try {
//                fileOutputStream.write(sb.toString().toByteArray())
//                e2.f6603a!!.flush()
//            } catch (e3: IOException) {
//                e3.printStackTrace()
//            }
//            synchronized(C4670y20::class.java) {
//                val sb2 = e2.c
//                sb2!!.delete(0, sb2.length)
//            }
//        }
//
//        @Synchronized
//        fun e(): C4670y20? {
//            var c4670y20: C4670y20?
//            synchronized(C4670y20::class.java) {
//                try {
//                    if (f == null) {
//                        f = C4670y20()
//                        Log.println(
//                            6,
//                            HttpUrl.FRAGMENT_ENCODE_SET,
//                            "Log instance=" + f
//                        )
//                    }
//                    c4670y20 = f
//                } catch (th: Throwable) {
//                    throw th
//                }
//            }
//            return c4670y20
//        }
//
//        fun f(): String {
//            val sb = StringBuilder()
//            sb.append(g)
//            sb.append("/")
//            sb.append(h)
//            val b: String = C0727Lk.b(sb, i, ".log")
//            i = (i + 1) % 2
//            return b
//        }
//
//        fun g(): Int {
//            val sb = StringBuilder()
//            sb.append(g)
//            sb.append("/")
//            val file: File = File(C2880k9.e(sb, h, "0.log"))
//            val lastModified = if (file.exists()) file.lastModified() else 0L
//            val sb2 = StringBuilder()
//            sb2.append(g)
//            sb2.append("/")
//            val file2: File = File(C2880k9.e(sb2, h, "1.log"))
//            return if (lastModified < (if (file2.exists()) file2.lastModified() else 0L)) 1 else 0
//        }
//
//        fun h(i2: Int, str: String, str2: String): Int {
//            val format: String
//            var file: File
//            val e2 = e()
//            format = try {
//                val time = Time()
//                val calendar: Calendar = Calendar.getInstance()
//                calendar.getTimeInMillis()
//                time.set(calendar.getTimeInMillis())
//                time.format("%Y-%m-%d %H:%M:%S") + "." + String.format(
//                    "%03d",
//                    java.lang.Long.valueOf(calendar.getTimeInMillis() % 1000)
//                )
//            } catch (unused: Exception) {
//                SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date(System.currentTimeMillis()))
//            }
//            try {
//                if (j) {
//                    FirebaseCrashlytics.getInstance().log("$format $str-->$str2")
//                }
//            } catch (e3: Exception) {
//                e3.printStackTrace()
//            }
//            if (!d || e2!!.c == null) {
//                return Log.println(i2, str, str2)
//            }
//            if (e) {
//                var i3 = 0
//                while (i3 <= str2.length / 1024) {
//                    val i4 = i3 * 1024
//                    i3++
//                    var i5 = i3 * 1024
//                    if (i5 > str2.length) {
//                        i5 = str2.length
//                    }
//                    Log.println(i2, str, str2.substring(i4, i5))
//                }
//            }
//            val e4 = e()
//            if (e4 != null && e4.b.also { file = it!! } != null && file.length() > 400000) {
//                try {
//                    e4.f6603a!!.close()
//                    e4.b = File(f())
//                    e4.f6603a = FileOutputStream(e4.b, false)
//                } catch (e5: IOException) {
//                    e4.b = null
//                    e5.printStackTrace()
//                }
//            }
//            synchronized(C4670y20::class.java) {
//                val sb = e2.c
//                sb!!.append("\r\n")
//                sb.append(format)
//                sb.append("--> ")
//                sb.append(str)
//                sb.append(" -->")
//                sb.append(str2)
//            }
//            e2!!.c!!.length
//            d()
//            return 0
//        }
//    }
//}