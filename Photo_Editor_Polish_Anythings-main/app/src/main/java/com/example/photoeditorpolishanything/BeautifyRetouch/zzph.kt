//package com.example.photoeditorpolishanything.BeautifyRetouch
//
//import java.io.Serializable
//
//
//
//
///* loaded from: classes2.dex */
//class zzph private constructor() : zzim {
//
//    private val zzb: zzim = zzir.zza(zzir.zzb(zzpj()))
//
//    companion object {
//        private val zza: zzph = zzph()
//
//        @JvmStatic
//        fun zzc(): Boolean {
//            zza.zza().zza()
//            return true
//        }
//
//        @JvmStatic
//        fun zzd(): Boolean {
//            return zza.zza().zzb()
//        }
//    }
//
//    override fun zza(): zzpi {
//        return this.zzb.zza() as zzpi
//    }
//}
//
//
//interface zzim {
//    fun zza(): Any?
//}
//
//
//interface zzpi {
//    fun zza(): Boolean
//    fun zzb(): Boolean
//}
//
//
//object zzir {
//    fun zza(zzimVar: zzim): zzim {
//        return if (zzimVar is zzip || zzimVar is zzin) zzimVar else if (zzimVar is Serializable) zzin(
//            zzimVar
//        ) else zzip(zzimVar)
//    }
//
//    fun zzb(obj: Any?): zzim {
//        return zziq(obj)
//    }
//}