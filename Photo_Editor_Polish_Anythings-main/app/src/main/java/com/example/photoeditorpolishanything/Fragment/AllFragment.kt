package com.example.photoeditorpolishanything.Fragment

import ImageAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.example.photoeditorpolishanything.Adapter.AlbumAdapter
import com.example.photoeditorpolishanything.Adapter.AlbumImagesAdapter
import com.example.photoeditorpolishanything.Adapter.CollageTemplateAdapter
import com.example.photoeditorpolishanything.Album.AlbumFetcher
import com.example.photoeditorpolishanything.Api.Group
import com.example.photoeditorpolishanything.Api.OkHttpHelper
import com.example.photoeditorpolishanything.EditActivity
import com.example.photoeditorpolishanything.ImagePreloadModelProvider
import com.example.photoeditorpolishanything.R
import com.example.photoeditorpolishanything.TemplatesActivity
import com.example.photoeditorpolishanything.TemplatesActivity.Companion.contexts
import com.example.photoeditorpolishanything.databinding.FragmentAllBinding
import com.example.photoeditorpolishanything.fetchImagesFromAlbum
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllFragment : Fragment() {

    private lateinit var binding: FragmentAllBinding
    lateinit var adapter: CollageTemplateAdapter
//    lateinit var recyclerView: RecyclerView

    var imageList: List<Uri> = mutableListOf()
    lateinit var AlbumImagesAdapter: AlbumImagesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AlbumImagesAdapter = AlbumImagesAdapter(mutableListOf())

        initView()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = GridLayoutManager(contexts, 2)

        val url = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/templates.json"

//      testing
//      val url = "https://s3.ap-south-1.amazonaws.com/photoeditorbeautycamera.app/photoeditor/testing.json"

        requireActivity().runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
        }

        OkHttpHelper.fetchTemplates(url) { templateApi ->
//            binding.progressBar.visibility = View.GONE

            requireActivity().runOnUiThread {
                binding.progressBar.visibility = View.GONE
                templateApi?.let {
                    val groupsList = mutableListOf<Group>()

                    it.templates.forEach {  template ->

                        template?.birthday?.let { birthday ->
                            birthday.group1?.let { groupsList.add(it) }
                            birthday.group2?.let { groupsList.add(it) }
                            birthday.group3?.let { groupsList.add(it) }
                            birthday.group4?.let { groupsList.add(it) }
                            birthday.group5?.let { groupsList.add(it) }
                            birthday.group6?.let { groupsList.add(it) }
                            birthday.group7?.let { groupsList.add(it) }
                            birthday.group8?.let { groupsList.add(it) }
                            birthday.group9?.let { groupsList.add(it) }
                            birthday.group10?.let { groupsList.add(it) }
                            birthday.group11?.let { groupsList.add(it) }
                            birthday.group12?.let { groupsList.add(it) }
                            birthday.group13?.let { groupsList.add(it) }
                            birthday.group14?.let { groupsList.add(it) }
                            birthday.group15?.let { groupsList.add(it) }
                            birthday.group16?.let { groupsList.add(it) }
                            birthday.group17?.let { groupsList.add(it) }
                            birthday.group18?.let { groupsList.add(it) }
                            birthday.group19?.let { groupsList.add(it) }
                            birthday.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the birthday category
                        }

                        template?.calendar?.let { calendar ->
                            calendar.group1?.let { groupsList.add(it) }
                            calendar.group2?.let { groupsList.add(it) }
                            calendar.group3?.let { groupsList.add(it) }
                            calendar.group4?.let { groupsList.add(it) }
                            calendar.group5?.let { groupsList.add(it) }
                            calendar.group6?.let { groupsList.add(it) }
                            calendar.group7?.let { groupsList.add(it) }
                            calendar.group8?.let { groupsList.add(it) }
                            calendar.group9?.let { groupsList.add(it) }
                            calendar.group10?.let { groupsList.add(it) }
                            calendar.group11?.let { groupsList.add(it) }
                            calendar.group12?.let { groupsList.add(it) }
                            calendar.group13?.let { groupsList.add(it) }
                            calendar.group14?.let { groupsList.add(it) }
                            calendar.group15?.let { groupsList.add(it) }
                            calendar.group16?.let { groupsList.add(it) }
                            calendar.group17?.let { groupsList.add(it) }
                            calendar.group18?.let { groupsList.add(it) }
                            calendar.group19?.let { groupsList.add(it) }
                            // Repeat for all groups in the childhood category
                        }

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
                        }

                        template?.family?.let { family ->
                            family.group1?.let { groupsList.add(it) }
                            family.group2?.let { groupsList.add(it) }
                            family.group3?.let { groupsList.add(it) }
                            family.group4?.let { groupsList.add(it) }
                            family.group5?.let { groupsList.add(it) }
                            family.group6?.let { groupsList.add(it) }
                            family.group7?.let { groupsList.add(it) }
                            family.group8?.let { groupsList.add(it) }
                            family.group9?.let { groupsList.add(it) }
                            family.group10?.let { groupsList.add(it) }
                            family.group11?.let { groupsList.add(it) }
                            family.group12?.let { groupsList.add(it) }
                            family.group13?.let { groupsList.add(it) }
                            family.group14?.let { groupsList.add(it) }
                            family.group15?.let { groupsList.add(it) }
                            family.group16?.let { groupsList.add(it) }
                            family.group17?.let { groupsList.add(it) }
                            family.group18?.let { groupsList.add(it) }
                            family.group19?.let { groupsList.add(it) }
                            family.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the childhood category
                        }

                        template?.frame?.let { frame ->
                            frame.group1?.let { groupsList.add(it) }
                            frame.group2?.let { groupsList.add(it) }
                            frame.group3?.let { groupsList.add(it) }
                            frame.group4?.let { groupsList.add(it) }
                            frame.group5?.let { groupsList.add(it) }
                            frame.group6?.let { groupsList.add(it) }
                            frame.group7?.let { groupsList.add(it) }
                            frame.group8?.let { groupsList.add(it) }
                            frame.group9?.let { groupsList.add(it) }
                            frame.group10?.let { groupsList.add(it) }
                            frame.group11?.let { groupsList.add(it) }
                            frame.group12?.let { groupsList.add(it) }
                            frame.group13?.let { groupsList.add(it) }
                            frame.group14?.let { groupsList.add(it) }
                            frame.group15?.let { groupsList.add(it) }
                            frame.group16?.let { groupsList.add(it) }
                            frame.group17?.let { groupsList.add(it) }
                            frame.group18?.let { groupsList.add(it) }
                            frame.group19?.let { groupsList.add(it) }
                            frame.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the frame category
                        }

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
//                        graduation.group19?.let { groupsList.add(it) }
//                        graduation.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the frame category
                        }

                        template?.love?.let { love ->
                            love.group1?.let { groupsList.add(it) }
                            love.group2?.let { groupsList.add(it) }
                            love.group3?.let { groupsList.add(it) }
                            love.group4?.let { groupsList.add(it) }
                            love.group5?.let { groupsList.add(it) }
                            love.group6?.let { groupsList.add(it) }
                            love.group7?.let { groupsList.add(it) }
                            love.group8?.let { groupsList.add(it) }
                            love.group9?.let { groupsList.add(it) }
                            love.group10?.let { groupsList.add(it) }
                            love.group11?.let { groupsList.add(it) }
                            love.group12?.let { groupsList.add(it) }
                            love.group13?.let { groupsList.add(it) }
                            love.group14?.let { groupsList.add(it) }
                            love.group15?.let { groupsList.add(it) }
                            love.group16?.let { groupsList.add(it) }
                            love.group17?.let { groupsList.add(it) }
                            love.group18?.let { groupsList.add(it) }
                            love.group19?.let { groupsList.add(it) }
                            love.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the love category
                        }

                        template?.motherday?.let { motherday ->
                            motherday.group1?.let { groupsList.add(it) }
                            motherday.group2?.let { groupsList.add(it) }
                            motherday.group3?.let { groupsList.add(it) }
                            motherday.group4?.let { groupsList.add(it) }
                            motherday.group5?.let { groupsList.add(it) }
                            motherday.group6?.let { groupsList.add(it) }
                            motherday.group7?.let { groupsList.add(it) }
                            motherday.group8?.let { groupsList.add(it) }
                            motherday.group9?.let { groupsList.add(it) }
                            motherday.group10?.let { groupsList.add(it) }
                            motherday.group11?.let { groupsList.add(it) }
                            motherday.group12?.let { groupsList.add(it) }
                            motherday.group13?.let { groupsList.add(it) }
                            motherday.group14?.let { groupsList.add(it) }
                            motherday.group15?.let { groupsList.add(it) }
                            motherday.group16?.let { groupsList.add(it) }
                            motherday.group17?.let { groupsList.add(it) }
                            motherday.group18?.let { groupsList.add(it) }
                            motherday.group19?.let { groupsList.add(it) }
                            motherday.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the love category
                        }

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
//                        newyear.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the love category
                        }

                        template?.pride?.let { pride ->
                            pride.group1?.let { groupsList.add(it) }
                            pride.group2?.let { groupsList.add(it) }
                            pride.group3?.let { groupsList.add(it) }
                            pride.group4?.let { groupsList.add(it) }
                            pride.group5?.let { groupsList.add(it) }
                            pride.group6?.let { groupsList.add(it) }
                            pride.group7?.let { groupsList.add(it) }
                            pride.group8?.let { groupsList.add(it) }
                            pride.group9?.let { groupsList.add(it) }
                            pride.group10?.let { groupsList.add(it) }
                            pride.group11?.let { groupsList.add(it) }
                            pride.group12?.let { groupsList.add(it) }
                            pride.group13?.let { groupsList.add(it) }
                            pride.group14?.let { groupsList.add(it) }
                            pride.group15?.let { groupsList.add(it) }
                            pride.group16?.let { groupsList.add(it) }
                            pride.group17?.let { groupsList.add(it) }
                            pride.group18?.let { groupsList.add(it) }
                            pride.group19?.let { groupsList.add(it) }
//                        pride.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the pridemonth category
                        }

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

                        template?.summer?.let { summer ->
                            summer.group1?.let { groupsList.add(it) }
                            summer.group2?.let { groupsList.add(it) }
                            summer.group3?.let { groupsList.add(it) }
                            summer.group4?.let { groupsList.add(it) }
                            summer.group5?.let { groupsList.add(it) }
                            summer.group6?.let { groupsList.add(it) }
                            summer.group7?.let { groupsList.add(it) }
                            summer.group8?.let { groupsList.add(it) }
                            summer.group9?.let { groupsList.add(it) }
                            summer.group10?.let { groupsList.add(it) }
                            summer.group11?.let { groupsList.add(it) }
                            summer.group12?.let { groupsList.add(it) }
                            summer.group13?.let { groupsList.add(it) }
                            summer.group14?.let { groupsList.add(it) }
                            summer.group15?.let { groupsList.add(it) }
                            summer.group16?.let { groupsList.add(it) }
                            summer.group17?.let { groupsList.add(it) }
                            summer.group18?.let { groupsList.add(it) }
                            summer.group19?.let { groupsList.add(it) }
//                        summer.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the summer category
                        }

                        template?.travel?.let { travel ->
                            travel.group1?.let { groupsList.add(it) }
                            travel.group2?.let { groupsList.add(it) }
                            travel.group3?.let { groupsList.add(it) }
                            travel.group4?.let { groupsList.add(it) }
                            travel.group5?.let { groupsList.add(it) }
                            travel.group6?.let { groupsList.add(it) }
                            travel.group7?.let { groupsList.add(it) }
                            travel.group8?.let { groupsList.add(it) }
                            travel.group9?.let { groupsList.add(it) }
                            travel.group10?.let { groupsList.add(it) }
                            travel.group11?.let { groupsList.add(it) }
                            travel.group12?.let { groupsList.add(it) }
                            travel.group13?.let { groupsList.add(it) }
                            travel.group14?.let { groupsList.add(it) }
                            travel.group15?.let { groupsList.add(it) }
                            travel.group16?.let { groupsList.add(it) }
                            travel.group17?.let { groupsList.add(it) }
                            travel.group18?.let { groupsList.add(it) }
                            travel.group19?.let { groupsList.add(it) }
                            travel.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the lgStory category
                        }

                        template?.winter?.let { winter ->
                            winter.group1?.let { groupsList.add(it) }
                            winter.group2?.let { groupsList.add(it) }
                            winter.group3?.let { groupsList.add(it) }
                            winter.group4?.let { groupsList.add(it) }
                            winter.group5?.let { groupsList.add(it) }
                            winter.group6?.let { groupsList.add(it) }
                            winter.group7?.let { groupsList.add(it) }
                            winter.group8?.let { groupsList.add(it) }
                            winter.group9?.let { groupsList.add(it) }
                            winter.group10?.let { groupsList.add(it) }
                            winter.group11?.let { groupsList.add(it) }
                            winter.group12?.let { groupsList.add(it) }
                            winter.group13?.let { groupsList.add(it) }
                            winter.group14?.let { groupsList.add(it) }
                            winter.group15?.let { groupsList.add(it) }
                            winter.group16?.let { groupsList.add(it) }
                            winter.group17?.let { groupsList.add(it) }
                            winter.group18?.let { groupsList.add(it) }
                            winter.group19?.let { groupsList.add(it) }
                            winter.group20?.let { groupsList.add(it) }
                            // Repeat for all groups in the selfie category
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

    @SuppressLint("MissingInflatedId")
    fun showBottomSheetForImageEditurl()
    {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
        val view = layoutInflater.inflate(R.layout.camera_dialog, null)

        view.findViewById<ImageView>(R.id.imgCross)?.setOnClickListener { bottomSheetDialog.dismiss() }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Set layout manager with 3 columns
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)


        val lnrPhotos = view.findViewById<LinearLayout>(R.id.lnrPhotos)
        val lnrAlbums = view.findViewById<LinearLayout>(R.id.lnrAlbums)

        // Ensure Photos is selected by default
        lnrPhotos.setBackgroundResource(R.drawable.toggle_selected)
        lnrAlbums.setBackgroundResource(R.drawable.toggle_unselected)

        lnrPhotos.setOnClickListener {
            lnrPhotos.setBackgroundResource(R.drawable.toggle_selected)
            lnrAlbums.setBackgroundResource(R.drawable.toggle_unselected)

            view.findViewById<LinearLayout>(R.id.lnrselectedCountTextView)?.let { it.visibility = View.VISIBLE }

            view.findViewById<LinearLayout>(R.id.lnrimgDelete)?.let { it.visibility = View.VISIBLE }

            view.findViewById<RecyclerView>(R.id.rcvSelected_Image)?.let { it.visibility = View.VISIBLE }

            view.findViewById<RecyclerView>(R.id.recyclerView)?.let { it.visibility = View.VISIBLE }

            view.findViewById<RecyclerView>(R.id.recyclerViewAlbum)?.let { it.visibility = View.GONE }

            view.findViewById<RecyclerView>(R.id.recyclerViewImages)?.let { it.visibility = View.GONE }
        }


        lnrAlbums.setOnClickListener {
            lnrAlbums.setBackgroundResource(R.drawable.toggle_selected)
            lnrPhotos.setBackgroundResource(R.drawable.toggle_unselected)

            view.findViewById<RecyclerView>(R.id.recyclerViewAlbum)?.let { recyclerViewAlbum ->
                recyclerViewAlbum.setHasFixedSize(true)
                recyclerViewAlbum.setItemViewCacheSize(20)
                recyclerViewAlbum.isNestedScrollingEnabled = false

                recyclerViewAlbum.visibility = View.VISIBLE

                // Fetch albums and display them in the recyclerViewAlbum
                val albumList = AlbumFetcher(requireContext()).fetchAlbums()

                val albumAdapter = AlbumAdapter(albumList) { selectedAlbum ->
                    imageList = fetchImagesFromAlbum(selectedAlbum.name,requireContext())
                    // Assume fetchImagesFromAlbum returns List<Uri>

                    // Hide the album list RecyclerView
                    view.findViewById<RecyclerView>(R.id.recyclerViewAlbum)?.let { it.visibility = View.GONE }

                    // Show the images RecyclerView
                    view.findViewById<RecyclerView>(R.id.recyclerViewImages)?.let {
                        val Adapter = AlbumImagesAdapter(imageList)
                        it.visibility = View.VISIBLE
                        it.isNestedScrollingEnabled = false
                        it.setHasFixedSize(true)
                        it.setItemViewCacheSize(20)
                        // Set up the RecyclerView with a GridLayoutManager and the AlbumImagesAdapter
                        it.layoutManager = GridLayoutManager(requireContext(), 3)
                        it.adapter = Adapter


                        val preloadSizeProvider = ViewPreloadSizeProvider<Uri>()
                        val preloader = RecyclerViewPreloader(
                            Glide.with(this),
                            ImagePreloadModelProvider(imageList), // Create a class to provide images for preloading
                            preloadSizeProvider,
                            100 // Preload 20 items ahead of time
                        )
                        // Lazy loading of images
                        it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                                val visibleItemCount = layoutManager.childCount
                                val totalItemCount = layoutManager.itemCount
                                val firstVisibleItemPosition =
                                    layoutManager.findFirstVisibleItemPosition()

                                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                                    // Fetch more images asynchronously and update the adapter
                                    loadMoreImages(it)
                                }
                            }
                        })

                        // Load initial set of images
                        lifecycleScope.launch {
                            val initialImages = fetchMoreImages()
                            AlbumImagesAdapter.updateData(initialImages)
                        }
                    }

                    // Assuming you are fetching imageList for the selected album
                    val imageAdapter = AlbumImagesAdapter(imageList)
                    view.findViewById<RecyclerView>(R.id.recyclerViewImages).setHasFixedSize(true)
                    view.findViewById<RecyclerView>(R.id.recyclerViewImages).isNestedScrollingEnabled = false
                    view.findViewById<RecyclerView>(R.id.recyclerViewImages).setItemViewCacheSize(20)
                    view.findViewById<RecyclerView>(R.id.recyclerViewImages).adapter = imageAdapter
                }

                recyclerViewAlbum.apply {
                    layoutManager = LinearLayoutManager(context).apply {
                        initialPrefetchItemCount = 10  // Prefetch 10 items ahead
                    }
                    setHasFixedSize(true)  // Optimizes performance
                    setItemViewCacheSize(20)  // Increase cache size to avoid reloading views too often
                    isNestedScrollingEnabled = false
                    recyclerViewAlbum.adapter = albumAdapter
                }
            }

            // Hide selected count text and delete button
            view.findViewById<LinearLayout>(R.id.lnrselectedCountTextView)?.let { it.visibility = View.GONE }
            view.findViewById<LinearLayout>(R.id.lnrimgDelete)?.let { it.visibility = View.GONE }
            // Hide the selected images RecyclerView and the Photos RecyclerView
            view.findViewById<RecyclerView>(R.id.rcvSelected_Image)?.let { it.visibility = View.GONE }
            view.findViewById<RecyclerView>(R.id.recyclerView)?.let { it.visibility = View.GONE }
        }


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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    bottomSheetDialog.window?.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.black)
                }
            }

            bottomSheetDialog.show()
        }.execute()
    }

    fun loadMoreImages(recyclerView: RecyclerView)
    {
        lifecycleScope.launch(Dispatchers.IO)
        {
            val newImages = fetchMoreImages() // Simulate fetching new images
            withContext(Dispatchers.Main)
            {
                (recyclerView.adapter as AlbumImagesAdapter).addImages(newImages)
            }
        }
    }

    suspend fun fetchMoreImages(): List<Uri>
    {
        return withContext(Dispatchers.IO)
            {
            // Fetch additional images (e.g., from local storage or API)
            val moreImages = mutableListOf<Uri>()
            // e.g., moreImages.add(Uri.parse(...))
            moreImages
        }
    }
}