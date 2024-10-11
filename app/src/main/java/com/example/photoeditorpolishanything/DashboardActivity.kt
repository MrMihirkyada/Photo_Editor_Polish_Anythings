package com.example.photoeditorpolishanything

import ImageAdapter
import LocaleHelper
import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.example.photoeditorpolishanything.Adapter.AlbumAdapter
import com.example.photoeditorpolishanything.Adapter.AlbumImagesAdapter
import com.example.photoeditorpolishanything.Adapter.SelectedImagesAdapter
import com.example.photoeditorpolishanything.Model.ImageItem
import com.example.photoeditorpolishanything.Model.ImageViewModel
import com.example.photoeditorpolishanything.databinding.ActivityDashboardBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var imageUris: MutableList<Uri>
    private lateinit var viewModel: ImageViewModel

    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    private val REQUEST_CODE_GALLERY = 102
    private val REQUEST_CODE_CAMERA = 103
    private lateinit var currentPhotoPath: String
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var selectedImageView: ImageView? = null
    private var selectedTextView: TextView? = null
    private var isPhotosSelected: Boolean = true // Track the currently selected state


    companion object {
        var context: Context? = null
    }

    lateinit var adapter: SelectedImagesAdapter
    lateinit var AlbumImagesAdapter: AlbumImagesAdapter

    private var selectedButton: LinearLayout? = null

    lateinit var albumAdapter: AlbumAdapter

    var imageList: List<Uri> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        AlbumImagesAdapter = AlbumImagesAdapter(mutableListOf())


        // Check and request all permissions on activity start
        checkPermissions()

        initView()
    }

    override fun updateUIForSelectedLanguage() {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.txtAppName.text = resources.getString(R.string.app_name)
        binding.txtExplore.text = resources.getString(R.string.Explore)
        binding.txtExplores.text = resources.getString(R.string.Explore)
        binding.txtGridArt.text = resources.getString(R.string.GridArt)
        binding.txtPro.text = resources.getString(R.string.Pro)
        binding.txtCollage.text = resources.getString(R.string.Collage)
        binding.txtTemplates.text = resources.getString(R.string.Template)
        binding.txtBeautify.text = resources.getString(R.string.Beautify)
        binding.txtCamera.text = resources.getString(R.string.Camera)

        val bottomSheetView = bottomSheetDialog?.findViewById<View>(R.id.btnCamera)
        bottomSheetView?.findViewById<TextView>(R.id.txtThisappcanonlyaccessPhotoSelected)?.text = resources.getString(R.string.ThisappcanonlyaccessPhotoSelected)
        bottomSheetView?.findViewById<TextView>(R.id.txtPhotos)?.text = resources.getString(R.string.Photos)
        bottomSheetView?.findViewById<TextView>(R.id.txtAlbums)?.text = resources.getString(R.string.Albums)
        bottomSheetView?.findViewById<TextView>(R.id.selectedCountTextView)?.text = resources.getString(R.string.Recent)
    }

    private fun updateBottomSheetTextViews(view: View) {
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        view.findViewById<TextView>(R.id.txtThisappcanonlyaccessPhotoSelected).text = resources.getString(R.string.ThisappcanonlyaccessPhotoSelected)
        view.findViewById<TextView>(R.id.txtPhotos).text = resources.getString(R.string.Photos)
        view.findViewById<TextView>(R.id.txtAlbums).text = resources.getString(R.string.Albums)
        view.findViewById<TextView>(R.id.selectedCountTextView).text = resources.getString(R.string.Recent)
    }

    private fun initView() {
        imageUris = mutableListOf()

        viewModel = ViewModelProvider(this)[ImageViewModel::class.java]

        binding.lnrCollage.setOnClickListener {
            toggleButton(
                binding.imgCollage,
                binding.txtCollage
            )
        }

        binding.lnrgallery.setOnClickListener {
            checkStoragePermission()
        }

        binding.imgStore.setOnClickListener {
            var i = Intent(this@DashboardActivity, StoreActivity::class.java)
            startActivity(i)
        }

        binding.lnrTemplates.setOnClickListener {
            toggleButton(
                binding.imgTemplates,
                binding.txtTemplates
            )
        }

        binding.lnrBeautify.setOnClickListener {
            toggleButton(
                binding.imgBeautify,
                binding.txtBeautify
            )
        }

        binding.lnrCamera.setOnClickListener { toggleButton(binding.imgCamera, binding.txtCamera) }

        binding.imgPro.setOnClickListener {
            var i = Intent(this@DashboardActivity, Polish_Pro_PaymentActivity::class.java)
            startActivity(i)
        }

        binding.lnrMenu.setOnClickListener {
            var i = Intent(this@DashboardActivity, SettingsActivity::class.java)
            startActivity(i)
        }
    }

    private fun checkPermissions() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun checkPermissionsBeautify() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        } else {
            checkStoragePermissionBeautify()
        }
    }

    private fun checkCameraPermissions(requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE_CAMERA
            )
        } else {
            when (requestCode) {
                REQUEST_CODE_GALLERY -> openGallery()
                REQUEST_CODE_CAMERA -> dispatchTakePictureIntent()
            }
        }
    }

    private fun toggleButton(imageView: ImageView, textView: TextView) {
        if (selectedImageView != imageView) {
            // Reset previously selected button's image and text color
            selectedImageView?.setColorFilter(
                ContextCompat.getColor(this, R.color.grey),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            selectedTextView?.setTextColor(ContextCompat.getColor(this, R.color.grey))

            // Set new selected button's image and text color
            imageView.setColorFilter(
                ContextCompat.getColor(this, R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))

            // Update the selected button references
            selectedImageView = imageView
            selectedTextView = textView

            when (imageView.id) {
                R.id.imgCollage -> {
                    if (allPermissionsGranted()) {
                        showBottomSheet()
//                        val albums = getAlbums(this)
//
//                        albumAdapter = AlbumAdapter(albums) { album ->
//                            // Handle album click
//                        }
//
//                        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewAlbum)
//                        recyclerView.layoutManager = LinearLayoutManager(this)
//                        recyclerView.adapter = albumAdapter
                    } else {
                        checkPermissions()
                    }
                }

                R.id.imgTemplates -> {
                    val intent = Intent(this@DashboardActivity, TemplatesActivity::class.java)
                    startActivity(intent)
                }

                R.id.imgBeautify -> {
//                    if (!allPermissionsGranted()) {
//                        checkPermissionsBeautify()
//                    } else {
//                        checkStoragePermissionBeautify()
//                    }

                    if (allPermissionsGranted()) {
                        checkStoragePermissionBeautify()
                    } else {
                        checkPermissions()
                    }
                }

                R.id.imgCamera -> {
//                    checkCameraPermissions(REQUEST_CODE_CAMERA)
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.CAMERA),
                            REQUEST_CODE_CAMERA
                        )
                    } else {
                        dispatchTakePictureIntent()
                    }
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(
                        this,
                        "Error occurred while creating the file",
                        Toast.LENGTH_SHORT
                    ).show()
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "${applicationContext.packageName}.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
                }
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = getExternalFilesDir(null)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showBottomSheetBeautify() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetStyle)
        val view = layoutInflater.inflate(R.layout.camera_dialog, null)

        // TextView to show selected images count
        val txtSelectedImagesCount: TextView = view.findViewById(R.id.selectedCountTextView)

        // Initialize the selectedImages list to store only one selected image
        val selectedImages = mutableListOf<Uri>()
        adapter = SelectedImagesAdapter(selectedImages) { position ->
            if (position >= 0 && position < selectedImages.size) {
                // Instead of directly removing from selectedImages, call the adapter's removeItem method
                adapter.removeItem(position)
                updateSelectedImagesCount(txtSelectedImagesCount, selectedImages.size)
            } else {
                Log.e("SelectedImagesAdapter", "Attempted to remove an item at an invalid index: $position")
            }
        }

        view.findViewById<ImageView>(R.id.imgNext).setOnClickListener {
            // Check if there are any selected images
            if (selectedImages.isNotEmpty()) {
                // Take the first image (as only one can be selected)
                val selectedImageUri = selectedImages[0]

                // Prepare the intent to start EditActivity
                val intent = Intent(this, Beautify_Activity::class.java).apply {
                    putExtra("selected_image_uri", selectedImageUri.toString()) // Sending the selected image URI as a String
                }

                // Start EditActivity
                startActivity(intent)
                finish() // Optional: finish the current activity if you don't want to return to it
            }
            else
            {
                // Handle the case where no images are selected
                Toast.makeText(this, "No images selected", Toast.LENGTH_SHORT).show()
            }
        }

        // Initialize the selected state to Photos
        setSelectedState(isPhotosSelected)

        // Set click listeners for both layouts
        findViewById<LinearLayout>(R.id.lnrPhotos)?.setOnClickListener {
            isPhotosSelected = true
            setSelectedState(isPhotosSelected) // Update the visual state
        }

        findViewById<LinearLayout>(R.id.lnrAlbums)?.setOnClickListener {
            isPhotosSelected = false
            setSelectedState(isPhotosSelected) // Update the visual state
        }

        // Initial count update
        updateSelectedImagesCount(txtSelectedImagesCount, selectedImages.size)

        val selectedImagesRecyclerView: RecyclerView = view.findViewById(R.id.rcvSelected_Image)
        selectedImagesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        selectedImagesRecyclerView.adapter = adapter
        selectedImagesRecyclerView.isNestedScrollingEnabled = false

        view.findViewById<ImageView>(R.id.imgDelete)?.setOnClickListener {
            val size = selectedImages.size
            selectedImages.clear()
            adapter.notifyItemRangeRemoved(0, size)
            updateSelectedImagesCount(txtSelectedImagesCount, selectedImages.size)
        }

        val lnrPhotos = view.findViewById<LinearLayout>(R.id.lnrPhotos)
        val lnrAlbums = view.findViewById<LinearLayout>(R.id.lnrAlbums)

        // Ensure Photos is selected by default
        lnrPhotos.setBackgroundResource(R.drawable.toggle_selected)
        lnrAlbums.setBackgroundResource(R.drawable.toggle_unselected)

        // Set visibility for Photos as default
        view.findViewById<LinearLayout>(R.id.lnrselectedCountTextView)?.visibility = View.VISIBLE
        view.findViewById<LinearLayout>(R.id.lnrimgDelete)?.visibility = View.VISIBLE
        view.findViewById<RecyclerView>(R.id.rcvSelected_Image)?.visibility = View.VISIBLE
        view.findViewById<RecyclerView>(R.id.recyclerView)?.visibility = View.VISIBLE
        view.findViewById<RecyclerView>(R.id.recyclerViewAlbum)?.visibility = View.GONE

        FetchImagesTask(this) { imageList ->
            val imageAdapter = ImageAdapter(imageList) { selectedImageUri ->
                // Ensure only one image can be selected
                selectedImages.clear() // Clear any previously selected image
                selectedImages.add(selectedImageUri) // Add the newly selected image
                adapter.notifyDataSetChanged() // Notify the adapter to refresh the RecyclerView
                updateSelectedImagesCount(txtSelectedImagesCount, selectedImages.size) // Update the image count
            }

            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = GridLayoutManager(this, 3)
            recyclerView.adapter = imageAdapter
            recyclerView.isNestedScrollingEnabled = false

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.setOnShowListener {
                lnrPhotos.performClick() // Trigger the initial click to ensure Photos is selected

                // Now, change the visibility after dialog is fully shown
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bottomSheetDialog.window?.navigationBarColor = ContextCompat.getColor(this, R.color.black)
                }
            }
            bottomSheetDialog.show()
        }.execute()
    }


    private class FetchImagesTask(val context: DashboardActivity, val callback: (List<ImageItem>) -> Unit) :
        AsyncTask<Void, Void, List<ImageItem>>() {
        override fun doInBackground(vararg params: Void?): List<ImageItem> {
            val images = mutableListOf<ImageItem>()
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.Media._ID)
            val cursor = context.contentResolver.query(
                uri, projection, null, null,
                MediaStore.Images.Media.DATE_ADDED + " DESC"
            )

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val imageUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    images.add(ImageItem(imageUri))
                }
            }
            return images
        }

        override fun onPostExecute(result: List<ImageItem>) {
            callback(result)
        }
    }

    private class FetchBeautifyImagesTask(
        val context: DashboardActivity,
        val callback: (List<ImageItem>) -> Unit
    ) : AsyncTask<Void, Void, List<ImageItem>>() {
        override fun doInBackground(vararg params: Void?): List<ImageItem> {
            val images = mutableListOf<ImageItem>()
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.Media._ID)
            val cursor = context.contentResolver.query(
                uri,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED + " DESC"
            )

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val imageUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    images.add(ImageItem(imageUri))
                }
            }
            return images
        }

        override fun onPostExecute(result: List<ImageItem>) {
            callback(result)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_GALLERY
            )
        } else {
            showBottomSheet()
        }
    }

    private fun checkStoragePermissionBeautify() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_GALLERY
            )
        } else {
            showBottomSheetBeautify()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                allPermissionsGranted()
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    data class Album(val name: String, val thumbnailUri: Uri, val photoCount: Int)

    @SuppressLint("CutPasteId")
    fun showBottomSheet()
    {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetStyle)
        val view = layoutInflater.inflate(R.layout.camera_dialog, null)

        // TextView to show selected images count
        val txtSelectedImagesCount: TextView = view.findViewById(R.id.selectedCountTextView)

        val selectedImages = mutableListOf<Uri>()
        adapter = SelectedImagesAdapter(selectedImages) { position ->
            if (position >= 0 && position < selectedImages.size) {
                // Instead of directly removing from selectedImages, call the adapter's removeItem method
                adapter.removeItem(position)
                updateSelectedImagesCount(txtSelectedImagesCount, selectedImages.size)
            }
            else
            {
                Log.e("SelectedImagesAdapter", "Attempted to remove an item at an invalid index: $position")
            }
        }

        view.findViewById<ImageView>(R.id.imgNext).setOnClickListener {
            // Check if there are any selected images
            if (selectedImages.isNotEmpty()) {
                // For this example, let's take the first image
                val selectedImageUri = selectedImages[0] // Modify this line to choose which image you want

                // Prepare the intent to start EditActivity
                val intent = Intent(this, EditActivity::class.java).apply {
                    putExtra("selected_image_uri", selectedImageUri.toString()) // Sending the selected image URI as a String
                }

                // Start EditActivity
                startActivity(intent)
                finish() // Optional: finish the current activity if you don't want to return to it
            }
            else
            {
                // Handle the case where no images are selected
                Toast.makeText(this, "No images selected", Toast.LENGTH_SHORT).show()
            }
        }

        // Initialize the selected state to Photos
        setSelectedState(isPhotosSelected)

        // Set click listeners for both layouts
        findViewById<LinearLayout>(R.id.lnrPhotos)?.setOnClickListener {
            isPhotosSelected = true
            setSelectedState(isPhotosSelected) // Update the visual state
        }

        findViewById<LinearLayout>(R.id.lnrAlbums)?.setOnClickListener {
            isPhotosSelected = false
            setSelectedState(isPhotosSelected) // Update the visual state
        }

        // Initial count update
        updateSelectedImagesCount(txtSelectedImagesCount, selectedImages.size)

        val selectedImagesRecyclerView: RecyclerView = view.findViewById(R.id.rcvSelected_Image)
        selectedImagesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        selectedImagesRecyclerView.adapter = adapter
        selectedImagesRecyclerView.isNestedScrollingEnabled = false

        view.findViewById<ImageView>(R.id.imgDelete)?.setOnClickListener {
            val size = selectedImages.size
            selectedImages.clear()
            adapter.notifyItemRangeRemoved(0, size)
            updateSelectedImagesCount(txtSelectedImagesCount, selectedImages.size)
        }

        val lnrPhotos = view.findViewById<LinearLayout>(R.id.lnrPhotos)
        val lnrAlbums = view.findViewById<LinearLayout>(R.id.lnrAlbums)

        // Ensure Photos is selected by default
        lnrPhotos.setBackgroundResource(R.drawable.toggle_selected)
        lnrAlbums.setBackgroundResource(R.drawable.toggle_unselected)

        // Set visibility for Photos as default
        view.findViewById<LinearLayout>(R.id.lnrselectedCountTextView)?.let { it.visibility = View.VISIBLE }
        view.findViewById<LinearLayout>(R.id.lnrimgDelete)?.let { it.visibility = View.VISIBLE }
        view.findViewById<RecyclerView>(R.id.rcvSelected_Image)?.let { it.visibility = View.VISIBLE }
        view.findViewById<RecyclerView>(R.id.recyclerView)?.let { it.visibility = View.VISIBLE }
        view.findViewById<RecyclerView>(R.id.recyclerViewAlbum)?.let { it.visibility = View.GONE }

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
                val albumList = AlbumFetcher(this).fetchAlbums()

                val albumAdapter = AlbumAdapter(albumList) { selectedAlbum ->
                    imageList = fetchImagesFromAlbum(selectedAlbum.name, this)
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
                        it.layoutManager = GridLayoutManager(this, 3)
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

//                    recyclerViewAlbum.setHasFixedSize(true)
//                    recyclerViewAlbum.isNestedScrollingEnabled = false
//                    recyclerViewAlbum.setItemViewCacheSize(20)
//                    recyclerViewAlbum.layoutManager = LinearLayoutManager(this).apply {
//                        initialPrefetchItemCount = 10  // Prefetch items before they appear
//                    }
//                    recyclerViewAlbum.adapter = albumAdapter
            }

            // Hide selected count text and delete button
            view.findViewById<LinearLayout>(R.id.lnrselectedCountTextView)
                ?.let { it.visibility = View.GONE }

            view.findViewById<LinearLayout>(R.id.lnrimgDelete)?.let { it.visibility = View.GONE }

            // Hide the selected images RecyclerView and the Photos RecyclerView
            view.findViewById<RecyclerView>(R.id.rcvSelected_Image)
                ?.let { it.visibility = View.GONE }

            view.findViewById<RecyclerView>(R.id.recyclerView)?.let { it.visibility = View.GONE }
        }

        FetchImagesTask(this) { imageList ->
            val imageAdapter = ImageAdapter(imageList) { selectedImageUri ->
                if (selectedImages.size < 100)
                {
                    selectedImages.add(selectedImageUri)
                    adapter.notifyDataSetChanged()
                    updateSelectedImagesCount(txtSelectedImagesCount, selectedImages.size)
                }
                else
                {
                    Toast.makeText(this, "You can select a maximum of 100 images.", Toast.LENGTH_SHORT).show()
                }
            }

            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = GridLayoutManager(this, 3)
            recyclerView.adapter = imageAdapter
            recyclerView.isNestedScrollingEnabled = false

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.setOnShowListener {
                lnrPhotos.performClick() // Trigger the initial click to ensure Photos is selected

                // Now, change the visibility after dialog is fully shown
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    bottomSheetDialog.window?.navigationBarColor = ContextCompat.getColor(this, R.color.black)
                }
            }
            bottomSheetDialog.show()
        }.execute()
    }

    private fun updateSelectedImagesCount(txtSelectedImagesCount: TextView, count: Int)
    {
        txtSelectedImagesCount.text = "Select 1 - 100 Photos  ($count)"
    }

    private fun toggleButton(button: LinearLayout?)
    {
        val view = layoutInflater.inflate(R.layout.camera_dialog, null)
        if (selectedButton != button)
        {
            // Deselect previously selected button
            selectedButton?.let {
                it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black)
                val prevTextView = it.findViewById<TextView>(R.id.txtFilter) ?: it.findViewById<TextView>(R.id.txtAdjust)
                prevTextView?.setTextColor(ContextCompat.getColor(this, R.color.white))
            }

            // Toggle the state of the button
            selectedButton = button
            selectedButton?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            val newTextView = button?.findViewById<TextView>(R.id.txtFilter) ?: button?.findViewById<TextView>(R.id.txtAdjust)
            newTextView?.setTextColor(ContextCompat.getColor(this, R.color.white))

            // Handle actions based on selected button (if needed)
            when (selectedButton?.id)
            {
                R.id.lnrPhotos -> {

                    view.findViewById<LinearLayout>(R.id.lnrselectedCountTextView).visibility = View.VISIBLE
                    view.findViewById<LinearLayout>(R.id.lnrimgDelete).visibility = View.VISIBLE
                    view.findViewById<RecyclerView>(R.id.rcvSelected_Image).visibility = View.VISIBLE
                    view.findViewById<RecyclerView>(R.id.recyclerView).visibility = View.VISIBLE

                    view.findViewById<RecyclerView>(R.id.recyclerViewAlbum).visibility = View.GONE



                    view.findViewById<RecyclerView>(R.id.lnrPhotos).setBackgroundResource(R.drawable.toggle_selected)
                    view.findViewById<RecyclerView>(R.id.lnrAlbums).setBackgroundResource(R.drawable.toggle_unselected)
                }

                R.id.lnrAlbums -> {

                    view.findViewById<RecyclerView>(R.id.recyclerViewAlbum).visibility = View.VISIBLE

                    view.findViewById<LinearLayout>(R.id.lnrselectedCountTextView).visibility = View.GONE
                    view.findViewById<LinearLayout>(R.id.lnrimgDelete).visibility = View.GONE
                    view.findViewById<RecyclerView>(R.id.rcvSelected_Image).visibility = View.GONE
                    view.findViewById<RecyclerView>(R.id.recyclerView).visibility = View.GONE

                    view.findViewById<RecyclerView>(R.id.lnrAlbums).setBackgroundResource(R.drawable.toggle_selected)
                    view.findViewById<RecyclerView>(R.id.lnrPhotos).setBackgroundResource(R.drawable.toggle_unselected)
                }
            }
        }
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

    suspend fun fetchMoreImages(): List<Uri> {
        return withContext(Dispatchers.IO) {
            // Fetch additional images (e.g., from local storage or API)
            val moreImages = mutableListOf<Uri>()
            // e.g., moreImages.add(Uri.parse(...))
            moreImages
        }
    }

    override fun onResume() {
        super.onResume()
        // Select a specific button in onResume (example: lnrFilter)
        val lnrPhotos = findViewById<LinearLayout>(R.id.lnrPhotos)
        toggleButton(lnrPhotos)
    }

    private fun setSelectedState(isPhotosSelected: Boolean) {
        val view = layoutInflater.inflate(R.layout.camera_dialog, null)

        // Set the selected state
        if (isPhotosSelected)
        {

            // Photos selected
            view.findViewById<LinearLayout>(R.id.lnrPhotos).setBackgroundColor(Color.BLUE) // Change to your desired color
            view.findViewById<TextView>(R.id.txtPhotos).setTextColor(Color.WHITE)
            view.findViewById<LinearLayout>(R.id.lnrAlbums).setBackgroundColor(Color.BLACK) // Change to your desired color
            view.findViewById<TextView>(R.id.txtAlbums).setTextColor(Color.WHITE)
        }
        else
        {

            // Albums selected
            view.findViewById<LinearLayout>(R.id.lnrAlbums).setBackgroundColor(Color.BLUE) // Change to your desired color
            view.findViewById<TextView>(R.id.txtAlbums).setTextColor(Color.WHITE)
            view.findViewById<LinearLayout>(R.id.lnrPhotos).setBackgroundColor(Color.BLACK) // Change to your desired color
            view.findViewById<TextView>(R.id.txtPhotos).setTextColor(Color.WHITE)
        }
    }

}