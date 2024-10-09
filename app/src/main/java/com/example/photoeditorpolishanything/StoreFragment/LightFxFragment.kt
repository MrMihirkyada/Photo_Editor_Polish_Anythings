package com.example.photoeditorpolishanything.StoreFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoeditorpolishanything.Adapter.LightFx_Adapter
import com.example.photoeditorpolishanything.Api.DataItems
import com.example.photoeditorpolishanything.Api.Groupes
import com.example.photoeditorpolishanything.Api.OkHttpHelperlightfx
import com.example.photoeditorpolishanything.databinding.FragmentLightfxBinding
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class LightFxFragment : Fragment() {

    private lateinit var binding: FragmentLightfxBinding
    private lateinit var adapter: LightFx_Adapter

    private val groupsList = mutableListOf<Groupes>()
    private val filteredGroupsList = mutableListOf<Groupes>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLightfxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupSearchView()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = LightFx_Adapter(filteredGroupsList)
        binding.recyclerView.adapter = adapter

        val url = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/lightfxs.json"

        requireActivity().runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
        }

        OkHttpHelperlightfx.fetchlightfx(url) { lightFxApi ->

            requireActivity().runOnUiThread {
                binding.progressBar.visibility = View.GONE

                if (lightFxApi != null) {
                    Log.e("LightFx", "Fetched data: ${lightFxApi.data}")

                    lightFxApi.data?.let {
                        populateGroupsList(it)
                        requireActivity().runOnUiThread {
                            adapter.updateData(filteredGroupsList)
                        }
                    } ?: Log.e("LightFx", "Data is null")
                } else {
                    Log.e("LightFx", "Failed to fetch data")
                }
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Do nothing here
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterGroups(newText ?: "")
                return true
            }
        })
    }

    private fun filterGroups(query: String) {
        val lowercaseQuery = query.toLowerCase()
        val filteredItems = groupsList.filter { it.textCategory!!.toLowerCase().contains(lowercaseQuery) }

        filteredGroupsList.clear()
        filteredGroupsList.addAll(filteredItems)
        adapter.notifyDataSetChanged()
    }

    private fun populateGroupsList(data: DataItems) {
        val items = mutableListOf<Groupes>()

        // Use Kotlin reflection to iterate over the properties of the DataItems class
        data::class.memberProperties.forEach { property ->
            // Cast the property to KProperty1<DataItems, *>
            val prop = property as? KProperty1<DataItems, *>
            val value = prop?.get(data)

            // Check if the value is not null and is of type Groupes or contains Groupes
            if (value != null) {
                // Check if the value is a Groupes instance
                if (value is Groupes) {
                    val categoryName = property.name.replace("_", " ").capitalizeWords()

                    value.let {
                        if (it.subImageUrl != null || it.mainImageUrl != null) {
                            items.add(
                                Groupes(
                                    subImageUrl = it.subImageUrl,
                                    mainImageUrl = it.mainImageUrl,
                                    textCategory = categoryName
                                )
                            )
                        }
                    }
                } else {
                    // If value is not a Groupes instance, use reflection to check nested properties
                    val groupProperty = value::class.memberProperties
                        .firstOrNull { it.returnType.classifier == Groupes::class }
                            as? KProperty1<Any, Groupes>

                    val nestedGroup = groupProperty?.get(value)

                    if (nestedGroup != null) {
                        val categoryName = property.name.replace("_", " ").capitalizeWords()

                        nestedGroup.let {
                            if (it.subImageUrl != null || it.mainImageUrl != null) {
                                items.add(
                                    Groupes(
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
        filteredGroupsList.clear()
        filteredGroupsList.addAll(items)
    }

    // Extension function to capitalize words
    private fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

}
