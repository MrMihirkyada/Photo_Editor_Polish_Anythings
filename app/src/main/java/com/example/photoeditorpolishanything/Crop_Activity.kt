package com.example.photoeditorpolishanything

import LocaleHelper
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.canhub.cropper.CropImageView
import com.example.photoeditorpolishanything.databinding.ActivityCropBinding

class Crop_Activity : BaseActivity() {

    lateinit var binding: ActivityCropBinding
    private var selectedButton: LinearLayout? = null
    var imageUriString: String? = null
    private lateinit var imageBitmap: Bitmap
    private var currentRotation = 0f
    private var isFlippedHorizontal = false
    private var isFlippedVertical = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)

        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        initView()
    }

    override fun updateUIForSelectedLanguage() {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.txtRotate.text = resources.getString(R.string.Rotate)
        binding.txtPerspective.text = resources.getString(R.string.Perspective)
        binding.txtCustom.text = resources.getString(R.string.Custom)
        binding.txtlG11.text = resources.getString(R.string.lG11)
        binding.txtlG45.text = resources.getString(R.string.lG45)
        binding.txtCover.text = resources.getString(R.string.Cover)
        binding.txtDevice.text = resources.getString(R.string.Device)
        binding.txtPost.text = resources.getString(R.string.Post)
        binding.txtHeader.text = resources.getString(R.string.Header)
        binding.txtLeft90.text = resources.getString(R.string.Left90)
        binding.txtRight90.text = resources.getString(R.string.Right90)
        binding.txtHorizontal.text = resources.getString(R.string.Horizontal)
        binding.txtVertical.text = resources.getString(R.string.Vertical)
        binding.txtHorizontalP.text = resources.getString(R.string.Horizontal)
        binding.txtVerticalP.text = resources.getString(R.string.Vertical)
    }

    private fun initView() {

        binding.imgback.setOnClickListener {
            onBackPressed()
        }

        findViewById<LinearLayout>(R.id.lnrCustom).setOnClickListener {
//            binding.imgCropSelectImage.setAspectRatio(1, 3)
            binding.imgCropSelectImage.setFixedAspectRatio(false)
        }

        findViewById<LinearLayout>(R.id.lnrLG11).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                1, 1
            )
        }

        findViewById<LinearLayout>(R.id.lnrLG45).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                4, 5
            )
        }
        findViewById<ImageView>(R.id.img54).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                5, 4
            )
        }
        findViewById<ImageView>(R.id.img34).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                3, 4
            )
        }
        findViewById<ImageView>(R.id.img43).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                4, 3
            )
        }
        findViewById<ImageView>(R.id.imgA4).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                2, 3
            )
        }
        // Add listeners for the second set of ratios
        findViewById<LinearLayout>(R.id.lnrCover).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                16, 9
            )
        }
        findViewById<LinearLayout>(R.id.lnrDevice).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                9, 16
            )
        }
        findViewById<ImageView>(R.id.img23).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                2, 3
            )
        }
        findViewById<ImageView>(R.id.img32).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                3, 2
            )
        }
        findViewById<ImageView>(R.id.img12).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                1, 2
            )
        }
        findViewById<LinearLayout>(R.id.lnrPost).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                16, 9
            )
        }
        findViewById<LinearLayout>(R.id.lnrHeader).setOnClickListener {
            binding.imgCropSelectImage.setAspectRatio(
                3, 1
            )
        }

        imageUriString = intent.getStringExtra("selected_image_uri")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            val imageView = findViewById<CropImageView>(R.id.imgCropSelectImage)

            if (imageView != null) {
                try {
                    imageUriString?.let { uri ->
                        Glide.with(this).asBitmap().load(imageUri)
                            .into(object : CustomTarget<Bitmap>() {
                                override fun onResourceReady(
                                    resource: Bitmap, transition: Transition<in Bitmap>?
                                ) {
                                    // Set the Bitmap into CropImageView
                                    binding.imgCropSelectImage.setImageBitmap(resource)
                                    imageBitmap = resource // Initialize imageBitmap here
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {
                                    // Handle any cleanup here
                                }
                            })
                    }
                } catch (e: Exception) {
                    logErrorAndFinish("Glide error: ${e.message}")
                }
            } else {
                logErrorAndFinish("ImageView not found in layout")
            }
        } else {
            logErrorAndFinish("Image URI string is null")
        }

        binding.lnrHorizontal1.setOnClickListener {

            binding.RsvScale.setOnSelectedIndexChangeListener(object :
                RulerScaleView.OnSelectedIndexChangeListener {
                override fun onSelectedIndexChange(index: Int) {
                    updateCropImageViewRotation(index)
                }
            })
        }

        binding.lnrVertical1.setOnClickListener {

            binding.RsvScale.setOnSelectedIndexChangeListener(object :
                RulerScaleView.OnSelectedIndexChangeListener {
                override fun onSelectedIndexChange(index: Int) {
                    updateCropImageViewRotationAndVerticalScale(index)
                }
            })
        }

        binding.RsvScale.setOnClickListener {
            binding.RsvScale.setSelectedIndex(0) // Set the selected index to 10 (adjust as needed)
        }

        binding.lnrRight90.setOnClickListener {
            rotateImageRight()
        }

        binding.lnrLeft90.setOnClickListener {
            rotateImageLeft()
        }

        binding.lnrHorizontal.setOnClickListener {
            flipImageHorizontally()
        }

        binding.lnrVertical.setOnClickListener {
            flipImageVertically()
        }

        binding.lnrRotate.setOnClickListener { toggleButton(binding.lnrRotate) }
        binding.lnrPerspective.setOnClickListener { toggleButton(binding.lnrPerspective) }
    }


    private fun updateCropImageViewRotation(index: Int) {
        val rotationAngle = (index * 0.9f).coerceIn(-45f, 45f)
        binding.imgCropSelectImage.setAspectRatio(1, 1)
        binding.imgCropSelectImage.rotation = rotationAngle // No cast needed
    }


    private fun updateCropImageViewRotationAndVerticalScale(index: Int) {
        // Vertical rotation
        val verticalRotationAngle = (index * 0.9f).coerceIn(-45f, 45f)
        binding.imgCropSelectImage.setAspectRatio(1, 1)
        binding.imgCropSelectImage.rotationX = verticalRotationAngle
    }

    private fun rotateImageRight() {
        currentRotation += 90f // Increment the rotation angle by 90 degrees
        binding.imgCropSelectImage.animate().rotation(currentRotation)
            .setDuration(100) // Duration of the rotation animation
            .start()
    }

    private fun rotateImageLeft() {
        currentRotation -= 90f // Decrement the rotation angle by 90 degrees
        binding.imgCropSelectImage.animate().rotation(currentRotation)
            .setDuration(100) // Duration of the rotation animation
            .start()
    }

    private fun flipImageHorizontally() {
        val animator = ObjectAnimator.ofFloat(
            binding.imgCropSelectImage, "scaleX", if (isFlippedHorizontal) 1f else -1f
        )
        animator.duration = 100
        animator.start()
        isFlippedHorizontal = !isFlippedHorizontal
    }


    private fun flipImageVertically() {
        val animator = ObjectAnimator.ofFloat(
            binding.imgCropSelectImage, "scaleY", if (isFlippedVertical) 1f else -1f
        )
        animator.duration = 100
        animator.start()
        isFlippedVertical = !isFlippedVertical
    }

    private fun toggleButton(button: LinearLayout?) {

        if (selectedButton != button) {
            // Deselect previously selected button
            selectedButton?.let {
                it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
                val prevTextView = it.findViewById<TextView>(R.id.txtRotate)
                    ?: it.findViewById<TextView>(R.id.txtPerspective)
                prevTextView?.setTextColor(ContextCompat.getColor(this, R.color.black))
            }

            // Toggle the state of the button
            selectedButton = button
            selectedButton?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            val newTextView = button?.findViewById<TextView>(R.id.txtRotate)
                ?: button?.findViewById<TextView>(R.id.txtPerspective)
            newTextView?.setTextColor(ContextCompat.getColor(this, R.color.white))

            // Handle actions based on selected button (if needed)
            when (selectedButton?.id) {
                R.id.lnrRotate -> {
                    binding.lnrlayout.visibility = View.VISIBLE

                    binding.lnrOrientation.visibility = View.GONE
                }

                R.id.lnrPerspective -> {
                    binding.lnrOrientation.visibility = View.VISIBLE

                    binding.lnrlayout.visibility = View.GONE
                }
            }
        }
    }

    override fun onResume()
    {
        super.onResume()
        // Select a specific button in onResume (example: lnrFilter)
        toggleButton(binding.lnrRotate)
    }

    private fun logErrorAndFinish(message: String)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish() // Close the activity or handle it appropriately
    }
}
