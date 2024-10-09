package com.example.photoeditorpolishanything

import LocaleHelper
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.photoeditorpolishanything.databinding.ActivityTextBinding

class Text_Activity : BaseActivity() {

    lateinit var binding: ActivityTextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        initView()
    }

    private fun initView()
    {
        binding.imgback.setOnClickListener {
            onBackPressed()
        }

        val imageUriString = intent.getStringExtra("selected_image_uri")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            val imageView = findViewById<ImageView>(R.id.imgTextSelectImage)

            if (imageView != null) {
                try {
                    Glide.with(this)
                        .load(imageUri)
                        .into(imageView)
                } catch (e: Exception) {
                    logErrorAndFinish("Glide error: ${e.message}")
                }
            } else {
                logErrorAndFinish("ImageView not found in layout")
            }
        } else {
            logErrorAndFinish("Image URI string is null")
        }
    }

    override fun updateUIForSelectedLanguage() {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.txtKeyboard.text = resources.getString(R.string.Keyboard)
        binding.txtFont.text = resources.getString(R.string.Font)
        binding.txtStyle.text = resources.getString(R.string.Styles)
        binding.txtPreset.text = resources.getString(R.string.Preset)
        binding.txtAdd.text = resources.getString(R.string.Add)
    }

    private fun logErrorAndFinish(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish() // Close the activity or handle it appropriately
    }
}