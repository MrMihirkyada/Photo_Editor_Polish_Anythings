package com.example.photoeditorpolishanything.StoreFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoeditorpolishanything.Adapter.BackgroundAdapter
import com.example.photoeditorpolishanything.Api.DataItem
import com.example.photoeditorpolishanything.Api.Groups
import com.example.photoeditorpolishanything.Api.OkHttpHelperBackground
import com.example.photoeditorpolishanything.databinding.FragmentAllBackgroundBinding
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class Store_All_Background_Fragment : Fragment()
{
    private lateinit var binding: FragmentAllBackgroundBinding
    lateinit var adapter: BackgroundAdapter

    lateinit var groupsMap : Map<String, Groups?>

    private val groupsList = mutableListOf<Groups>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentAllBackgroundBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        ReadJSONFromAssets(requireContext(),"lightfx.json")

        initView()
    }

    private fun initView()
    {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BackgroundAdapter(requireActivity(),groupsList)
        binding.recyclerView.adapter = adapter

        val url = "https://s3.eu-north-1.amazonaws.com/photoeditorbeautycamera.com/photoeditor/background.json"

        OkHttpHelperBackground.fetchBackground(url) { backgroundApi ->
            if (backgroundApi != null) {
                Log.e("BackgroundFragment", "Fetched data: ${backgroundApi.data}")

                backgroundApi.data?.let {
                    populateGroupsList(it)
                    requireActivity().runOnUiThread {
                        adapter.updateData(groupsList)
                    }
                } ?: Log.e("BackgroundFragment", "Data is null")
            } else {
                Log.e("BackgroundFragment", "Failed to fetch data")
            }
        }
    }

    private fun populateGroupsList(data: DataItem) {
        val items = mutableListOf<Groups>()

        // Use Kotlin reflection to iterate over the properties of the Dataas class
        data::class.memberProperties.forEach { property ->
            // Cast the property to KProperty1<Dataas, *>
            val prop = property as? KProperty1<DataItem, *>
            val value = prop?.get(data)

            // Check if the value is not null and is of type Groupas or contains Groupas
            if (value != null)
            {
                // Check if the value is a Groupas instance
                if (value is Groups)
                {
                    val categoryName = property.name.replace("_", " ").capitalizeWords()

                    value.let {
                        if (it.subImageUrl != null || it.mainImageUrl != null)
                        {
                            items.add(
                                Groups(
                                    subImageUrl = it.subImageUrl,
                                    mainImageUrl = it.mainImageUrl,
                                    textCategory = categoryName
                                )
                            )
                        }
                    }
                }
                else
                {
                    // If value is not a Groupas instance, use reflection to check nested properties
                    val groupProperty = value::class.memberProperties
                        .firstOrNull { it.returnType.classifier == Groups::class }
                            as? KProperty1<Any, Groups>

                    val nestedGroup = groupProperty?.get(value)

                    if (nestedGroup != null) {
                        val categoryName = property.name.replace("_", " ").capitalizeWords()

                        nestedGroup.let {
                            if (it.subImageUrl != null || it.mainImageUrl != null) {
                                items.add(
                                    Groups(
                                        subImageUrl = it.subImageUrl,
                                        mainImageUrl = it.mainImageUrl,
                                        textCategory = categoryName
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        groupsList.clear()
        groupsList.addAll(items)
    }

    // Extension function to capitalize words
    private fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

//    private fun populateGroupsList(data: Data) {
//        val items = mutableListOf<Groups>()
//
//        fun addGroupasIfValid(Groups: Groups?, category: String)
//        {
//            Groups?.let {
//                if (it.subImageUrl != null || it.mainImageUrl != null)
//                {
//                    items.add(
//                        Groups(
//                            subImageUrl = it.subImageUrl,
//                            mainImageUrl = it.mainImageUrl,
//                            textCategory = category
//                        )
//                    )
//                }
//            }
//        }
//        addGroupasIfValid(data.abstract?.group, "Abstract")
//        addGroupasIfValid(data.allAboutTheLove?.group, "All About the love")
//        addGroupasIfValid(data.aRTISTIC?.group, "ARTISTIC")
//        addGroupasIfValid(data.autumn?.group, "Autumn")
//        addGroupasIfValid(data.babyBoom?.group, "baby boom")
//        addGroupasIfValid(data.backToSchool?.group, "Back to School")
//        addGroupasIfValid(data.beMine?.group, "Be Mine")
//        addGroupasIfValid(data.bestDad?.group, "Best Dad")
//        addGroupasIfValid(data.bestDayEver?.group, "Best Day Ever")
//        addGroupasIfValid(data.blackWhite?.group, "black white")
//        addGroupasIfValid(data.bohemianPatterns?.group, "Bohemian Patterns")
//        addGroupasIfValid(data.botanical?.group, "Botanical")
//        addGroupasIfValid(data.checkeredHearts?.group, "Checkered Hearts")
//        addGroupasIfValid(data.childlikeGraffiti?.group, "Childlike Graffiti")
//        addGroupasIfValid(data.childrensDay?.group, "children's day")
//        addGroupasIfValid(data.christmas?.group, "Christmas")
//        addGroupasIfValid(data.cloud?.group, "Cloud")
//        addGroupasIfValid(data.congratulationsGraduates?.group, "Congratulations Graduates")
//        addGroupasIfValid(data.crumpledKraftPaper?.group, "Crumpled Kraft Paper")
//        addGroupasIfValid(data.daisy?.group, "Daisy")
//        addGroupasIfValid(data.diaDeMuertos?.group, "Dia De Muertos")
//        addGroupasIfValid(data.diaDosNamorados?.group, "Dia Dos Namorados")
//        addGroupasIfValid(data.dots?.group, "Dots")
//        addGroupasIfValid(data.eidAlAdha?.group, "Eid Al Adha")
//        addGroupasIfValid(data.emoji?.group, "Emoji")
//        addGroupasIfValid(data.emojiBanner?.group, "emoji banner")
//        addGroupasIfValid(data.ester?.group, "Easter")
//        addGroupasIfValid(data.fadeTexture?.group, "Fade texture")
//        addGroupasIfValid(data.fairTradeFruits?.group, "Fair Trade Fruits")
//        addGroupasIfValid(data.fallingInLove?.group, "falling in love")
//        addGroupasIfValid(data.fathersDay?.group, "Father's day")
//        addGroupasIfValid(data.floral?.group, "Floral")
//        addGroupasIfValid(data.flowerVintage?.group, "flower vintage")
//        addGroupasIfValid(data.flowers?.group, "Flowers")
//        addGroupasIfValid(data.glowingPartyNight?.group, "Glowing Party Night")
//        addGroupasIfValid(data.goldDust?.group, "Gold Dust")
//        addGroupasIfValid(data.graduationSeason?.group, "graduation season")
//        addGroupasIfValid(data.halloween?.group, "Halloween")
//        addGroupasIfValid(data.happyAutumn?.group, "Happy Autumn")
//        addGroupasIfValid(data.happyBirthday?.group, "Happy Birthday")
//        addGroupasIfValid(data.happyChildrenDay?.group, "happy children's day")
//        addGroupasIfValid(data.happyDiwali?.group, "Happy Diwali")
//        addGroupasIfValid(data.happyEaster?.group, "Happy Easter")
//        addGroupasIfValid(data.happyGraduation?.group, "Happy Graduation")
//        addGroupasIfValid(data.happyGraduationDay?.group, "happy graduation day")
//        addGroupasIfValid(data.happyHalloween?.group, "Happy Halloween")
//        addGroupasIfValid(data.happyHoli?.group, "Happy Holi")
//        addGroupasIfValid(data.happyHour?.group, "happy hour")
//        addGroupasIfValid(data.happyLabourDay?.group, "Happy Labour Day")
//        addGroupasIfValid(data.happyMothersDay?.group, "happy mother's day")
//        addGroupasIfValid(data.happyNewYear?.group, "happy new year")
//        addGroupasIfValid(data.happyPrideMonth?.group, "happy pride month")
//        addGroupasIfValid(data.happyTeachersDay?.group, "happy teachers day")
//        addGroupasIfValid(data.happyThanksgiving?.group, "Happy Thanksgiving")
//        addGroupasIfValid(data.helloFall?.group, "Hello Fall")
//        addGroupasIfValid(data.helloSpring?.group, "Hello Spring")
//        addGroupasIfValid(data.holiday?.group, "Holiday")
//        addGroupasIfValid(data.holo?.group, "Holo")
//        addGroupasIfValid(data.holographic?.group, "Holographic")
//        addGroupasIfValid(data.hugAndKiss?.group, "Hug and Kiss")
//        addGroupasIfValid(data.iLoveYou?.group, "I love You")
//        addGroupasIfValid(data.iLoveYouMom?.group, "I love you mom")
//        addGroupasIfValid(data.japanesePattern?.group, "Japanese Pattern")
//        addGroupasIfValid(data.jobBoards?.group, "job boards")
//        addGroupasIfValid(data.kaleidoscopePattern?.group, "Kaleidoscope Pattern")
//        addGroupasIfValid(data.love?.group, "Love")
//        addGroupasIfValid(data.loveTerims?.group, "Love Terms")
//        addGroupasIfValid(data.loveYouMom?.group, "Love you mom")
//        addGroupasIfValid(data.lucky?.group, "Lucky")
//        addGroupasIfValid(data.marble?.group, "Marble")
//        addGroupasIfValid(data.memphisEvent?.group, "Memphis Event")
//        addGroupasIfValid(data.merryChristmas?.group, "Merry Christmas")
//        addGroupasIfValid(data.noteAndPaper?.group, "Note and Paper")
//        addGroupasIfValid(data.oasis?.group, "Oasis")
//        addGroupasIfValid(data.ornaments?.group, "Ornaments")
//        addGroupasIfValid(data.ourDreamWedding?.group, "Our Dream Wedding")
//        addGroupasIfValid(data.paper?.group, "Paper")
//        addGroupasIfValid(data.patnicks?.group, "Patnicks")
//        addGroupasIfValid(data.pattern?.group, "Pattern")
//        addGroupasIfValid(data.peachFuzzVibes?.group, "Peach fuzz vibes")
//        addGroupasIfValid(data.pink?.group, "Pink")
//        addGroupasIfValid(data.pinkWedding?.group, "Pink Wedding")
//        addGroupasIfValid(data.pinky?.group, "Pinky")
//        addGroupasIfValid(data.poolParty?.group, "Pool Party")
//        addGroupasIfValid(data.prideMonth?.group, "Pride Month")
//        addGroupasIfValid(data.pumkin?.group, "Pumpkin")
//        addGroupasIfValid(data.pumpkin?.group, "Pumpkin")
//        addGroupasIfValid(data.rainbowFun?.group, "Rainbow Fun")
//        addGroupasIfValid(data.ramjan?.group, "Ramjan")
//        addGroupasIfValid(data.rememberMe?.group, "Remember me")
//        addGroupasIfValid(data.snowWorld?.group, "Snow World")
//        addGroupasIfValid(data.space?.group, "Space")
//        addGroupasIfValid(data.summer?.group, "Summer")
//        addGroupasIfValid(data.summerGame?.group, "Summer Game")
//        addGroupasIfValid(data.summerTime?.group, "Summer Time")
//        addGroupasIfValid(data.superDad?.group, "Super Dad")
//        addGroupasIfValid(data.sweetLittleFamily?.group, "Sweet Little Family")
//        addGroupasIfValid(data.texture?.group, "Texture")
//        addGroupasIfValid(data.theWhole?.group, "The Whole")
//        addGroupasIfValid(data.thinkPink?.group, "Think Pink")
//        addGroupasIfValid(data.trickOrTreat?.group, "Trick or Treat")
//        addGroupasIfValid(data.tropical?.group, "Tropical")
//        addGroupasIfValid(data.tropicalVibes?.group, "Tropical Vibes")
//        addGroupasIfValid(data.unicornAreReal?.group, "Unicorns are Real")
//        addGroupasIfValid(data.uicon?.group, "Uicon")
//        addGroupasIfValid(data.valentineDay?.group, "Valentine's Day")
//        addGroupasIfValid(data.vibranteCarnaval?.group, "Vibrant Carnaval")
//        addGroupasIfValid(data.vintagePaper?.group, "Vintage Paper")
//        addGroupasIfValid(data.vintagePaperTexture?.group, "Vintage Paper Texture")
//        addGroupasIfValid(data.waterFiltration?.group, "Water Filtration")
//        addGroupasIfValid(data.watercolor?.group, "Watercolor")
//        addGroupasIfValid(data.welcomeAutumn?.group, "Welcome Autumn")
//        addGroupasIfValid(data.welcomeBackToSchool?.group, "Welcome Back to School")
//        addGroupasIfValid(data.winterWonderland?.group, "Winter Wonderland")
//        addGroupasIfValid(data.woldlife?.group, "Wildlife")
//        addGroupasIfValid(data.worldwide?.group, "Worldwide")
//        addGroupasIfValid(data.wrapItUp?.group, "Wrap it up")
//        addGroupasIfValid(data.xOXO?.group, "XOXO")
//
//        groupsList.clear()
//        groupsList.addAll(items)
//
//    }
}