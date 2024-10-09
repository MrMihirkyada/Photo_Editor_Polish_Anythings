//package com.example.photoeditorpolishanything.BeautifyRetouch
//
//import kotlin.concurrent.Volatile
//
//
//class zzip(zzimVar: zzim) : zzim {
//    @Volatile
//    private var zzb: zzim
//    private var zzc: Any? = null
//
//    init {
//        zzimVar.javaClass
//        zzb = zzimVar
//    }
//
//    override fun toString(): String {
//        var obj: Any = zzb
//        if (obj === zza) {
//            obj = C3214ml.c("<supplier that returned ", zzc.toString(), ">")
//        }
//        return C3214ml.c("Suppliers.memoize(", obj.toString(), ")")
//    }
//
//    // com.google.android.gms.internal.measurement.zzim
//    override fun zza(): Any? {
//        val zzimVar = zzb
//        val zzimVar2 = zza
//        if (zzimVar !== zzimVar2) {
//            synchronized(this) {
//                try {
//                    if (zzb !== zzimVar2) {
//                        val zza2 = zzb.zza()
//                        zzc = zza2
//                        zzb = zzimVar2
//                        return zza2
//                    }
//                } finally {
//                }
//            }
//        }
//        return zzc
//    }
//
//    companion object {
//        private val zza: zzim = object : zzim {
//            // from class: com.google.android.gms.internal.measurement.zzio
//            // com.google.android.gms.internal.measurement.zzim
//            override fun zza(): Any? {
//                throw IllegalStateException()
//            }
//        }
//    }
//}