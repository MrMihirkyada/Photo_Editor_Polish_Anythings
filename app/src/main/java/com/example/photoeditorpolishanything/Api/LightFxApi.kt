package com.example.photoeditorpolishanything.Api

import com.google.gson.annotations.SerializedName

data class LightFxApi(

	@field:SerializedName("baseUrl")
	val baseUrl: String?,

	@field:SerializedName("data")
	val data: DataItems?
)

data class DataItems(

	@field:SerializedName("Heart")
	val heart: Heart?,

	@field:SerializedName("Holi")
	val holi: Holi? ,

	@field:SerializedName("shape Bokeh")
	val shapeBokeh: ShapeBokeh? ,

	@field:SerializedName("Light and Shadow")
	val lightAndShadow: LightAndShadow?,

	@field:SerializedName("Smoke")
	val smoke: Smoke?,

	@field:SerializedName("Torn Paper")
	val tornPaper: TornPaper?,

	@field:SerializedName("Rusty effects")
	val rustyEffects: RustyEffects?,

	@field:SerializedName("Wrinkle Paper")
	val wrinklePaper: WrinklePaper?,

	@field:SerializedName("Leaks")
	val leaks: Leaks?,

	@field:SerializedName("Miracle Light")
	val miracleLight: MiracleLight?,

	@field:SerializedName("Bubble")
	val bubble: Bubble?,

	@field:SerializedName("Fog Overlay")
	val fogOverlay: FogOverlay?,

	@field:SerializedName("Neon")
	val neon: Neons?,

	@field:SerializedName("Rainbow")
	val rainbow: Rainbow?,

	@field:SerializedName("Bokeh")
	val bokeh: Bokeh?,

	@field:SerializedName("Fireworks")
	val fireworks: Fireworks?,

	@field:SerializedName("Glitch")
	val glitch: Glitch?,

	@field:SerializedName("Dust and Scratch")
	val dustAndScratch: DustAndScratch?,

	@field:SerializedName("Broken Glass")
	val brokenGlass: BrokenGlass?,

	@field:SerializedName("Xmas Spakle")
	val xmasSpakle: XmasSpakle?,

	@field:SerializedName("Explosion")
	val explosion: Explosion?,

	@field:SerializedName("Autumn")
	val autumn: Autumnss?,

	@field:SerializedName("Film Leak")
	val filmLeak: FilmLeak?,

	@field:SerializedName("Grain Splash")
	val grainSplash: GrainSplash?,

	@field:SerializedName("Plastic Wrap")
	val plasticWrap: PlasticWrap? ,

	@field:SerializedName("Diamond")
	val diamond: Diamond? ,

	@field:SerializedName("Confetti")
	val confetti: Confetti? ,

	@field:SerializedName("Spring")
	val spring: Springs? ,

	@field:SerializedName("String Lights")
	val stringLights: StringLights?,

	@field:SerializedName("Speed Lines")
	val speedLines: SpeedLines?,

	@field:SerializedName("Powder explosion")
	val powderExplosion: PowderExplosion?,

	@field:SerializedName("face Shadow")
	val faceShadow: FaceShadow? ,

	@field:SerializedName("Dreamy light")
	val dreamyLight: DreamyLight? ,

	@field:SerializedName("Light Leaks")
	val lightLeaks: LightLeaks? ,

	@field:SerializedName("Lightning")
	val lightning: Lightning? ,

	@field:SerializedName("Love")
	val love: Lovees? ,

	@field:SerializedName("Fire")
	val fire: Fire? ,

	@field:SerializedName("Fantasy")
	val fantasy: Fantasy? ,

	@field:SerializedName("Rainy Day")
	val rainyDay: RainyDay? ,

	@field:SerializedName("Splash")
	val splash: Splash? ,

	@field:SerializedName("Halloween")
	val halloween: Halloween? ,

	@field:SerializedName("Dust and Sunlight")
	val dustAndSunlight: DustAndSunlight? ,

	@field:SerializedName("Flurries")
	val flurries: Flurries? ,

	@field:SerializedName("Halo")
	val halo: Halo? ,

	@field:SerializedName("lens flares")
	val lensFlares: LensFlares?
)


data class Groupes(

	var textCategory: String?,

	@field:SerializedName("subImageUrl")
	val subImageUrl: List<String?>?,

	@field:SerializedName("mainImageUrl")
	val mainImageUrl: List<String?>?
)


data class Leaks(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Halo(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Holi(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class RainyDay(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Confetti(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class BrokenGlass(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Splash(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class WrinklePaper(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Lovees(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class DustAndScratch(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Fantasy(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class PlasticWrap(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class FogOverlay(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class FaceShadow(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Fire(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Springs(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class StringLights(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class FilmLeak(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class LensFlares(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Flurries(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class RustyEffects(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Explosion(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class LightAndShadow(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class LightLeaks(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class DustAndSunlight(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class DreamyLight(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Autumnss(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class PowderExplosion(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Glitch(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Bokeh(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class ShapeBokeh(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class MiracleLight(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Halloween(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class GrainSplash(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Bubble(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Rainbow(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Smoke(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class TornPaper(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Fireworks(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Diamond(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Neons(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class XmasSpakle(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class SpeedLines(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Lightning(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Heart(

	@field:SerializedName("Group")
	val group: Groupes?
)
