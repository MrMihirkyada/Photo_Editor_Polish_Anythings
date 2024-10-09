package com.example.photoeditorpolishanything.Api

import com.google.gson.annotations.SerializedName

data class BackgroundApi(

	@field:SerializedName("baseUrl")
	val baseUrl: String?,

	@field:SerializedName("data")
	val data: DataItem?
)

data class DataItem(

	@field:SerializedName("i love you mom")
	val iLoveYouMom: ILoveYouMom?,

	@field:SerializedName("I love You")
	val iLoveYou: ILoveYous?,

	@field:SerializedName("Patnicks")
	val patnicks: Patnicks?,

	@field:SerializedName("XOXO")
	val xOXO: XOXOs?,

	@field:SerializedName("trick or treat")
	val trickOrTreat: TrickOrTreats?,

	@field:SerializedName("happy children day")
	val happyChildrenDay: HappyChildrenDay?,

	@field:SerializedName("pumkin")
	val pumkin: Pumkin?,

	@field:SerializedName("Our Dream Wedding")
	val ourDreamWedding: OurDreamWedding?,

	@field:SerializedName("think pink")
	val thinkPink: ThinkPink?,

	@field:SerializedName("Childlike Graffiti")
	val childlikeGraffiti: ChildlikeGraffiti?,

	@field:SerializedName("merry christmas")
	val merryChristmas: MerryChristmass?,

	@field:SerializedName("Fade texture")
	val fadeTexture: FadeTexture?,

	@field:SerializedName("wrap it up")
	val wrapItUp: WrapItUp?,

	@field:SerializedName("Space")
	val space: Space?,

	@field:SerializedName("falling in love")
	val fallingInLove: FallingInLove?,

	@field:SerializedName("holographic")
	val holographic: Holographic?,

	@field:SerializedName("Happy Labour Day")
	val happyLabourDay: HappyLabourDay?,

	@field:SerializedName("Worldwide")
	val worldwide: Worldwide?,

	@field:SerializedName("Oasis")
	val oasis: Oasis?,

	@field:SerializedName("Ramjan")
	val ramjan: Ramjans?,

	@field:SerializedName("woldlife")
	val woldlife: Woldlife?,

	@field:SerializedName("Gold Dust")
	val goldDust: GoldDust?,

	@field:SerializedName("Daisy")
	val daisy: Daisy?,

	@field:SerializedName("happy birthday")
	val happyBirthday: HappyBirthdays?,

	@field:SerializedName("Pinky")
	val pinky: Pinky?,

	@field:SerializedName("Watercolor")
	val watercolor: Watercolor?,

	@field:SerializedName("Summer Game")
	val summerGame: SummerGame?,

	@field:SerializedName("childrens day")
	val childrensDay: ChildrensDay?,

	@field:SerializedName("tropical vibes")
	val tropicalVibes: TropicalVibees?,

	@field:SerializedName("happy thanksgiving")
	val happyThanksgiving: HappyThanksgivings?,

	@field:SerializedName("Memphis Event")
	val memphisEvent: MemphisEvent?,

	@field:SerializedName("Dots")
	val dots: Dots?,

	@field:SerializedName("Winter wonderland")
	val winterWonderland: WinterWonderland?,

	@field:SerializedName("happy hour")
	val happyHour: HappyHour?,

	@field:SerializedName("Note and Paper")
	val noteAndPaper: NoteAndPaper?,

	@field:SerializedName("Pink Wedding")
	val pinkWedding: PinkWedding?,

	@field:SerializedName("Texture")
	val texture: Texture?,

	@field:SerializedName("happy pride month")
	val happyPrideMonth: HappyPrideMonth?,

	@field:SerializedName("Hello Fall")
	val helloFall: HelloFall?,

	@field:SerializedName("hello spring")
	val helloSpring: HelloSpring?,

	@field:SerializedName("Love you mom")
	val loveYouMom: LoveYouMom?,

	@field:SerializedName("Pattern")
	val pattern: Pattern?,

	@field:SerializedName("be mine")
	val beMine: BeMine?,

	@field:SerializedName("Peach fuzz vibes")
	val peachFuzzVibes: PeachFuzzVibes?,

	@field:SerializedName("Abstract")
	val abstract: Abstract?,

	@field:SerializedName("happy graduation")
	val happyGraduation: HappyGraduation?,

	@field:SerializedName("Dia De Muertos")
	val diaDeMuertos: DiaDeMuertos?,

	@field:SerializedName("tropical")
	val tropical: Tropical?,

	@field:SerializedName("Pool Party")
	val poolParty: PoolParty?,

	@field:SerializedName("super dad")
	val superDad: SuperDad?,

	@field:SerializedName("Holiday")
	val holiday: Holiday?,

	@field:SerializedName("Eid Al Adha")
	val eidAlAdha: EidAlAdha?,

	@field:SerializedName("Happy Holi")
	val happyHoli: HappyHolis?,

	@field:SerializedName("Love Terims")
	val loveTerims: LoveTerims?,

	@field:SerializedName("job boards")
	val jobBoards: JobBoards?,

	@field:SerializedName("Botanical")
	val botanical: Botanical?,

	@field:SerializedName("Summer")
	val summer: Summers?,

	@field:SerializedName("Welcome autumn")
	val welcomeAutumn: WelcomeAutumn?,

	@field:SerializedName("vintage paper")
	val vintagePaper: VintagePaper?,

	@field:SerializedName("happy new year")
	val happyNewYear: HappyNewYears?,

	@field:SerializedName("Ester")
	val ester: Ester?,

	@field:SerializedName("halloween")
	val halloween: Halloweens?,

	@field:SerializedName("Kaleidoscope Pattern")
	val kaleidoscopePattern: KaleidoscopePattern?,

	@field:SerializedName("Ornaments")
	val ornaments: Ornaments?,

	@field:SerializedName("Summer Time")
	val summerTime: SummerTime?,

	@field:SerializedName("fathers day")
	val fathersDay: FathersDay?,

	@field:SerializedName("Happy Diwali")
	val happyDiwali: HappyDiwalis?,

	@field:SerializedName("Glowing Party Night")
	val glowingPartyNight: GlowingPartyNight?,

	@field:SerializedName("happy graduation day")
	val happyGraduationDay: HappyGraduationDay?,

	@field:SerializedName("Autumn")
	val autumn: Autumns?,

	@field:SerializedName("pride month")
	val prideMonth: PrideMonth?,

	@field:SerializedName("Bohemian Patterns")
	val bohemianPatterns: BohemianPatterns?,

	@field:SerializedName("happy easter")
	val happyEaster: HappyEasters?,

	@field:SerializedName("happy mothers day")
	val happyMothersDay: HappyMothersDays?,

	@field:SerializedName("Marble")
	val marble: Marble?,

	@field:SerializedName("christmas")
	val christmas: Christmass?,

	@field:SerializedName("Fair Trade Fruits")
	val fairTradeFruits: FairTradeFruits?,

	@field:SerializedName("Floral")
	val floral: Floral?,

	@field:SerializedName("Lucky")
	val lucky: Lucky?,

	@field:SerializedName("valentine day")
	val valentineDay: ValentineDays?,

	@field:SerializedName("Happy Halloween")
	val happyHalloween: HappyHalloweens?,

	@field:SerializedName("sweet little family")
	val sweetLittleFamily: SweetLittleFamily?,

	@field:SerializedName("best day ever")
	val bestDayEver: BestDayEver?,

	@field:SerializedName("Pink")
	val pink: Pink?,

	@field:SerializedName("welcome back to school")
	val welcomeBackToSchool: WelcomeBackToSchools?,

	@field:SerializedName("Love")
	val love: Loves?,

	@field:SerializedName("Checkered Hearts")
	val checkeredHearts: CheckeredHearts?,

	@field:SerializedName("Rainbow Fun")
	val rainbowFun: RainbowFun?,

	@field:SerializedName("flower vintage")
	val flowerVintage: FlowerVintage?,

	@field:SerializedName("Holo")
	val holo: Holo?,

	@field:SerializedName("unicorn are real")
	val unicornAreReal: UnicornAreReal?,

	@field:SerializedName("emoji banner")
	val emojiBanner: EmojiBanner?,

	@field:SerializedName("Flowers")
	val flowers: Flowers?,

	@field:SerializedName("ARTISTIC")
	val aRTISTIC: ARTISTIC?,

	@field:SerializedName("uicon")
	val uicon: Uicon?,

	@field:SerializedName("Remember me")
	val rememberMe: RememberMe?,

	@field:SerializedName("Water Filtration")
	val waterFiltration: WaterFiltration?,

	@field:SerializedName("black white")
	val blackWhite: BlackWhite?,

	@field:SerializedName("Best Dad")
	val bestDad: BestDad?,

	@field:SerializedName("Emoji")
	val emoji: Emojis?,

	@field:SerializedName("Congratulations Graduates")
	val congratulationsGraduates: CongratulationsGraduates?,

	@field:SerializedName("snow world")
	val snowWorld: SnowWorld?,

	@field:SerializedName("vintage paper texture")
	val vintagePaperTexture: VintagePaperTexture?,

	@field:SerializedName("Vibrante Carnaval")
	val vibranteCarnaval: VibranteCarnaval?,

	@field:SerializedName("graduation season")
	val graduationSeason: GraduationSeason?,

	@field:SerializedName("the whole")
	val theWhole: TheWhole?,

	@field:SerializedName("baby boom")
	val babyBoom: BabyBoom?,

	@field:SerializedName("hug and kiss")
	val hugAndKiss: HugAndKiss?,

	@field:SerializedName("Crumpled Kraft Paper")
	val crumpledKraftPaper: CrumpledKraftPaper?,

	@field:SerializedName("happy teachers day")
	val happyTeachersDay: HappyTeachersDays?,

	@field:SerializedName("Happy Autumn")
	val happyAutumn: HappyAutumn?,

	@field:SerializedName("All About the love")
	val allAboutTheLove: AllAboutTheLove?,

	@field:SerializedName("Cloud")
	val cloud: Cloud?,

	@field:SerializedName("Dia Dos Namorados")
	val diaDosNamorados: DiaDosNamoradoss?,

	@field:SerializedName("Paper")
	val paper: Paper?,

	@field:SerializedName("japanese Pattern")
	val japanesePattern: JapanesePattern?,

	@field:SerializedName("Back to School")
	val backToSchool: BackToSchool?,

	@field:SerializedName("pumpkin")
	val pumpkin: Pumpkin?
)

data class Groups(
	val textCategory: String?,

	@field:SerializedName("subImageUrl")
	val subImageUrl: List<String?>?,

	@field:SerializedName("mainImageUrl")
	val mainImageUrl: List<String?>?
)

data class BestDayEver(

	@field:SerializedName("Group")
	val group: Groups?
)

data class ValentineDays(

	@field:SerializedName("Group")
	val group: Groups?
)

data class FadeTexture(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Worldwide(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HugAndKiss(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Holographic(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Pattern(

	@field:SerializedName("Group")
	val group: Groups?
)

data class MemphisEvent(

	@field:SerializedName("Group")
	val group: Groups?
)

data class BeMine(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyChildrenDay(

	@field:SerializedName("Group")
	val group: Groups?
)

data class SnowWorld(

	@field:SerializedName("Group")
	val group: Groups?
)

data class MerryChristmass(

	@field:SerializedName("Group")
	val group: Groups?
)

data class TheWhole(

	@field:SerializedName("Group")
	val group: Groups?
)

data class ILoveYous(

	@field:SerializedName("Group")
	val group: Groups?
)

data class BohemianPatterns(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyTeachersDays(

	@field:SerializedName("Group")
	val group: Groups?
)

data class BabyBoom(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Pumpkin(

	@field:SerializedName("Group")
	val group: Groups?
)

data class ChildrensDay(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Tropical(

	@field:SerializedName("Group")
	val group: Groups?
)

data class FallingInLove(

	@field:SerializedName("Group")
	val group: Groups?
)

data class VibranteCarnaval(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Daisy(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyHalloweens(

	@field:SerializedName("Group")
	val group: Groups?
)

data class WelcomeBackToSchools(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Dots(

	@field:SerializedName("Group")
	val group: Groups?
)

data class VintagePaper(

	@field:SerializedName("Group")
	val group: Groups?
)

data class ARTISTIC(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Marble(

	@field:SerializedName("Group")
	val group: Groups?
)

data class CongratulationsGraduates(

	@field:SerializedName("Group")
	val group: Groups?
)

data class ThinkPink(

	@field:SerializedName("Group")
	val group: Groups?
)

data class BlackWhite(

	@field:SerializedName("Group")
	val group: Groups?
)

data class SummerTime(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyMothersDays(

	@field:SerializedName("Group")
	val group: Groups?
)

data class FlowerVintage(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyGraduation(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Summers(

	@field:SerializedName("Group")
	val group: Groups?
)

data class OurDreamWedding(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Patnicks(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HelloSpring(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Floral(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Botanical(

	@field:SerializedName("Group")
	val group: Groups?
)

data class SummerGame(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Pumkin(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Uicon(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyHolis(

	@field:SerializedName("Group")
	val group: Groups?
)

data class ChildlikeGraffiti(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyThanksgivings(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Autumns(

	@field:SerializedName("Group")
	val group: Groups?
)

data class WelcomeAutumn(

	@field:SerializedName("Group")
	val group: Groups?
)

data class RememberMe(

	@field:SerializedName("Group")
	val group: Groups?
)

data class VintagePaperTexture(

	@field:SerializedName("Group")
	val group: Groups?
)

data class NoteAndPaper(

	@field:SerializedName("Group")
	val group: Groups?
)

data class DiaDeMuertos(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyHour(

	@field:SerializedName("Group")
	val group: Groups?
)

data class AllAboutTheLove(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyNewYears(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyEasters(

	@field:SerializedName("Group")
	val group: Groups?
)

data class XOXOs(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Holiday(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Texture(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Woldlife(

	@field:SerializedName("Group")
	val group: Groups?
)

data class TropicalVibees(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyLabourDay(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Halloweens(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyDiwalis(

	@field:SerializedName("Group")
	val group: Groups?
)

data class GoldDust(

	@field:SerializedName("Group")
	val group: Groups?
)

data class DiaDosNamoradoss(

	@field:SerializedName("Group")
	val group: Groups?
)

data class TrickOrTreats(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Loves(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Ramjans(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Pinky(

	@field:SerializedName("Group")
	val group: Groups?
)

data class BackToSchool(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyPrideMonth(

	@field:SerializedName("Group")
	val group: Groups?
)

data class WrapItUp(

	@field:SerializedName("Group")
	val group: Groups?
)

data class SuperDad(

	@field:SerializedName("Group")
	val group: Groups?
)

data class ILoveYouMom(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Ornaments(

	@field:SerializedName("Group")
	val group: Groups?
)

data class PinkWedding(

	@field:SerializedName("Group")
	val group: Groups?
)

data class PrideMonth(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Ester(

	@field:SerializedName("Group")
	val group: Groups?
)

data class GraduationSeason(

	@field:SerializedName("Group")
	val group: Groups?
)

data class PeachFuzzVibes(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HelloFall(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyAutumn(

	@field:SerializedName("Group")
	val group: Groups?
)

data class SweetLittleFamily(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Flowers(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Watercolor(

	@field:SerializedName("Group")
	val group: Groups?
)

data class JobBoards(

	@field:SerializedName("Group")
	val group: Groups?
)

data class FathersDay(

	@field:SerializedName("Group")
	val group: Groups?
)

data class CheckeredHearts(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Paper(

	@field:SerializedName("Group")
	val group: Groups?
)

data class PoolParty(

	@field:SerializedName("Group")
	val group: Groups?
)

data class LoveTerims(

	@field:SerializedName("Group")
	val group: Groups?
)

data class WaterFiltration(

	@field:SerializedName("Group")
	val group: Groups?
)

data class UnicornAreReal(

	@field:SerializedName("Group")
	val group: Groups?
)

data class RainbowFun(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Christmass(

	@field:SerializedName("Group")
	val group: Groups?
)

data class FairTradeFruits(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Pink(

	@field:SerializedName("Group")
	val group: Groups?
)

data class BestDad(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Lucky(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Oasis(

	@field:SerializedName("Group")
	val group: Groups?
)

data class KaleidoscopePattern(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Space(

	@field:SerializedName("Group")
	val group: Groups?
)

data class EmojiBanner(

	@field:SerializedName("Group")
	val group: Groups?
)

data class CrumpledKraftPaper(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyGraduationDay(

	@field:SerializedName("Group")
	val group: Groups?
)

data class HappyBirthdays(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Emojis(

	@field:SerializedName("Group")
	val group: Groups?
)

data class EidAlAdha(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Cloud(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Holo(

	@field:SerializedName("Group")
	val group: Groups?
)

data class JapanesePattern(

	@field:SerializedName("Group")
	val group: Groups?
)

data class WinterWonderland(

	@field:SerializedName("Group")
	val group: Groups?
)

data class LoveYouMom(

	@field:SerializedName("Group")
	val group: Groups?
)

data class GlowingPartyNight(

	@field:SerializedName("Group")
	val group: Groups?
)

data class Abstract(

	@field:SerializedName("Group")
	val group: Groups?
)
