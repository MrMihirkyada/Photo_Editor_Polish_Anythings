package com.example.photoeditorpolishanything.Fragment

import ImageAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photoeditorpolishanything.Adapter.CollageTemplateAdapter
import com.example.photoeditorpolishanything.Api.Group
import com.example.photoeditorpolishanything.Api.OkHttpHelper
import com.example.photoeditorpolishanything.EditActivity
import com.example.photoeditorpolishanything.R
import com.example.photoeditorpolishanything.TemplatesActivity
import com.example.photoeditorpolishanything.databinding.FragmentIgStoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class zIG_StoryFragment : Fragment(){

    private lateinit var binding: FragmentIgStoryBinding
    lateinit var adapter: CollageTemplateAdapter
//    lateinit var recyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentIgStoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = GridLayoutManager(TemplatesActivity.contexts,2)

        val url = "https://s3.eu-north-1.amazonaws.com/photoeditorbeautycamera.com/collagemaker/templates.json"

        requireActivity().runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
        }

        OkHttpHelper.fetchTemplates(url) { categoryApi ->
            requireActivity().runOnUiThread {
                binding.progressBar.visibility = View.GONE
                categoryApi?.let {

                    val groupsList = mutableListOf<Group>()

                    it.templates.forEach { template ->

//                    template?.lgStory?.let { lgStory ->
//                        lgStory.group1?.let { groupsList.add(it) }
//                        lgStory.group2?.let { groupsList.add(it) }
//                        lgStory.group3?.let { groupsList.add(it) }
//                        lgStory.group4?.let { groupsList.add(it) }
//                        lgStory.group5?.let { groupsList.add(it) }
//                        lgStory.group6?.let { groupsList.add(it) }
//                        lgStory.group7?.let { groupsList.add(it) }
//                        lgStory.group8?.let { groupsList.add(it) }
//                        lgStory.group9?.let { groupsList.add(it) }
//                        lgStory.group10?.let { groupsList.add(it) }
//                        lgStory.group11?.let { groupsList.add(it) }
//                        lgStory.group12?.let { groupsList.add(it) }
//                        lgStory.group13?.let { groupsList.add(it) }
//                        lgStory.group14?.let { groupsList.add(it) }
//                        lgStory.group15?.let { groupsList.add(it) }
//                        lgStory.group16?.let { groupsList.add(it) }
//                        lgStory.group17?.let { groupsList.add(it) }
//                        lgStory.group18?.let { groupsList.add(it) }
//                        lgStory.group19?.let { groupsList.add(it) }
//                        lgStory.group20?.let { groupsList.add(it) }
//                        // Repeat for all groups in the lgStory category
//                    }
                    }

//                requireActivity().runOnUiThread {
//                    adapter = CollageTemplateAdapter(
//                        requireContext(),
//                        groupsList
//                    ) /*{ selectedGroup ->
//                        showBottomSheetForImageEditurl()
//                    }*/
//                    binding.recyclerView.adapter = adapter
//                }
                }
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    fun showBottomSheetForImageEditurl() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
        val view = layoutInflater.inflate(R.layout.camera_dialog, null)
        view.findViewById<ImageView>(R.id.imgCross)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Set layout manager with 3 columns
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        // Fetch images using AsyncTask and populate the RecyclerView
        TemplatesActivity.FetchImagesTasks(requireContext()) { imageList ->
            recyclerView.adapter = ImageAdapter(imageList) { selectedImageUri ->
                bottomSheetDialog.dismiss()
                val intent = Intent(requireContext(), EditActivity::class.java)
                intent.putExtra("selected_image_uri", selectedImageUri.toString())
                startActivity(intent)
            }

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.setOnShowListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bottomSheetDialog.window?.navigationBarColor =
                        ContextCompat.getColor(requireContext(), R.color.black)
                }
            }

            bottomSheetDialog.show()
        }.execute()
    }
}