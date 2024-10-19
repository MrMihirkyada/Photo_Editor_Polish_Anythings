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
import com.example.photoeditorpolishanything.databinding.FragmentGraduationBinding

class GraduationFragment : Fragment() {

    private lateinit var binding: FragmentGraduationBinding
    lateinit var adapter: CollageTemplateAdapter
//    lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGraduationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = GridLayoutManager(TemplatesActivity.contexts, 2)

        val url =
            "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/templates.json"

        requireActivity().runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
        }

        OkHttpHelper.fetchTemplates(url) { categorysApi ->

            requireActivity().runOnUiThread {
                binding.progressBar.visibility = View.GONE
                categorysApi?.let {
                    val groupsList = mutableListOf<Group>()

                    it.templates.forEach { template ->
                        template?.graduation?.let { graduation ->
                            graduation.group1?.let { groupsList.add(it) }
                            graduation.group2?.let { groupsList.add(it) }
                            graduation.group3?.let { groupsList.add(it) }
                            graduation.group4?.let { groupsList.add(it) }
                            graduation.group5?.let { groupsList.add(it) }
                            graduation.group6?.let { groupsList.add(it) }
                            graduation.group7?.let { groupsList.add(it) }
                            graduation.group8?.let { groupsList.add(it) }
                            graduation.group9?.let { groupsList.add(it) }
                            graduation.group10?.let { groupsList.add(it) }
                            graduation.group11?.let { groupsList.add(it) }
                            graduation.group12?.let { groupsList.add(it) }
                            graduation.group13?.let { groupsList.add(it) }
                            graduation.group14?.let { groupsList.add(it) }
                            graduation.group15?.let { groupsList.add(it) }
                            graduation.group16?.let { groupsList.add(it) }
                            graduation.group17?.let { groupsList.add(it) }
                            graduation.group18?.let { groupsList.add(it) }

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