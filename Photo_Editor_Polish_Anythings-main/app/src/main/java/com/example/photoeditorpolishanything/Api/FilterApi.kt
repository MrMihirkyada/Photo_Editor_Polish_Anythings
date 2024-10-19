package com.example.photoeditorpolishanything.Api

import com.google.gson.annotations.SerializedName

data class FilterApi(

	@field:SerializedName("baseUrl")
	val baseUrl: String?,

	@field:SerializedName("data")
	val data: Dataes?
)

data class Dataes(

	@field:SerializedName("Clean Hue")
	val cleanHue: CleanHue?,

	@field:SerializedName("Fresh")
	val fresh: Fresh?,

	@field:SerializedName("Wes Anderson")
	val wesAnderson: WesAnderson?,

	@field:SerializedName("Lofi")
	val lofi: Lofi?,

	@field:SerializedName("Pop Art")
	val popArt: PopArt?,

	@field:SerializedName("Film")
	val film: Film?,

	@field:SerializedName("Duo Tone")
	val duoTone: DuoTone?,

	@field:SerializedName("Interior")
	val interior: Interior?,

	@field:SerializedName("Black and White")
	val blackAndWhite: BlackAndWhite?,

	@field:SerializedName("Cedar")
	val cedar: Cedar?,

	@field:SerializedName("Bright")
	val bright: Bright?,

	@field:SerializedName("Retro")
	val retro: Retro?,

	@field:SerializedName("Wander")
	val wander: Wander?,

	@field:SerializedName("Vintage")
	val vintage: Vintage?,

	@field:SerializedName("Urban")
	val urban: Urban?,

	@field:SerializedName("Chroma")
	val chroma: Chroma?,

	@field:SerializedName("Nostalgia")
	val nostalgia: Nostalgia?,

	@field:SerializedName("Indie Kid Effect")
	val indieKidEffect: IndieKidEffect?,

	@field:SerializedName("Toasty Bliss")
	val toastyBliss: ToastyBliss?,

	@field:SerializedName("Food")
	val food: Food?,

	@field:SerializedName("Diffuse")
	val diffuse: Diffuse?,

	@field:SerializedName("Autumn")
	val autumn: Autumnes?,

	@field:SerializedName("Aerochrome")
	val aerochrome: Aerochrome?,

	@field:SerializedName("Sporting Life")
	val sportingLife: SportingLife?,

	@field:SerializedName("Romance")
	val romance: Romance?,

	@field:SerializedName("Spring")
	val spring: Spring?,

	@field:SerializedName("Split Tone")
	val splitTone: SplitTone?,

	@field:SerializedName("Mood")
	val mood: Mood?,

	@field:SerializedName("Foodle")
	val foodle: Foodle?,

	@field:SerializedName("Cyberpunk")
	val cyberpunk: Cyberpunk?,

	@field:SerializedName("Analog")
	val analog: Analog?,

	@field:SerializedName("Serene")
	val serene: Serene?
)

data class Foodle(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Groupess(

	var textCategory: String?,

	@field:SerializedName("subImageUrl")
	val subImageUrl: List<String?>?,

	@field:SerializedName("mainImageUrl")
	val mainImageUrl: List<String?>?
)


data class Nostalgia(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Cedar(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class SportingLife(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Urban(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Mood(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Analog(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Spring(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Interior(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Bright(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class SplitTone(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class ToastyBliss(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Diffuse(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Lofi(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class BlackAndWhite(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class IndieKidEffect(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Aerochrome(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Chroma(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Romance(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Cyberpunk(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Vintage(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Wander(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Autumnes(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Serene(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class PopArt(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Fresh(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class DuoTone(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Food(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class CleanHue(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class WesAnderson(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Retro(

	@field:SerializedName("Group")
	val group: Groupess?
)

data class Film(

	@field:SerializedName("Group")
	val group: Groupess?
)