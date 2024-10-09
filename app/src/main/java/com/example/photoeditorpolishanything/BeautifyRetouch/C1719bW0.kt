//package com.example.photoeditorpolishanything.BeautifyRetouch
//
//import android.content.Context
//import android.util.TypedValue
//
//object C1719bW0 : XW0 {
//
//    @JvmStatic
//    fun a(sb: StringBuilder, obj: Any?, hashMap: HashMap<Any, Any?>) {
//        if (obj == null) {
//            sb.append("null")
//            return
//        }
//
//        if (!obj::class.java.isArray) {
//            try {
//                sb.append(obj.toString())
//            } catch (th: Throwable) {
//                C0991Pz0.e("SLF4J: Failed toString() invocation on an object of type [${obj::class.java.name}]", th)
//                sb.append("[FAILED toString()]")
//            }
//            return
//        }
//
//        when (obj) {
//            is BooleanArray -> {
//                sb.append('[')
//                obj.joinTo(sb, ", ")
//                sb.append(']')
//            }
//            is ByteArray -> {
//                sb.append('[')
//                obj.joinTo(sb, ", ") { it.toInt() }
//                sb.append(']')
//            }
//            is CharArray -> {
//                sb.append('[')
//                obj.joinTo(sb, ", ")
//                sb.append(']')
//            }
//            is ShortArray -> {
//                sb.append('[')
//                obj.joinTo(sb, ", ") { it.toInt() }
//                sb.append(']')
//            }
//            is IntArray -> {
//                sb.append('[')
//                obj.joinTo(sb, ", ")
//                sb.append(']')
//            }
//            is LongArray -> {
//                sb.append('[')
//                obj.joinTo(sb, ", ")
//                sb.append(']')
//            }
//            is FloatArray -> {
//                sb.append('[')
//                obj.joinTo(sb, ", ")
//                sb.append(']')
//            }
//            is DoubleArray -> {
//                sb.append('[')
//                obj.joinTo(sb, ", ")
//                sb.append(']')
//            }
//            else -> {
//                val objArr = obj as Array<*>
//                sb.append('[')
//                if (hashMap.containsKey(objArr)) {
//                    sb.append("...")
//                } else {
//                    hashMap[objArr] = null
//                    objArr.joinTo(sb, ", ") { element ->
//                        a(sb, element, hashMap)
//                        null
//                    }
//                    hashMap.remove(objArr)
//                }
//                sb.append(']')
//            }
//        }
//    }
//
//    @JvmStatic
//    fun b(context: Context, f: Float): Int {
//        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, context.resources.displayMetrics).toInt()
//    }
//
//    @JvmStatic
//    fun c(context: Context): Float {
//        return context.resources.displayMetrics.density
//    }
//
//    fun d(): Boolean {
//        val bool: Boolean = false
//        return bool != null && bool == true
//    }
//
//    override fun zza(): Any {
//        return zzph.zzd()
//    }
//}
