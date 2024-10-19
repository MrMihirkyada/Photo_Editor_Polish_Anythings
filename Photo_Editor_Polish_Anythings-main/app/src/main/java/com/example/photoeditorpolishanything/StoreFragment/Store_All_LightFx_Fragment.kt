package com.example.photoeditorpolishanything.StoreFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoeditorpolishanything.Adapter.LightFx_Adapter
import com.example.photoeditorpolishanything.Api.DataItem
import com.example.photoeditorpolishanything.Api.DataItems
import com.example.photoeditorpolishanything.Api.Groupes
import com.example.photoeditorpolishanything.Api.OkHttpHelperlightfx
import com.example.photoeditorpolishanything.databinding.FragmentAllLightfxStoreBinding

class Store_All_LightFx_Fragment: Fragment()
{
    private lateinit var binding: FragmentAllLightfxStoreBinding
    lateinit var adapter: LightFx_Adapter

    private val groupsList = mutableListOf<Groupes>()

    var datas : DataItem? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentAllLightfxStoreBinding.inflate(inflater, container, false)
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
        adapter = LightFx_Adapter(groupsList)
        binding.recyclerView.adapter = adapter

        val url = "https://s3.eu-north-1.amazonaws.com/photoeditorbeautycamera.com/photoeditor/new.json"

        OkHttpHelperlightfx.fetchlightfx(url) { lightFxApi ->
            if (lightFxApi != null) {
                Log.e("LightFx", "Fetched data: ${lightFxApi.data}")

                lightFxApi.data?.let {
                    populateGroupsList(it)
                    requireActivity().runOnUiThread {
                        adapter.updateData(groupsList)
                    }
                } ?: Log.e("LightFx", "Data is null")
            } else {
                Log.e("LightFx", "Failed to fetch data")
            }
        }


    }

//    private fun populateGroupsList(data: List<DataItem?>) {
//        val items = mutableListOf<Groupes>()
//
//        data.forEach { dataItem ->
//            dataItem?.let { item ->
//                // Use reflection to get all properties of DataItem
//                item::class.memberProperties.forEach { property ->
//                    try {
//                        // Get the value of the property
//                        val value = (property as? KProperty1<DataItem, *>)?.get(item)
//
//                        // Check if the value is of type Groupes
//                        if (value is Groupes) {
//                            items.add(value.apply {
//                                textCategory = property.name.replace("_", " ").capitalizeWords()
//                            })
//                        }
//                    } catch (e: Exception) {
//                        Log.e("PopulateGroups", "Error accessing property ${property.name}", e)
//                    }
//                }
//            }
//        }
//
//        groupsList.clear()
//        groupsList.addAll(items)
//        requireActivity().runOnUiThread {
//            adapter.updateData(groupsList)
//        }
//    }

    private fun populateGroupsList(dataItems: DataItems) {
        val items = mutableListOf<Groupes>()

        // Function to add Groups to the list if they contain valid data
        fun addGroupasIfValid(group: Groupes?, category: String) {
            group?.let {
                if (it.subImageUrl != null || it.mainImageUrl != null) {
                    items.add(
                        Groupes(
                            subImageUrl = it.subImageUrl,
                            mainImageUrl = it.mainImageUrl,
                            textCategory = category
                        )
                    )
                }
            }
        }

//        dataItems?.forEach { dataItem ->
//            dataItem?.let { item ->
//                addGroupasIfValid(item.autumn?.group, "Autumn")
//                addGroupasIfValid(item.bokeh?.group, "Bokeh")
//                addGroupasIfValid(item.brokenGlass?.group, "Broken Glass")
//                addGroupasIfValid(item.bubble?.group, "Bubble")
//                addGroupasIfValid(item.confetti?.group, "Confetti")
//                addGroupasIfValid(item.diamond?.group, "Diamond")
//                addGroupasIfValid(item.dreamyLight?.group, "Dreamy Light")
//                addGroupasIfValid(item.dustAndScratch?.group, "Dust and Scratch")
//                addGroupasIfValid(item.dustAndSunlight?.group, "Dust and Sunlight")
//                addGroupasIfValid(item.explosion?.group, "Explosion")
//                addGroupasIfValid(item.faceShadow?.group, "Face Shadow")
//                addGroupasIfValid(item.fantasy?.group, "Fantasy")
//                addGroupasIfValid(item.filmLeak?.group, "Film Leak")
//                addGroupasIfValid(item.fire?.group, "Fire")
//                addGroupasIfValid(item.fireworks?.group, "Fireworks")
//                addGroupasIfValid(item.flurries?.group, "Flurries")
//                addGroupasIfValid(item.fogOverlay?.group, "Fog Overlay")
//                addGroupasIfValid(item.galaxy?.group, "Galaxy")
//                addGroupasIfValid(item.goldenHour?.group, "Golden Hour")
//                addGroupasIfValid(item.lightStroke?.group, "Light Stroke")
//                addGroupasIfValid(item.lightning?.group, "Lightning")
//                addGroupasIfValid(item.love?.group, "Love")
//                addGroupasIfValid(item.moon?.group, "Moon")
//                addGroupasIfValid(item.neon?.group, "Neon")
//                addGroupasIfValid(item.petals?.group, "Petals")
//                addGroupasIfValid(item.prism?.group, "Prism")
//                addGroupasIfValid(item.rain?.group, "Rain")
//                addGroupasIfValid(item.rainbow?.group, "Rainbow")
//                addGroupasIfValid(item.retro?.group, "Retro")
//                addGroupasIfValid(item.ripple?.group, "Ripple")
//                addGroupasIfValid(item.rose?.group, "Rose")
//                addGroupasIfValid(item.scarf?.group, "Scarf")
//                addGroupasIfValid(item.shadow?.group, "Shadow")
//                addGroupasIfValid(item.shutter?.group, "Shutter")
//                addGroupasIfValid(item.smoke?.group, "Smoke")
//                addGroupasIfValid(item.snow?.group, "Snow")
//                addGroupasIfValid(item.star?.group, "Star")
//                addGroupasIfValid(item.sunlight?.group, "Sunlight")
//                addGroupasIfValid(item.texture?.group, "Texture")
//                addGroupasIfValid(item.water?.group, "Water")
//            }
//        }

        groupsList.clear()
        groupsList.addAll(items)
        requireActivity().runOnUiThread {
            adapter.updateData(groupsList)
        }
    }

    // Extension function to capitalize words
    private fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }
}