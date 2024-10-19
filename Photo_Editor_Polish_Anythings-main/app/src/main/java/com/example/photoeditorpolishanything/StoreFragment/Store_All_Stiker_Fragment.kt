package com.example.photoeditorpolishanything.StoreFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoeditorpolishanything.Adapter.StickerAdapter
import com.example.photoeditorpolishanything.Api.Dataas
import com.example.photoeditorpolishanything.Api.Groupas
import com.example.photoeditorpolishanything.Api.OkHttpHelpers
import com.example.photoeditorpolishanything.databinding.FragmentAllStoreBinding
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class Store_All_Stiker_Fragment : Fragment() {

    private lateinit var binding: FragmentAllStoreBinding
    lateinit var adapter: StickerAdapter

    private val groupsList = mutableListOf<Groupas>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentAllStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView()
    {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = StickerAdapter(requireActivity(), groupsList)
        binding.recyclerView.adapter = adapter

        val url = "https://s3.eu-north-1.amazonaws.com/photoeditorbeautycamera.com/photoeditor/sticker.json"

        OkHttpHelpers.fetchSticker(url) { stickerApi ->
            if (stickerApi != null) {
                Log.e("StoreFragment", "Fetched data: ${stickerApi.data}")

                stickerApi.data?.let {
                    populateGroupsList(it)
                    requireActivity().runOnUiThread {
                        adapter.updateData(groupsList)
                    }
                } ?: Log.e("StoreFragment", "Data is null")
            } else {
                Log.e("StoreFragment", "Failed to fetch data")
            }
        }
    }

    private fun addItemsToList(vararg items: Groupas?)
    {
        items.forEach { item ->
            item?.let { groupsList.add(it) }
        }
    }

    private fun populateGroupsList(data: Dataas)
    {
        val items = mutableListOf<Groupas>()

        // Use Kotlin reflection to iterate over the properties of the Dataas class
        data::class.memberProperties.forEach { property ->
            val prop = property as? KProperty1<Dataas, *>
            val value = prop?.get(data)

            // Check if the value is an instance of a class containing a Groupas
            if (value != null) {
                // Use reflection to find the Groupas property within the nested class
                val groupProperty = value::class.memberProperties
                    .firstOrNull { it.returnType.classifier == Groupas::class }
                        as? KProperty1<Any, Groupas>

                val nestedGroup = groupProperty?.get(value)

                if (nestedGroup != null) {
                    val categoryName = property.name.replace("_", " ").capitalizeWords()

                    nestedGroup.let {
                        if (it.subImageUrl != null || it.mainImageUrl != null) {
                            items.add(
                                Groupas(
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

        groupsList.clear()
        groupsList.addAll(items)
    }

    // Extension function to capitalize words
    private fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }
}