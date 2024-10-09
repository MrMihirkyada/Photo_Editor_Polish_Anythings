package com.example.photoeditorpolishanything.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoeditorpolishanything.Adapter.BackgroundAdapter
import com.example.photoeditorpolishanything.Api.DataItem
import com.example.photoeditorpolishanything.Api.Groups
import com.example.photoeditorpolishanything.Api.OkHttpHelperBackground
import com.example.photoeditorpolishanything.databinding.FragmentBackgroundBinding
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class BackgroundFragment : Fragment() {

    private lateinit var binding: FragmentBackgroundBinding
    private lateinit var adapter: BackgroundAdapter

    private val groupsList = mutableListOf<Groups>()
    private val filteredGroupsList = mutableListOf<Groups>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBackgroundBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initView()
        setupSearchView()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BackgroundAdapter(requireActivity(), filteredGroupsList)
        binding.recyclerView.adapter = adapter

        val url =
            "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/backgrounds.json"

        requireActivity().runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
        }

        OkHttpHelperBackground.fetchBackground(url) { backgroundApi ->
            requireActivity().runOnUiThread {
                binding.progressBar.visibility = View.GONE

                if (backgroundApi != null) {
                    Log.e("BackgroundFragment", "Fetched data: ${backgroundApi.data}")

                    backgroundApi.data?.let {
                        populateGroupsList(it)
                        requireActivity().runOnUiThread {
                            adapter.updateData(filteredGroupsList)
                        }
                    } ?: Log.e("BackgroundFragment", "Data is null")
                } else {
                    Log.e("BackgroundFragment", "Failed to fetch data")
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

    private fun populateGroupsList(data: DataItem) {
        val items = mutableListOf<Groups>()

        // Use Kotlin reflection to iterate over the properties of the DataItem class
        data::class.memberProperties.forEach { property ->
            // Cast the property to KProperty1<DataItem, *>
            val prop = property as? KProperty1<DataItem, *>
            val value = prop?.get(data)

            // Check if the value is not null and is of type Groups or contains Groups
            if (value != null) {
                // Check if the value is a Groups instance
                if (value is Groups) {
                    val categoryName = property.name.replace("_", " ").capitalizeWords()

                    value.let {
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
                } else {
                    // If value is not a Groups instance, use reflection to check nested properties
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
        filteredGroupsList.clear()
        filteredGroupsList.addAll(items)
    }

    // Extension function to capitalize words
    private fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

}
