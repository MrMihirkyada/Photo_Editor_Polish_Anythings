package com.example.photoeditorpolishanything.Api

import com.google.gson.annotations.SerializedName

data class CategorysApi(

    @field:SerializedName("baseUrl")
    val baseUrl: String,

    @field:SerializedName("templates")
    val templates: List<TemplatesItem?>
)

data class TemplatesItem(

    @field:SerializedName("birthday")
    val birthday: Birthday?,

    @field:SerializedName("calendar")
    val calendar: Calendar?,

    @field:SerializedName("love")
    val love: Love?,

    @field:SerializedName("schoollife")
    val schoollife: Schoollife?,

    @field:SerializedName("winter")
    val winter: Winter?,

    @field:SerializedName("motherday")
    val motherday: Motherday?,

    @field:SerializedName("christmas")
    val christmas: Christmas?,

    @field:SerializedName("summer")
    val summer: Summer?,

    @field:SerializedName("newyear")
    val newyear: Newyear?,

    @field:SerializedName("graduation")
    val graduation: graduation?,

    @field:SerializedName("pride")
    val pride: Pride?,

    @field:SerializedName("family")
    val family: Family?,

    @field:SerializedName("travel")
    val travel: Travel?,

    @field:SerializedName("frame")
    val frame: Frame?
)

data class Birthday(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

    @field:SerializedName("group20")
    val group20: Group?
)

data class Calendar(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?

//    @field:SerializedName("group20")
//    val group20: Group?
)

data class Love(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

    @field:SerializedName("group20")
    val group20: Group?
)

data class Schoollife(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

//    @field:SerializedName("group20")
//    val group20: Group?
)

data class Winter(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

    @field:SerializedName("group20")
    val group20: Group?
)

data class Motherday(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

    @field:SerializedName("group20")
    val group20: Group?
)

data class Christmas(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

//    @field:SerializedName("group20")
//    val group20: Group?
)

data class Summer(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

//    @field:SerializedName("group20")
//    val group20: Group?
)

data class Newyear(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

//    @field:SerializedName("group20")
//    val group20: Group?
)

data class graduation(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

//    @field:SerializedName("group19")
//    val group19: Group?,
//
//    @field:SerializedName("group20")
//    val group20: Group?
)

data class Pride(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

//    @field:SerializedName("group20")
//    val group20: Group?
)

data class Family(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

    @field:SerializedName("group20")
    val group20: Group?
)

data class Travel(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

    @field:SerializedName("group20")
    val group20: Group?
)

data class Frame(

    @field:SerializedName("group1")
    val group1: Group?,

    @field:SerializedName("group2")
    val group2: Group?,

    @field:SerializedName("group3")
    val group3: Group?,

    @field:SerializedName("group4")
    val group4: Group?,

    @field:SerializedName("group5")
    val group5: Group?,

    @field:SerializedName("group6")
    val group6: Group?,

    @field:SerializedName("group7")
    val group7: Group?,

    @field:SerializedName("group8")
    val group8: Group?,

    @field:SerializedName("group9")
    val group9: Group?,

    @field:SerializedName("group10")
    val group10: Group?,

    @field:SerializedName("group11")
    val group11: Group?,

    @field:SerializedName("group12")
    val group12: Group?,

    @field:SerializedName("group13")
    val group13: Group?,

    @field:SerializedName("group14")
    val group14: Group?,

    @field:SerializedName("group15")
    val group15: Group?,

    @field:SerializedName("group16")
    val group16: Group?,

    @field:SerializedName("group17")
    val group17: Group?,

    @field:SerializedName("group18")
    val group18: Group?,

    @field:SerializedName("group19")
    val group19: Group?,

    @field:SerializedName("group20")
    val group20: Group?
)


//--------------------------Groups----------------------------------//
data class Group1(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group2(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group3(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group4(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group5(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group6(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group7(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group8(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group9(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group10(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group11(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group12(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group13(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group14(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group15(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group16(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group17(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group18(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group19(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group20(

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String? = null
)

data class Group(
    @field:SerializedName("imageUrl")
    val imageUrl: String?,

    @field:SerializedName("imageEditUrl")
    val imageEditUrl: String?
)

data class Template(
    val id: Int,
    val imageSlots: List<ImageSlot>
)

data class ImageSlot(
    val width: Float,
    val height: Float,
    val x: Float,
    val y: Float,
    val imageUrl: String? = null
)

data class ButtonCoordinate(val x: Float, val y: Float)

//--------------------------Groups----------------------------------//
