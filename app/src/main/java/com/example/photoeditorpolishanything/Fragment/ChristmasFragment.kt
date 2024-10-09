package com.example.photoeditorpolishanything.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoeditorpolishanything.Adapter.CollageTemplateAdapter
import com.example.photoeditorpolishanything.Api.Group
import com.example.photoeditorpolishanything.Api.OkHttpHelper
import com.example.photoeditorpolishanything.TemplatesActivity
import com.example.photoeditorpolishanything.databinding.FragmentChristmasBinding

class ChristmasFragment : Fragment() {

    private lateinit var binding: FragmentChristmasBinding
    lateinit var adapter: CollageTemplateAdapter
//    lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChristmasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = GridLayoutManager(TemplatesActivity.contexts, 2)

        val url = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/templates.json"


//        //        testing
//        val url = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/testing.json"

        requireActivity().runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
        }

        OkHttpHelper.fetchTemplates(url) { categorysApi ->
            requireActivity().runOnUiThread {
                binding.progressBar.visibility = View.GONE
                categorysApi?.let {
                    val groupsList = mutableListOf<Group>()

                    it.templates.forEach { template ->
                        template?.christmas?.let { christmas ->
                            christmas.group1?.let { groupsList.add(it) }
                            christmas.group2?.let { groupsList.add(it) }
                            christmas.group3?.let { groupsList.add(it) }
                            christmas.group4?.let { groupsList.add(it) }
                            christmas.group5?.let { groupsList.add(it) }
                            christmas.group6?.let { groupsList.add(it) }
                            christmas.group7?.let { groupsList.add(it) }
                            christmas.group8?.let { groupsList.add(it) }
                            christmas.group9?.let { groupsList.add(it) }
                            christmas.group10?.let { groupsList.add(it) }
                            christmas.group11?.let { groupsList.add(it) }
                            christmas.group12?.let { groupsList.add(it) }
                            christmas.group13?.let { groupsList.add(it) }
                            christmas.group14?.let { groupsList.add(it) }
                            christmas.group15?.let { groupsList.add(it) }
                            christmas.group16?.let { groupsList.add(it) }
                            christmas.group17?.let { groupsList.add(it) }
                            christmas.group18?.let { groupsList.add(it) }
                            christmas.group19?.let { groupsList.add(it) }
//                        christmas.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the childhood category


                            requireActivity().runOnUiThread {
                                adapter = CollageTemplateAdapter(requireContext(), groupsList) /*{ selectedGroup ->
                        showBottomSheetForImageEditurl()
                    }*/
                                binding.recyclerView.adapter = adapter
                            }
                        }
                    }
                }
            }
        }
    }
}