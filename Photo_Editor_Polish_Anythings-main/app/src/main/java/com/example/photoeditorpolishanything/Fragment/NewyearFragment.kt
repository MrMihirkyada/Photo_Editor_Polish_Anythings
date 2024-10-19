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
import com.example.photoeditorpolishanything.databinding.FragmentNewyearBinding

class NewyearFragment : Fragment() {

    private lateinit var binding: FragmentNewyearBinding
    lateinit var adapter: CollageTemplateAdapter
//    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentNewyearBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView()
    {
        binding.recyclerView.layoutManager = GridLayoutManager(TemplatesActivity.contexts, 2)

        val url = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/templates.json"

        requireActivity().runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
        }

        OkHttpHelper.fetchTemplates(url) { categorysApi ->
            requireActivity().runOnUiThread {
                binding.progressBar.visibility = View.GONE
                categorysApi?.let {
                    val groupsList = mutableListOf<Group>()

                    it.templates.forEach { template ->
                        template?.newyear?.let { newyear ->
                            newyear.group1?.let { groupsList.add(it) }
                            newyear.group2?.let { groupsList.add(it) }
                            newyear.group3?.let { groupsList.add(it) }
                            newyear.group4?.let { groupsList.add(it) }
                            newyear.group5?.let { groupsList.add(it) }
                            newyear.group6?.let { groupsList.add(it) }
                            newyear.group7?.let { groupsList.add(it) }
                            newyear.group8?.let { groupsList.add(it) }
                            newyear.group9?.let { groupsList.add(it) }
                            newyear.group10?.let { groupsList.add(it) }
                            newyear.group11?.let { groupsList.add(it) }
                            newyear.group12?.let { groupsList.add(it) }
                            newyear.group13?.let { groupsList.add(it) }
                            newyear.group14?.let { groupsList.add(it) }
                            newyear.group15?.let { groupsList.add(it) }
                            newyear.group16?.let { groupsList.add(it) }
                            newyear.group17?.let { groupsList.add(it) }
                            newyear.group18?.let { groupsList.add(it) }
                            newyear.group19?.let { groupsList.add(it) }
                            // Repeat for all groups in the love category
                        }
                    }

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