package com.example.photoeditorpolishanything

import LocaleHelper
import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
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
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.photoeditorpolishanything.Model.ImageItem
import com.example.photoeditorpolishanything.databinding.ActivityAdjustBinding
import yuku.ambilwarna.AmbilWarnaDialog
import java.lang.Math.cos
import java.lang.Math.sin
import java.util.Random

class Adjust_Activity : BaseActivity()
{

    lateinit var binding: ActivityAdjustBinding
    private lateinit var originalBitmap: Bitmap
    private lateinit var filteredBitmap: Bitmap
    private lateinit var buttonsLayout: LinearLayout // Adjust to your layout type
    private var selectedButton: LinearLayout? = null
    private var mDefaultColor = 0
    private var currentMode: AdjustmentMode = AdjustmentMode.NONE
    private val adjustmentProgress = mutableMapOf<AdjustmentMode, Int>()
    private var currentButtonId: String = "button1"
    private var saturation = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdjustBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21)
        {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        loadAllButtonProgress()

        initView()
    }

    private fun initView()
    {
        val imageUriString = intent.getStringExtra("selected_image_uri")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            val imageView = findViewById<ImageView>(R.id.imgAdjustSelectImage)

            if (imageView != null)
            {
                try
                {
                    Glide.with(this).load(imageUri).into(imageView)
                }
                catch (e: Exception)
                {
                    logErrorAndFinish("Glide error: ${e.message}")
                }
            }
            else
            {
                logErrorAndFinish("ImageView not found in layout")
            }
        }
        else
        {
            logErrorAndFinish("Image URI string is null")
        }

        val selectedImageUriString = intent.getStringExtra("selected_image_uri")
        var selectedImageUri = Uri.parse(selectedImageUriString)

        // Load and display the selected image using Glide
        // Load the selected image into originalBitmap for editing
        originalBitmap =
            BitmapFactory.decodeStream(contentResolver.openInputStream(selectedImageUri))

        // Make a copy of the original bitmap to apply filters
        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)

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
                    binding.imgAdjustSelectImage.setImageBitmap(originalBitmap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectImages)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectBright)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectStory)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectNatural)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectWarm)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectBright)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectDew)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectGold)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectLomo)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.imgAdjustSelectPink)

        // Display the original image in ImageView
        binding.imgAdjustSelectImage.setImageBitmap(originalBitmap)


        // Example: Apply brightness filter when "Bright Filter" button is clicked
        binding.imgAdjustSelectImages.setOnClickListener {
            resetToOriginalImage()
        }

        binding.imgAdjustSelectBright.setOnClickListener {
            applyBrightFilter()
        }

        binding.imgAdjustSelectStory.setOnClickListener {
            applyStoryFilter()
        }

        binding.imgAdjustSelectNatural.setOnClickListener {
            applyNaturalFilter()
        }

        binding.imgAdjustSelectWarm.setOnClickListener {
            applyWarmFilter()
        }

        binding.imgAdjustSelectDew.setOnClickListener {
            applyDewFilter(selectedImageUri)
        }

        binding.imgAdjustSelectGold.setOnClickListener {
            applyGoldFilter()
        }

        binding.imgAdjustSelectLomo.setOnClickListener {
            applyLomoFilter()
        }

        binding.imgAdjustSelectPink.setOnClickListener {
            applyPinkFilter()
        }

        binding.imgback.setOnClickListener {
            onBackPressed()
        }


        findViewById<LinearLayout>(R.id.lnrBrightness).setOnClickListener {
            currentMode = AdjustmentMode.BRIGHTNESS
            binding.skbseekbar.progress = adjustmentProgress[AdjustmentMode.BRIGHTNESS] ?: 0
            currentButtonId = "button1"
            setupSeekBarForButton(currentButtonId)
            Log.e("BUTTON", "initView: " + "lnrBrightness is click")
        }

        findViewById<LinearLayout>(R.id.lnrContrast).setOnClickListener {
            currentMode = AdjustmentMode.CONTRAST
            binding.skbseekbar.progress = adjustmentProgress[AdjustmentMode.BRIGHTNESS] ?: 0
            currentButtonId = "button2"
            setupSeekBarForButton(currentButtonId)

            Log.e("BUTTON", "initView: " + "lnrContrast is click")
        }

        findViewById<LinearLayout>(R.id.lnrWarmth).setOnClickListener {
            currentMode = AdjustmentMode.WARMTH
            currentButtonId = "button3"
            setupSeekBarForButton(currentButtonId)
            binding.skbseekbar.progress = adjustmentProgress[AdjustmentMode.BRIGHTNESS] ?: 0

            Log.e("BUTTON", "initView: " + "lnrWarmth is click")
        }

        findViewById<LinearLayout>(R.id.lnrSaturation).setOnClickListener {
            currentMode = AdjustmentMode.SATURATION
            currentButtonId = "button4"
            setupSeekBarForButton(currentButtonId)
            binding.skbseekbar.progress = adjustmentProgress[AdjustmentMode.BRIGHTNESS] ?: 0

            Log.e("BUTTON", "initView: " + "lnrSaturation is click")
        }

        findViewById<LinearLayout>(R.id.lnrFade).setOnClickListener {
            currentMode = AdjustmentMode.FADE
            currentButtonId = "button5"
            setupSeekBarForButton(currentButtonId)
            binding.skbseekbar.progress = adjustmentProgress[AdjustmentMode.BRIGHTNESS] ?: 0

            Log.e("BUTTON", "initView: " + "lnrFade is click")
        }

        findViewById<LinearLayout>(R.id.lnrHighlight).setOnClickListener {
            currentMode = AdjustmentMode.HIGHLIGHT
            currentButtonId = "button6"
            setupSeekBarForButton(currentButtonId)
            binding.skbseekbar.progress = adjustmentProgress[AdjustmentMode.BRIGHTNESS] ?: 0

            Log.e("BUTTON", "initView: " + "lnrHighlight is click")
        }

        findViewById<LinearLayout>(R.id.lnrShadow).setOnClickListener {
            currentMode = AdjustmentMode.SHADOW
            currentButtonId = "button7"
            setupSeekBarForButton(currentButtonId)
            binding.skbseekbar.progress = adjustmentProgress[AdjustmentMode.BRIGHTNESS] ?: 0

            Log.e("BUTTON", "initView: " + "lnrShadow is click")
        }

        findViewById<LinearLayout>(R.id.lnrTint).setOnClickListener {
            currentMode = AdjustmentMode.TINT
            currentButtonId = "button8"
            setupSeekBarForButton(currentButtonId)
            binding.skbseekbar.progress = adjustmentProgress[AdjustmentMode.BRIGHTNESS] ?: 0

            Log.e("BUTTON", "initView: " + "lnrTint is click")
        }

        findViewById<LinearLayout>(R.id.lnrHue).setOnClickListener {
            currentMode = AdjustmentMode.HUE
            currentButtonId = "button9"
            setupSeekBarForButton(currentButtonId)
            binding.skbseekbar.progress = adjustmentProgress[AdjustmentMode.BRIGHTNESS] ?: 0

            Log.e("BUTTON", "initView: " + "lnrHue is click")
        }

        findViewById<LinearLayout>(R.id.lnrGrain).setOnClickListener {
            currentMode = AdjustmentMode.GRAIN
            currentButtonId = "button10"
            setupSeekBarForButton(currentButtonId)
            binding.skbseekbar.progress = adjustmentProgress[AdjustmentMode.BRIGHTNESS] ?: 0

            Log.e("BUTTON", "initView: " + "lnrGrain is click")
        }

        binding.lnrFilter.setOnClickListener { toggleButton(binding.lnrFilter) }
        binding.lnrAdjust.setOnClickListener { toggleButton(binding.lnrAdjust) }

        binding.skbseekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                var buttonId: String? = null
                val contrast = (progress - 100) / 200f
                val warmMappedProgress = progress - 50
                val saturationMappedProgress = progress - 50
                val highlightMappedProgress = progress - 50 // Map 0-100 to -50 to 50
                val hueMappedProgress = progress - 50  // Maps SeekBar progress to range -50 to 50
                val hue = hueMappedProgress * 0.6f // Converts the mapped progress to a hue value in the range -180 to 180 degrees

                when (currentMode)
                {
                    AdjustmentMode.BRIGHTNESS -> applyBrightFilter(progress / 100f)
                    AdjustmentMode.CONTRAST -> getContrastFilter(contrast)
                    AdjustmentMode.WARMTH -> applyWarmFilter(warmMappedProgress)
                    AdjustmentMode.SATURATION -> applySaturationFilter(saturationMappedProgress)
                    AdjustmentMode.FADE -> applyFadeFilter(progress)
                    AdjustmentMode.HIGHLIGHT -> applyHighlightFilter(highlightMappedProgress)
                    AdjustmentMode.SHADOW -> applyShadowFilter(progress - 50)
//                    AdjustmentMode.TINT -> applyTintFilter((progress - 100) / 100f)
                    AdjustmentMode.HUE -> applyHueFilter(hue)
                    AdjustmentMode.GRAIN -> applyGrainFilter(progress)
                    else -> { /* No action */ }
                }

                adjustmentProgress[currentMode] = progress
                binding.txtProgress.text = progress.toString()

                // Save the current progress in SharedPreferences
                saveButtonProgress(currentButtonId, progress)

                binding.txtProgress.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                adjustmentProgress[currentMode] = binding.skbseekbar.progress

                saveCurrentProgress()
            }
        })

        binding.lnrTint.setOnClickListener {
            binding.hsvlayout.visibility = View.GONE

            binding.hsvColor.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        saveCurrentProgress()
    }

    private fun saveCurrentProgress() {
        adjustmentProgress[currentMode]?.let {
            saveButtonProgress(currentButtonId ?: "", it)
        }
    }

    private fun saveButtonProgress(buttonId: String, progress: Int) {
        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(buttonId, progress)
        editor.apply()
    }

    private fun loadButtonProgress(buttonId: String): Int {
        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        return sharedPreferences.getInt(buttonId, 0)  // Default to 0 if no value is found
    }

    private fun loadAllButtonProgress() {
        AdjustmentMode.values().forEachIndexed { index, mode ->
            adjustmentProgress[AdjustmentMode.BRIGHTNESS] = loadButtonProgress("button1")
            adjustmentProgress[AdjustmentMode.CONTRAST] = loadButtonProgress("button2")
            adjustmentProgress[AdjustmentMode.WARMTH] = loadButtonProgress("button3")
            adjustmentProgress[AdjustmentMode.SATURATION] = loadButtonProgress("button4")
            adjustmentProgress[AdjustmentMode.FADE] = loadButtonProgress("button5")
            adjustmentProgress[AdjustmentMode.HIGHLIGHT] = loadButtonProgress("button6")
            adjustmentProgress[AdjustmentMode.SHADOW] = loadButtonProgress("button7")
            adjustmentProgress[AdjustmentMode.TINT] = loadButtonProgress("button8")
            adjustmentProgress[AdjustmentMode.HUE] = loadButtonProgress("button9")
            adjustmentProgress[AdjustmentMode.GRAIN] = loadButtonProgress("button10")
        }
    }

    private fun setupSeekBarForButton(buttonId: String) {
        // Update SeekBar progress based on the saved progress for the buttonId
        binding.skbseekbar.progress = adjustmentProgress[currentMode] ?: 0
    }

    override fun updateUIForSelectedLanguage() {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

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

        binding.txtFilter.text = resources.getString(R.string.Filter)
        binding.txtAdjust.text = resources.getString(R.string.Adjust)

        binding.txtBrightness.text = resources.getString(R.string.Brightness)
        binding.txtContrast.text = resources.getString(R.string.Contrast)
        binding.txtWarmth.text = resources.getString(R.string.Warmth)
        binding.txtSaturation.text = resources.getString(R.string.Saturation)
        binding.txtFade.text = resources.getString(R.string.Fade)
        binding.txtHighlight.text = resources.getString(R.string.Highlight)
        binding.txtShadow.text = resources.getString(R.string.Shadow)
        binding.txtTint.text = resources.getString(R.string.Tint)
        binding.txtHue.text = resources.getString(R.string.Hue)
        binding.txtGrain.text = resources.getString(R.string.Grain)
    }

    private fun toggleButton(button: LinearLayout?)
    {
        if (selectedButton != button)
        {
            // Deselect previously selected button
            selectedButton?.let {
                it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
                val prevTextView = it.findViewById<TextView>(R.id.txtFilter) ?: it.findViewById<TextView>(R.id.txtAdjust)
                prevTextView?.setTextColor(ContextCompat.getColor(this, R.color.black))
            }

            // Toggle the state of the button
            selectedButton = button
            selectedButton?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            val newTextView = button?.findViewById<TextView>(R.id.txtFilter) ?: button?.findViewById<TextView>(R.id.txtAdjust)
            newTextView?.setTextColor(ContextCompat.getColor(this, R.color.white))

            // Handle actions based on selected button (if needed)
            when (selectedButton?.id)
            {
                R.id.lnrFilter -> {
                    binding.rtllayouts.visibility = View.VISIBLE

                    binding.lnrskbseek.visibility = View.GONE
                    binding.hsvlayout.visibility = View.GONE
                }

                R.id.lnrAdjust -> {
                    binding.lnrskbseek.visibility = View.VISIBLE
                    binding.hsvlayout.visibility = View.VISIBLE

                    binding.rtllayouts.visibility = View.GONE
                    binding.hsvColor.visibility = View.GONE
                }
            }
        }
    }

    override fun onResume()
    {
        super.onResume()
        // Select a specific button in onResume (example: lnrFilter)
//        val lnrFilter = findViewById<LinearLayout>(R.id.lnrFilter)
        toggleButton(binding.lnrFilter)
    }

    private fun logErrorAndFinish(message: String)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish() // Close the activity or handle it appropriately
    }


    private class FetchImagesTask(val context: EditActivity, val callback: (List<ImageItem>) ->
    Unit) : AsyncTask<Void, Void, List<ImageItem>>()
    {
        override fun doInBackground(vararg params: Void?): List<ImageItem>
        {
            val images = mutableListOf<ImageItem>()
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.Media._ID)
            val cursor = context.contentResolver.query(
                uri, projection,null,null,MediaStore.Images.Media.DATE_ADDED + " DESC"
            )

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (it.moveToNext())
                {
                    val id = it.getLong(idColumn)
                    val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    images.add(ImageItem(imageUri))
                }
            }
            return images
        }

        override fun onPostExecute(result: List<ImageItem>)
        {
            callback(result)
        }
    }

    private fun applyBrightFilter()
    {
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
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyStoryFilter()
    {
        // Apply contrast filter
        val contrast = 1.5f
        val contrastMatrix = ColorMatrix().apply { setSaturation(contrast) }

        val colorFilter = ColorMatrixColorFilter(contrastMatrix)
        val canvas = Canvas(filteredBitmap)
        val paint = Paint()
        paint.colorFilter = colorFilter
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyNaturalFilter()
    {
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

        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyWarmFilter()
    {
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

        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyDewFilter(imageUri: Uri)
    {
        val filteredBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

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
        val paint = Paint().apply { colorFilter = ColorMatrixColorFilter(colorMatrix) }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyGoldFilter() {
        // Create a copy of the original bitmap
        val filteredBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

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
        val paint = Paint().apply { colorFilter = ColorMatrixColorFilter(colorMatrix) }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyLomoFilter() {
        // Create a copy of the original bitmap
        val filteredBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

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
        val paint = Paint().apply { colorFilter = ColorMatrixColorFilter(colorMatrix) }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyPinkFilter() {
        // Create a copy of the original bitmap
        val filteredBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

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
        val paint = Paint().apply { colorFilter = ColorMatrixColorFilter(colorMatrix) }

        // Draw the original bitmap onto the filteredBitmap using Canvas
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Display the filtered image on ImageView
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    fun onResetButtonClick(view: View) {
        resetToOriginalImage()
    }

    private fun resetToOriginalImage() {
        // Ensure originalBitmap is initialized and not null
        originalBitmap.let { binding.imgAdjustSelectImage.setImageBitmap(it) }
    }

    fun onButtonClick(view: View) {}


    private fun applyBrightFilter(intensity: Float) {
        // Map SeekBar intensity to a brightness scale (e.g., 0.5x to 1.5x)
        val brightness = 0.5f + intensity // Adjust the factor as needed

        // Create a color matrix for brightness adjustment
        val colorMatrix = ColorMatrix().apply { setScale(brightness, brightness, brightness, 1f) }

        // Create a color filter with the color matrix
        val colorFilter = ColorMatrixColorFilter(colorMatrix)

        // Apply the color filter to the filtered bitmap
        val filteredBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)
        val canvas = Canvas(filteredBitmap)
        val paint = Paint()
        paint.colorFilter = colorFilter
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Update the ImageView with the filtered bitmap
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun applyContrastFilter(contrast: Float) {
        val filter = getContrastFilter(contrast)

        // Apply the color filter to the filtered bitmap
        val filteredBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)
        val canvas = Canvas(filteredBitmap)
        val paint = Paint().apply { colorFilter = filter }
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Update the ImageView with the filtered bitmap
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun getContrastFilter(contrast: Float): ColorMatrixColorFilter {
        val cm = ColorMatrix()
        val scale = contrast + 1.0f
        val translate = (-0.5f * scale + 0.5f) * 255.0f

        cm.set(
            arrayOf(
                scale, 0f, 0f, 0f, translate,
                0f, scale, 0f, 0f, translate,
                0f, 0f, scale, 0f, translate,
                0f, 0f, 0f, 1f, 0f
            ).toFloatArray()
        )

        return ColorMatrixColorFilter(cm)
    }

    private fun applyWarmFilter(intensity: Int) {
        // Calculate intensity based on mapped SeekBar progress
        val normalizedIntensity = intensity / 50.0f // Maps -50 to 50 to -1 to 1

        // Create a new bitmap to hold the filtered image
        val filteredBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

        val canvas = Canvas(filteredBitmap)
        val paint = Paint()

        // Create a ColorMatrix to adjust the image color
        val colorMatrix = ColorMatrix().apply {
            val warmRed = 1.0f + normalizedIntensity * 0.2f
            val warmGreen = 1.0f + normalizedIntensity * 0.1f
            val warmBlue = 1.0f - normalizedIntensity * 0.1f

            set(
                floatArrayOf(
                    warmRed, 0.0f, 0.0f, 0.0f, 0.0f,
                    0.0f, warmGreen, 0.0f, 0.0f, 0.0f,
                    0.0f, 0.0f, warmBlue, 0.0f, 0.0f,
                    0.0f, 0.0f, 0.0f, 1.0f, 0.0f
                )
            )
        }

        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Update the ImageView with the filtered bitmap
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }


    private fun applySaturationFilter(saturation: Int) {
        // Normalize the saturation value to range from 0.0f to 2.0f
        val normalizedSaturation = (saturation / 50.0f) + 1.0f

        // Create a new bitmap to hold the filtered image
        val filteredBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

        val canvas = Canvas(filteredBitmap)
        val paint = Paint()

        // Create a ColorMatrix to adjust saturation
        val colorMatrix = ColorMatrix().apply { setSaturation(normalizedSaturation) }

        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Update the ImageView with the filtered bitmap
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }


    private fun applyFadeFilter(progress: Int) {
        // Map the progress to an alpha value (0 to 255)
        val alpha = (progress * 255) / 350

        // Create a Paint object with a ColorFilter for fading effect
        val paint = Paint().apply {
            colorFilter = PorterDuffColorFilter(Color.argb(alpha, 255, 255, 255), PorterDuff.Mode.SRC_OVER)
        }

        // Create a new bitmap to hold the faded image
        val fadedBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

        val canvas = Canvas(fadedBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Update the ImageView with the faded bitmap
        binding.imgAdjustSelectImage.setImageBitmap(fadedBitmap)
    }


//    private fun applyHighlightFilter(progress: Int) {
//        val factor = 1.0f + (progress / 100.0f)
//
//        // Run the image processing in a background thread
//        GlobalScope.launch(Dispatchers.IO) {
//            if (!::originalBitmap.isInitialized) {
//                Log.e("Filter", "Original bitmap is not initialized.")
//                return@launch
//            }
//
//            val filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
//            val width = filteredBitmap.width
//            val height = filteredBitmap.height
//
//            for (y in 0 until height) {
//                for (x in 0 until width) {
//                    val pixel = filteredBitmap.getPixel(x, y)
//
//                    val red = Color.red(pixel)
//                    val green = Color.green(pixel)
//                    val blue = Color.blue(pixel)
//
//                    val newRed = (red * factor).coerceAtMost(255.0f).toInt()
//                    val newGreen = (green * factor).coerceAtMost(255.0f).toInt()
//                    val newBlue = (blue * factor).coerceAtMost(255.0f).toInt()
//
//                    filteredBitmap.setPixel(x, y, Color.argb(Color.alpha(pixel), newRed, newGreen, newBlue))
//                }
//            }
//
//            // Switch back to the main thread to update the UI
//            withContext(Dispatchers.Main) {
//                binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
//            }
//        }
//    }

    private fun applyHighlightFilter(progress: Int) {
        // Ensure the progress is within the expected range
        val highlightFactor = progress / 300.0f // Adjust factor to scale as needed

        // Create a new bitmap with the same dimensions as the original
        val brightBitmap =
            Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)
        val canvasBright = Canvas(brightBitmap)
        val paintBright = Paint().apply {
            // Apply a color filter to create a brightened version of the original image
            val colorMatrix = ColorMatrix().apply {
                set(
                    floatArrayOf(
                        1.0f, 0.0f, 0.0f, 0.0f, highlightFactor * 255.0f, // Red
                        0.0f, 1.0f, 0.0f, 0.0f, highlightFactor * 255.0f, // Green
                        0.0f, 0.0f, 1.0f, 0.0f, highlightFactor * 255.0f, // Blue
                        0.0f, 0.0f, 0.0f, 1.0f, 0.0f // Alpha
                    )
                )
            }
            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }

        // Draw the brightened bitmap
        canvasBright.drawBitmap(originalBitmap, 0f, 0f, paintBright)

        // Create a new bitmap to hold the final result
        val finalBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)
        val canvasFinal = Canvas(finalBitmap)
        val paintFinal = Paint()

        // Blend the original image and the brightened image
        val blendMode = PorterDuff.Mode.SCREEN // Use SCREEN mode to blend for highlights
        val blendFilter = PorterDuffColorFilter(Color.TRANSPARENT, blendMode)
        paintFinal.colorFilter = blendFilter
        canvasFinal.drawBitmap(originalBitmap, 0f, 0f, paintFinal)
        canvasFinal.drawBitmap(brightBitmap, 0f, 0f, paintFinal)

        // Update the ImageView with the final bitmap
        binding.imgAdjustSelectImage.setImageBitmap(finalBitmap)
    }


    private inner class HighlightFilterTask(private val progress: Int) :
        AsyncTask<Void, Void, Bitmap>() {
        override fun doInBackground(vararg params: Void?): Bitmap {
            val factor = 1.0f + (progress / 100.0f)

            if (!::originalBitmap.isInitialized) {
                Log.e("Filter", "Original bitmap is not initialized.")
                return originalBitmap
            }

            val filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
            val width = filteredBitmap.width
            val height = filteredBitmap.height

            for (y in 0 until height) {
                for (x in 0 until width) {
                    val pixel = filteredBitmap.getPixel(x, y)

                    val red = Color.red(pixel)
                    val green = Color.green(pixel)
                    val blue = Color.blue(pixel)

                    val newRed = (red * factor).coerceAtMost(255.0f).toInt()
                    val newGreen = (green * factor).coerceAtMost(255.0f).toInt()
                    val newBlue = (blue * factor).coerceAtMost(255.0f).toInt()

                    filteredBitmap.setPixel(x, y, Color.argb(Color.alpha(pixel), newRed, newGreen, newBlue))
                }
            }

            return filteredBitmap
        }

        override fun onPostExecute(result: Bitmap) {
            binding.imgAdjustSelectImage.setImageBitmap(result)
        }
    }


    private fun applyShadowFilter(progress: Int) {
        // Calculate intensity and shadow color based on SeekBar progress
        val intensity = Math.abs(progress) / 300.0f // Scale to 0.0 to 1.0 range
        val shadowColor: Int

        // Determine the shadow color
        shadowColor = if (progress >= 0) {
            // Apply white shadow when progress is 0 to 50
            Color.argb((intensity * 255).toInt(), 255, 255, 255)
        } else {
            // Apply black shadow when progress is -50 to 0
            Color.argb((intensity * 255).toInt(), 0, 0, 0)
        }

        // Create a bitmap with shadow effect
        val shadowBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, Bitmap.Config.ARGB_8888)
        val shadowCanvas = Canvas(shadowBitmap)
        val paint = Paint().apply { colorFilter = PorterDuffColorFilter(shadowColor, PorterDuff.Mode.SRC_OVER) }

        // Draw the original bitmap onto the shadow canvas with the shadow color
        shadowCanvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Create a final bitmap with original image and shadow effect
        val finalBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, Bitmap.Config.ARGB_8888)
        val finalCanvas = Canvas(finalBitmap)

        // Draw the original bitmap
        finalCanvas.drawBitmap(originalBitmap, 0f, 0f, null)

        // Draw the shadow bitmap over the original image
        finalCanvas.drawBitmap(shadowBitmap, 0f, 0f, null)

        // Update the ImageView with the final bitmap
        binding.imgAdjustSelectImage.setImageBitmap(finalBitmap)

        // Clean up resources
        paint.reset()
    }

    private fun applyHueFilter(hue: Float) {
        val cosVal = cos(Math.toRadians(hue.toDouble())).toFloat()
        val sinVal = sin(Math.toRadians(hue.toDouble())).toFloat()

        val lumR = 0.213f
        val lumG = 0.715f
        val lumB = 0.072f

        val colorMatrix = ColorMatrix(
            floatArrayOf(
                lumR + cosVal * (1 - lumR) + sinVal * 0f,
                lumG + cosVal * -lumG + sinVal * 0.143f,
                lumB + cosVal * -lumB + sinVal * -0.283f,
                0f, 0f,
                lumR + cosVal * -lumR + sinVal * -0.213f,
                lumG + cosVal * (1 - lumG) + sinVal * 0.14f,
                lumB + cosVal * -lumB + sinVal * 0.283f,
                0f, 0f,
                lumR + cosVal * -lumR + sinVal * 0.787f,
                lumG + cosVal * -lumG + sinVal * -0.715f,
                lumB + cosVal * (1 - lumB) + sinVal * 0f,
                0f, 0f, 0f, 0f, 0f, 1f, 0f
            )
        )

        val paint = Paint().apply { colorFilter = ColorMatrixColorFilter(colorMatrix) }
        val filteredBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun createHueMatrix(degrees: Float): ColorMatrix {
        val radians = Math.toRadians(degrees.toDouble())
        val cosVal = cos(radians).toFloat()
        val sinVal = sin(radians).toFloat()

        val lumR = 0.213f
        val lumG = 0.715f
        val lumB = 0.072f

        val hueMatrix = ColorMatrix(
            floatArrayOf(
                lumR + cosVal * (1 - lumR) + sinVal * -lumR,
                lumG + cosVal * -lumG + sinVal * -lumG,
                lumB + cosVal * -lumB + sinVal * (1 - lumB), 0f, 0f,
                lumR + cosVal * -lumR + sinVal * 0.143f,
                lumG + cosVal * (1 - lumG) + sinVal * 0.140f,
                lumB + cosVal * -lumB + sinVal * -0.283f, 0f, 0f,
                lumR + cosVal * -lumR + sinVal * -(1 - lumR),
                lumG + cosVal * -lumG + sinVal * lumG,
                lumB + cosVal * (1 - lumB) + sinVal * lumB, 0f, 0f, 0f, 0f, 0f, 1f, 0f
            )
        )
        return hueMatrix
    }

    private fun applyGrainFilter(intensity: Int) {
        if (!::originalBitmap.isInitialized) {
            Log.e("Filter", "Original bitmap is not initialized.")
            return
        }

        // Create a new bitmap to hold the filtered image
        val filteredBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, originalBitmap.config)

        // Create a Canvas and Paint object
        val canvas = Canvas(filteredBitmap)
        val paint = Paint()

        // Draw the original bitmap onto the canvas
        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        // Create a noise bitmap
        val noiseBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, Bitmap.Config.ARGB_8888)

        // Generate noise pixels
        val noisePixels = IntArray(originalBitmap.width * originalBitmap.height)
        val noiseIntensity = intensity / 200.0f  // Convert intensity to a float between 0 and 1

        val random = Random()
        for (i in noisePixels.indices) {
            val noise = (random.nextFloat() * 255 * noiseIntensity).toInt()
            noisePixels[i] = Color.argb(128, noise, noise, noise) // Semi-transparent noise
        }

        noiseBitmap.setPixels(
            noisePixels, 0, originalBitmap.width, 0, 0, originalBitmap.width, originalBitmap.height
        )

        // Create a paint object to blend the noise bitmap onto the original image
        val blendPaint = Paint().apply {
            alpha = (255 * noiseIntensity).toInt() // Adjust the alpha based on intensity
        }

        // Overlay the noise bitmap on the original image
        canvas.drawBitmap(noiseBitmap, 0f, 0f, blendPaint)

        // Update the ImageView with the filtered bitmap
        binding.imgAdjustSelectImage.setImageBitmap(filteredBitmap)
    }

    private fun setColor(color: Int, selectedView: View)
    {
        binding.imgAdjustSelectImage.setColorFilter(color)
        resetColorSelection()
        selectedView.setBackgroundResource(R.drawable.ring)
    }

    private fun resetColorSelection()
    {
        binding.civRed.setBackgroundResource(0)
        binding.civOrange.setBackgroundResource(0)
        binding.civYellow.setBackgroundResource(0)
        binding.civGreen.setBackgroundResource(0)
        binding.civLightBlue.setBackgroundResource(0)
        binding.civDarkBlue.setBackgroundResource(0)
        binding.civPurple.setBackgroundResource(0)
        binding.lnrColor.setBackgroundResource(0)
    }

    private fun openColorPickerDialog()
    {
        val colorPickerDialog = AmbilWarnaDialog(this, mDefaultColor,
            object : AmbilWarnaDialog.OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog?) {}

                override fun onOk(dialog: AmbilWarnaDialog?, color: Int)
                {
                    mDefaultColor = color

                    setColor(color, binding.lnrColor)

                    val customBackgroundDrawable = ContextCompat.getDrawable(this@Adjust_Activity,
                        R.drawable.custom_background)
                    customBackgroundDrawable?.setColorFilter(mDefaultColor, PorterDuff.Mode.SRC_ATOP)
                    binding.lnrColor.background = customBackgroundDrawable
                }
            })
        colorPickerDialog.show()
    }
}