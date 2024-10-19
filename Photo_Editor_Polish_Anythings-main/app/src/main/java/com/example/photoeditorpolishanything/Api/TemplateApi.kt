package com.example.photoeditorpolishanything.Api

data class TemplateResponse(
    val baseUrl : String,
    val templates : List<Template>
)

data class Templates(
    val birthday : Birthdays
)

data class Birthdays(
    val group1 : Groupsss
)

data class Groupsss(
    val imageUrl : String,
    val imageEditUrl : String,
    val dynamicImageAdd : List<DynamicImage>
)

data class DynamicImage(
    val layout : Layout,
    val position : Position
)

data class Layout(
    val width : Int,
    val height : Int
)

data class Position(
    val x : Int,
    val y : Int
)
