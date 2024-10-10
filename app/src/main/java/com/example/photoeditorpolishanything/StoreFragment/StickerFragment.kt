package com.example.photoeditorpolishanything.StoreFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoeditorpolishanything.Adapter.StickerAdapter
import com.example.photoeditorpolishanything.Api.Dataas
import com.example.photoeditorpolishanything.Api.Groupas
import com.example.photoeditorpolishanything.Api.OkHttpHelpers
import com.example.photoeditorpolishanything.databinding.FragmentStickerBinding
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class StickerFragment : Fragment() {

    private lateinit var binding: FragmentStickerBinding
    private lateinit var adapter: StickerAdapter

    private val groupsList = mutableListOf<Groupas>()
    private val filteredGroupsList = mutableListOf<Groupas>()

    private val baseUrl = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/sticker/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStickerBinding.inflate(inflater, container, false)
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
        adapter = StickerAdapter(requireActivity(), filteredGroupsList)
        binding.recyclerView.adapter = adapter

        val url =
            "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/stickers.json"

        requireActivity().runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
        }

        OkHttpHelpers.fetchSticker(url) { stickerApi ->
            requireActivity().runOnUiThread {
                binding.progressBar.visibility = View.GONE

                if (stickerApi != null) {
                    Log.e("StoreFragment", "Fetched data: ${stickerApi.data}")

                    stickerApi.data?.let {
                        populateGroupsList(it)
                        requireActivity().runOnUiThread {
                            adapter.updateData(filteredGroupsList)
                        }
                    } ?: Log.e("StoreFragment", "Data is null")
                } else {
                    Log.e("StoreFragment", "Failed to fetch data")
                }
            }
        }
    }

    private fun setupSearchView()
    {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                // Do nothing here
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean
            {
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

    private fun populateGroupsList(data: Dataas) {
        val items = mutableListOf<Groupas>()

        data::class.memberProperties.forEach { property ->
            val prop = property as? KProperty1<Dataas, *>
            val value = prop?.get(data)

            if (value != null) {
                val groupProperty = value::class.memberProperties
                    .firstOrNull { it.returnType.classifier == Groupas::class } as? KProperty1<Any, Groupas>

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
        filteredGroupsList.clear()
        filteredGroupsList.addAll(items)
    }

    // Extension function to capitalize words
    private fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }
}
