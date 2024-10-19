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
import com.example.photoeditorpolishanything.databinding.FragmentSchoollifeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class SchoolLifeFragment : Fragment() {

    private lateinit var binding: FragmentSchoollifeBinding
    lateinit var adapter: CollageTemplateAdapter
//    lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSchoollifeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
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

                        template?.schoollife?.let { schoollife ->
                            schoollife.group1?.let { groupsList.add(it) }
                            schoollife.group2?.let { groupsList.add(it) }
                            schoollife.group3?.let { groupsList.add(it) }
                            schoollife.group4?.let { groupsList.add(it) }
                            schoollife.group5?.let { groupsList.add(it) }
                            schoollife.group6?.let { groupsList.add(it) }
                            schoollife.group7?.let { groupsList.add(it) }
                            schoollife.group8?.let { groupsList.add(it) }
                            schoollife.group9?.let { groupsList.add(it) }
                            schoollife.group10?.let { groupsList.add(it) }
                            schoollife.group11?.let { groupsList.add(it) }
                            schoollife.group12?.let { groupsList.add(it) }
                            schoollife.group13?.let { groupsList.add(it) }
                            schoollife.group14?.let { groupsList.add(it) }
                            schoollife.group15?.let { groupsList.add(it) }
                            schoollife.group16?.let { groupsList.add(it) }
                            schoollife.group17?.let { groupsList.add(it) }
                            schoollife.group18?.let { groupsList.add(it) }
                            schoollife.group19?.let { groupsList.add(it) }
//                        schoollife.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the school category
                        }

                    }

                    requireActivity().runOnUiThread {
                        adapter = CollageTemplateAdapter(
                            requireContext(),
                            groupsList
                        ) /*{ selectedGroup ->
                        showBottomSheetForImageEditurl()
                    }*/
                        binding.recyclerView.adapter = adapter
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
}