package com.example.photoeditorpolishanything

import ImageAdapter
import LocaleHelper
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.photoeditorpolishanything.Api.DynamicImage
import com.example.photoeditorpolishanything.Api.Layout
import com.example.photoeditorpolishanything.Api.Position
import com.example.photoeditorpolishanything.Model.ImageItem
import com.example.photoeditorpolishanything.databinding.ActivityEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditActivity : BaseActivity()
{
    lateinit var binding: ActivityEditBinding
    private lateinit var originalBitmap: Bitmap
    private lateinit var filteredBitmap: Bitmap
    private var selectedButton: LinearLayout? = null
    private var bottomSheetDialog: BottomSheetDialog? = null
    private lateinit var dynamicImage: DynamicImage
    private val REQUEST_CODE_PICK_IMAGE = 1
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21)
        {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)

        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        initView()
    }

    override fun updateUIForSelectedLanguage()
    {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.txtOpen.text = resources.getString(R.string.Open)
        binding.txtMore.text = resources.getString(R.string.More)
        binding.txtOriginal.text = resources.getString(R.string.Original)
        binding.txtBright.text = resources.getString(R.string.Bright)
        binding.txtStory.text = resources.getString(R.string.Story)
        binding.txtNatural.text = resources.getString(R.string.Natural)
        binding.txtWarm.text = resources.getString(R.string.Warm)
        binding.txtDew.text = resources.getString(R.string.Dew)
        binding.txtGold.text = resources.getString(R.string.Gold)
        binding.txtLomo.text = resources.getString(R.string.Lomo)
        binding.txtPink.text = resources.getString(R.string.Pink)
        binding.txtStyle.text = resources.getString(R.string.Styles)
        binding.txtTools.text = resources.getString(R.string.Tools)
        binding.txtExport.text = resources.getString(R.string.Export)

        bottomSheetDialog?.let {
            updateBottomSheetTextViews(it.findViewById(R.id.mainlayout)!!)
            updateBottomSheetTextView(it.findViewById(R.id.btnCamera)!!)
        }

    }

    private fun updateBottomSheetTextViews(view: View)
    {
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        view.findViewById<TextView>(R.id.txtSticker)?.text = resources.getString(R.string.Sticker)
        view.findViewById<TextView>(R.id.txtDraw)?.text = resources.getString(R.string.Draw)
        view.findViewById<TextView>(R.id.txtLayout)?.text = resources.getString(R.string.Layout)
        view.findViewById<TextView>(R.id.txtTemplates)?.text = resources.getString(R.string.Templates)
        view.findViewById<TextView>(R.id.txtFilter)?.text = resources.getString(R.string.Filter)
        view.findViewById<TextView>(R.id.txtAdjust)?.text = resources.getString(R.string.Adjust)
        view.findViewById<TextView>(R.id.txtBackground)?.text = resources.getString(R.string.Background)
        view.findViewById<TextView>(R.id.txtText)?.text = resources.getString(R.string.Text)
        view.findViewById<TextView>(R.id.txtCrop)?.text = resources.getString(R.string.Crop)
    }

    private fun updateBottomSheetTextView(view: View)
    {
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        view.findViewById<TextView>(R.id.txtThisappcanonlyaccessPhotoSelected).text = resources.getString(R.string.ThisappcanonlyaccessPhotoSelected)
        view.findViewById<TextView>(R.id.txtPhotos).text = resources.getString(R.string.Photos)
        view.findViewById<TextView>(R.id.txtAlbums).text = resources.getString(R.string.Albums)
        view.findViewById<TextView>(R.id.selectedCountTextView).text = resources.getString(R.string.Recent)
    }

    @SuppressLint("MissingInflatedId")
    private fun initView()
    {
        val imageContainer = findViewById<ImageView>(R.id.imgEditSelectImages)

        binding.imgEditSelectImage.setImageDrawable(null)

        val selectedImageUriString = intent.getStringExtra("selected_image_uri")

        val selectedImageEditUrl = intent.getStringExtra("selected_imageEditUrl")

        val selectedImageUri = Uri.parse(selectedImageUriString)

        val source = intent.getStringExtra("source")

        val imageEditUrl: String? = intent.getStringExtra("selected_imageEditUrl") // Retrieve correctly

        if (selectedImageUri != null && imageEditUrl != null)
        {
            loadMaskAndApply(selectedImageUri, imageEditUrl)
        }
        else
        {
            Toast.makeText(this, "Invalid image or template URL", Toast.LENGTH_SHORT).show()
        }

        // If the source is from the adapter, load the image using Glide
         if (source == "adapter" && !selectedImageUriString.isNullOrBlank())
        {
            // Load image from selected_imageEditUrl using Glide
            Glide.with(this).load(selectedImageEditUrl).into(binding.imgEditSelectImage)
        }
        else
        {
            // Load image from selected_image_uri using setImageUR
            selectedImageUriString?.let {
                val selectedImageUri = Uri.parse(selectedImageUriString)
                binding.imgEditSelectImage.setImageURI(selectedImageUri)
            }
        }

        // Load and display the selected image using Glide
        // Load the selected image into originalBitmap for editing
        originalBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(selectedImageUri!!))

        // Make a copy of the original bitmap to apply filters
        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)

        binding.txtOpen.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetStyle)
            val view = layoutInflater.inflate(R.layout.camera_dialog, null)
            view.findViewById<ImageView>(R.id.imgCross)?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            updateBottomSheetTextView(view)

            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

            // Set layout manager with 3 columns
            recyclerView.layoutManager = GridLayoutManager(this, 3)

            FetchImagesTask(this) { imageList ->
                recyclerView.adapter = ImageAdapter(imageList) { selectedImageUri ->
                    bottomSheetDialog.dismiss()
                    val intent = Intent(this, EditActivity::class.java)
                    intent.putExtra("selected_image_uri", selectedImageUri.toString())
                    startActivity(intent)
                    finish()
                }
                bottomSheetDialog.setContentView(view)
                bottomSheetDialog.setOnShowListener {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        bottomSheetDialog.window?.navigationBarColor =
                            ContextCompat.getColor(this, R.color.black)
                    }
                }
                bottomSheetDialog.show()
            }.execute()
        }

        // Load the selected image into originalBitmap for editing
        Glide.with(this)
            .asBitmap()
            .load(selectedImageUri)
            .apply(RequestOptions().override(800, 800)) // Adjust size as needed
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    originalBitmap = resource

                    // Make a copy of the original bitmap to apply filters
                    filteredBitmap = originalBitmap.copy(originalBitmap.config, true)

                    // Display the original image in ImageView
                    if (source == "adapter") {
                        binding.imgEditSelectImage.setImageBitmap(null)
                    } else {
                        binding.imgEditSelectImage.setImageBitmap(originalBitmap)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgEditSelectImages)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgEditSelectBright)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgEditSelectStory)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgEditSelectNatural)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgEditSelectWarm)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgEditSelectBright)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgEditSelectDew)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgEditSelectGold)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgEditSelectLomo)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgEditSelectPink)

        // Display the original image in ImageView
        binding.imgEditSelectImage.setImageBitmap(originalBitmap)

        // Example: Apply brightness filter when "Bright Filter" button is clicked
        binding.imgEditSelectImages.setOnClickListener {
            resetToOriginalImage()
        }

        binding.imgEditSelectBright.setOnClickListener {
            applyBrightFilter()
        }

        binding.imgEditSelectStory.setOnClickListener {
            applyStoryFilter()
        }

        binding.imgEditSelectNatural.setOnClickListener {
            applyNaturalFilter()
        }

        binding.imgEditSelectWarm.setOnClickListener {
            applyWarmFilter()
        }

        binding.imgEditSelectDew.setOnClickListener {
            applyDewFilter(selectedImageUri)
        }

        binding.imgEditSelectGold.setOnClickListener {
            applyGoldFilter()
        }

        binding.imgEditSelectLomo.setOnClickListener {
            applyLomoFilter()
        }

        binding.imgEditSelectPink.setOnClickListener {
            applyPinkFilter()
        }

        binding.lnrStyles.setOnClickListener { toggleButton(binding.lnrStyles) }
        binding.lnrTools.setOnClickListener { toggleButton(binding.lnrTools) }
        binding.lnrExport.setOnClickListener { toggleButton(binding.lnrExport) }



        // Get template data from Intent
        val imageEditUrls = intent.getStringExtra("selected_imageEditUrl")
        val layoutWidth = intent.getIntExtra("layout_width", 0)
        val layoutHeight = intent.getIntExtra("layout_height", 0)
        val positionX = intent.getIntExtra("position_x", 0)
        val positionY = intent.getIntExtra("position_y", 0)

        // Load the template image into an ImageView
        val templateImageView = binding.imgEditSelectImage
        // Assuming imageEditUrls is a valid URL to load the image
        Glide.with(this).load(imageEditUrls).into(templateImageView)


        // Create dynamicImage object to store layout and position data
        dynamicImage = DynamicImage(Layout(layoutWidth, layoutHeight), Position(positionX, positionY))


//        var container = binding.templateContainer


        templateImageView.setOnClickListener {
            openImagePickerForTemplate()
//            createDynamicFrameLayout(dynamicImage, container)

//            binding.templateContainer.visibility = View.VISIBLE
            binding.templateImageView.visibility = View.VISIBLE
        }
    }
 
    private fun toggleButton(button: LinearLayout?) {
        // Check if the clicked button is already selected
        val selectedImageUriString = intent.getStringExtra("selected_image_uri")
        var selectedImageUri = Uri.parse(selectedImageUriString)
      
//        loadImage(selectedImageUri)
        if (selectedButton != button)
        {
            // Deselect previously selected button
            selectedButton?.let {
                it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black)
            }

            // Toggle the state of the button
            selectedButton = button
            selectedButton?.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.blue)

            // Handle actions based on selected button (if needed)
            when (selectedButton?.id)
            {
                R.id.lnrStyles -> {
                    // Handle Styles button click
                    // Example: performStylesAction()
                }

                R.id.lnrTools -> {
                    val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetStyle)
                    val view = layoutInflater.inflate(R.layout.tools_dialog, null)

                    updateBottomSheetTextViews(view)

                    view.findViewById<LinearLayout>(R.id.lnrSticker).setOnClickListener {
                        var i = Intent(this@EditActivity, Sticker_Activity::class.java)
                        i.putExtra("selected_image_uri", selectedImageUri.toString())
                        startActivity(i)
                        bottomSheetDialog.dismiss()
                    }

                    view.findViewById<LinearLayout>(R.id.lnrDraw).setOnClickListener {
                        var i = Intent(this@EditActivity, Draw_Activity::class.java)
                        i.putExtra("selected_image_uri", selectedImageUri.toString())
                        startActivity(i)
                        bottomSheetDialog.dismiss()
                    }

                    view.findViewById<LinearLayout>(R.id.lnrLayout).setOnClickListener {
                        var i = Intent(this@EditActivity, LayoutActivity::class.java)
                        i.putExtra("selected_image_uri", selectedImageUri.toString())
                        startActivity(i)
                        bottomSheetDialog.dismiss()
                    }

                    view.findViewById<LinearLayout>(R.id.lnrTemplates).setOnClickListener {
                        var i = Intent(this@EditActivity, TemplatesActivity::class.java)
                        startActivity(i)
                        bottomSheetDialog.dismiss()
                    }

                    view.findViewById<LinearLayout>(R.id.lnrFilter).setOnClickListener {
                        var i = Intent(this@EditActivity, Adjust_Activity::class.java)
                        i.putExtra("selected_image_uri", selectedImageUri.toString())
                        startActivity(i)
                        bottomSheetDialog.dismiss()
                    }

                    view.findViewById<LinearLayout>(R.id.lnrAdjust).setOnClickListener {
                        var i = Intent(this@EditActivity, Adjust_Activity::class.java)
                        i.putExtra("selected_image_uri", selectedImageUri.toString())
                        startActivity(i)
                        bottomSheetDialog.dismiss()
                    }

                    view.findViewById<LinearLayout>(R.id.lnrBackground).setOnClickListener {
                        var i = Intent(this@EditActivity, Background_Activity::class.java)
                        i.putExtra("selected_image_uri", selectedImageUri.toString())
                        startActivity(i)
                        bottomSheetDialog.dismiss()
                    }

                    view.findViewById<LinearLayout>(R.id.lnrText).setOnClickListener {
                        var i = Intent(this@EditActivity, Text_Activity::class.java)
                        i.putExtra("selected_image_uri", selectedImageUri.toString())
                        startActivity(i)
                        bottomSheetDialog.dismiss()
                    }

                    view.findViewById<LinearLayout>(R.id.lnrCrop).setOnClickListener {
                        var i = Intent(this@EditActivity, Crop_Activity::class.java)
                        i.putExtra("selected_image_uri", selectedImageUri.toString())
                        startActivity(i)
                        bottomSheetDialog.dismiss()
                    }
                    bottomSheetDialog.setOnShowListener {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            bottomSheetDialog.window?.navigationBarColor =
                                ContextCompat.getColor(this, R.color.black)
                        }
                    }
                    bottomSheetDialog.setContentView(view)
                    bottomSheetDialog.show()
                }

                R.id.lnrExport -> {
                    var i = Intent(this@EditActivity, Export_Activity::class.java)
                    i.putExtra("selected_image_uri", selectedImageUri.toString())
                    startActivity(i)
                }
            }
        }
    }

    private fun loadImage(imageUri: Uri?) {
        val selectedImageUrl = intent.getStringExtra("selected_image_url")
        if (!selectedImageUrl.isNullOrBlank()) {
            Glide.with(this)
                .load(imageUri)
                .into(binding.imgEditSelectImage)
        } else {
            Toast.makeText(this, "Image URL is empty or null", Toast.LENGTH_SHORT).show()
            finish() // Finish activity or handle accordingly
        }
    }

    override fun onResume() {
        super.onResume()

        // Initialize the default selected button (if needed)
        if (selectedButton == null) {
            selectedButton =
                binding.lnrStyles // For example, select the Styles button by default
            selectedButton?.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.blue)
        }
    }
 
    private class FetchImagesTask(val context: EditActivity, val callback: (List<ImageItem>) -> Unit) : AsyncTask<Void, Void, List<ImageItem>>()
    {
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
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id
                        )
                    images.add(ImageItem(imageUri))
                }
            }
            return images
        }

        override fun onPostExecute(result: List<ImageItem>) {
            callback(result)
        }
    }

    private fun applyBrightFilter() {
        // Example: Increase brightness by 20%
        val brightness = 1.2f

        // Create a color matrix for brightness adjustment
        val colorMatrix = ColorMatrix().apply {
            setScale(brightness, brightness, brightness, 1f)
        }

        // Create a color filter with the color matrix
        val colorFilter = ColorMatrixColorFilter(colorMatrix)

        // Apply the color filter to the filtered bitmap
        val canvas = Canvas(filteredBitmap)
        val paint = Paint()
        paint.colorFilter = colorFilter
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Update the ImageView with the filtered bitmap
        binding.imgEditSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyStoryFilter() {
        // Apply contrast filter
        val contrast = 1.5f
        val contrastMatrix = ColorMatrix().apply {
            setSaturation(contrast)
        }

        val colorFilter = ColorMatrixColorFilter(contrastMatrix)
        val canvas = Canvas(filteredBitmap)
        val paint = android.graphics.Paint()
        paint.colorFilter = colorFilter
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        binding.imgEditSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyNaturalFilter() {
        val colorMatrix = ColorMatrix().apply {
            // Increase saturation slightly
            setSaturation(1.2f)
            // Apply brightness and contrast adjustments
            val contrast = 1.1f
            val brightness = 1.05f
            val scale = floatArrayOf(
                contrast, 0f, 0f, 0f, brightness,
                0f, contrast, 0f, 0f, brightness,
                0f, 0f, contrast, 0f, brightness,
                0f, 0f, 0f, 1f, 0f
            )
            postConcat(ColorMatrix(scale))
        }

        val colorFilter = ColorMatrixColorFilter(colorMatrix)
        val canvas = Canvas(filteredBitmap)
        val paint = Paint()
        paint.colorFilter = colorFilter
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        binding.imgEditSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyWarmFilter() {
        val colorMatrix = ColorMatrix().apply {
            // Warm filter: Increase red and yellow tones
            set(
                floatArrayOf(
                    1.2f, 0.0f, 0.0f, 0.0f, 0.0f,
                    0.0f, 1.1f, 0.0f, 0.0f, 0.0f,
                    0.0f, 0.0f, 0.9f, 0.0f, 0.0f,
                    0.0f, 0.0f, 0.0f, 1.0f, 0.0f
                )
            )
        }

        val colorFilter = ColorMatrixColorFilter(colorMatrix)
        val canvas = Canvas(filteredBitmap)
        val paint = Paint()
        paint.colorFilter = colorFilter
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        binding.imgEditSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyDewFilter(imageUri: Uri) {
        val filteredBitmap =
            Bitmap.createBitmap(
                originalBitmap.width,
                originalBitmap.height,
                originalBitmap.config
            )

        // Apply color adjustments using ColorMatrix
        val colorMatrix = ColorMatrix().apply {
            // Increase saturation slightly
            setSaturation(1.2f)
            // Apply brightness and contrast adjustments
            val contrast = 1.1f
            val brightness = 1.05f
            val scale = floatArrayOf(
                contrast, 0f, 0f, 0f, brightness,
                0f, contrast, 0f, 0f, brightness,
                0f, 0f, contrast, 0f, brightness,
                0f, 0f, 0f, 1f, 0f
            )
            postConcat(ColorMatrix(scale))
        }

        // Create a Paint object with the color filter
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgEditSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyGoldFilter() {
        // Create a copy of the original bitmap
        val filteredBitmap =
            Bitmap.createBitmap(
                originalBitmap.width,
                originalBitmap.height,
                originalBitmap.config
            )

        // Apply color adjustments using ColorMatrix for a less intense gold tone
        val colorMatrix = ColorMatrix().apply {
            // Adjust the red and green channels to create a toned-down golden hue
            set(
                floatArrayOf(
                    1.5f, 0f, 0f, 0f, 0f,
                    0f, 1.2f, 0f, 0f, 0f,
                    0f, 0f, 0.8f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        }

        // Create a Paint object with the color filter
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgEditSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyLomoFilter() {
        // Create a copy of the original bitmap
        val filteredBitmap =
            Bitmap.createBitmap(
                originalBitmap.width,
                originalBitmap.height,
                originalBitmap.config
            )

        // Apply color adjustments using ColorMatrix for a less intense Lomo effect
        val colorMatrix = ColorMatrix().apply {
            // Adjust contrast and brightness to achieve a subtle Lomo effect
            set(
                floatArrayOf(
                    1.1f, 0f, 0f, 0f, 0f,
                    0f, 1.1f, 0f, 0f, 0f,
                    0f, 0f, 1.1f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        }

        // Create a Paint object with the color filter
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgEditSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyPinkFilter() {
        // Create a copy of the original bitmap
        val filteredBitmap =
            Bitmap.createBitmap(
                originalBitmap.width,
                originalBitmap.height,
                originalBitmap.config
            )

        // Apply color adjustments using ColorMatrix for a less intense pink filter effect
        val colorMatrix = ColorMatrix().apply {
            // Increase red and blue channels slightly for a subtle pink tint
            set(
                floatArrayOf(
                    1.1f, 0f, 0f, 0f, 0f,
                    0f, 0.9f, 0f, 0f, 0f,
                    0f, 0f, 1.1f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        }

        // Create a Paint object with the color filter
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgEditSelectImage.setImageBitmap(filteredBitmap)
    }

    fun onResetButtonClick(view: View)
    {
        resetToOriginalImage()
    }

    private fun resetToOriginalImage()
    {
        // Ensure originalBitmap is initialized and not null
        originalBitmap?.let {
            binding.imgEditSelectImage.setImageBitmap(it)
        }
    }

    private fun loadMaskAndApply(imageUri: Uri, maskUrl: String)
    {
        Glide.with(this)
            .asBitmap()
            .load(maskUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(maskBitmap: Bitmap, transition: Transition<in Bitmap>?)
                {
                    applyMaskToImage(imageUri, maskBitmap)
                }
        
                override fun onLoadCleared(placeholder: Drawable?)
                {
                    // Handle placeholder or clear actions if needed
                }
            })
    }

    private fun applyMaskToImage(imageUri: Uri, maskBitmap: Bitmap)
    {
        val originalBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)

        // Create a mutable bitmap with the same dimensions as the mask
        val resultBitmap = Bitmap.createBitmap(maskBitmap.width, maskBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Draw the original image on the canvas
        canvas.drawBitmap(originalBitmap, null, Rect(0, 0, maskBitmap.width, maskBitmap.height), paint)

        // Set the paint to use the mask as a destination-in mode
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

        // Draw the mask on top of the original image
        canvas.drawBitmap(maskBitmap, 0f, 0f, paint)

        // Set the resulting masked image to an ImageView
        binding.imgEditSelectImage.setImageBitmap(resultBitmap)
    }


    // Opens the image picker to select an image
    private fun openImagePickerForTemplate()
    {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    companion object
    {
        const val REQUEST_CODE_PICK_IMAGE = 1001
    }

    // Handle the result of image picker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let { uri ->
                val bitmap = uriToBitmap(uri)
                bitmap?.let { userBitmap ->
                    displayUserImageInTemplate(userBitmap)
                }
            }
        }
    }

    // Convert URI to Bitmap
    private fun uriToBitmap(uri: Uri): Bitmap?
    {
        return try
        {
            val inputStream = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            null
        }
    }


    // Function to simulate getting dynamic image data from the API
    fun getDynamicImagesFromApi(): List<DynamicImage>
    {
        return listOf(
            DynamicImage(layout = Layout(width = 137, height = 1014), position = Position(x = 1407, y = 1014)),
            DynamicImage(layout = Layout(width = 240, height = 239), position = Position(x = 574, y = 221))
        )
    }

    private fun displayUserImageInTemplate(bitmap: Bitmap, dynamicImage: DynamicImage) {
        // Dynamically set the size and position from the API response
        val width = dynamicImage.layout.width
        val height = dynamicImage.layout.height
        val x = dynamicImage.position.x
        val y = dynamicImage.position.y

        // Create an ImageView for the user's image
        val userImageView = ImageView(this)
        userImageView.setImageBitmap(bitmap)

        // Set layout parameters for the ImageView based on the API response
        val params = FrameLayout.LayoutParams(width, height)
        params.leftMargin = x // Set X position
        params.topMargin = y  // Set Y position

        // Add the ImageView to the FrameLayout (imgEditSelectImage is a FrameLayout)
        binding.imgEditSelectImageContainer.addView(userImageView, params)
    }

    // Display the user's image in the template at a specific position and size
    private fun displayUserImageInTemplate(bitmap: Bitmap) {
        // Set the user's image into the ImageView
        binding.templateImageView.setImageBitmap(bitmap)
        binding.templateImageView.visibility = View.VISIBLE

        // Dynamically set the size and position based on the template data (this would come from API)
        val width = 240 // Replace with actual width from the API
        val height = 239 // Replace with actual height from the API
        val x = 574 // Replace with actual x-position from the API
        val y = 221 // Replace with actual y-position from the API

        // Set the layout parameters for the user's image
        val params = FrameLayout.LayoutParams(width, height)
        params.leftMargin = x
        params.topMargin = y
        binding.templateImageView.layoutParams = params
    }
}