package com.example.photoeditorpolishanything

import LocaleHelper
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.photoeditorpolishanything.Adapter.TabAdapter
import com.example.photoeditorpolishanything.Fragment.RatioFragment
import com.example.photoeditorpolishanything.databinding.ActivityLayoutBinding
import com.google.android.material.tabs.TabLayout

class LayoutActivity : BaseActivity(), RatioFragment.OnLayoutSelectedListener {

    lateinit var binding: ActivityLayoutBinding

    var imageUriString: String? = null

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBinding.inflate(layoutInflater)
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

        binding.tabLayout.getTabAt(0)?.text = resources.getString(R.string.tab_ratio)
        binding.tabLayout.getTabAt(1)?.text = resources.getString(R.string.tab_layout)
        binding.tabLayout.getTabAt(2)?.text = resources.getString(R.string.tab_margin)
        binding.tabLayout.getTabAt(3)?.text = resources.getString(R.string.tab_border)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {

        val ratioFragment = RatioFragment.newInstance(imageUriString)

        ratioFragment.listener = this

        binding.imgback.setOnClickListener {
            onBackPressed()
        }

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.tab_ratio))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.tab_layout))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.tab_margin))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.tab_border))

        binding.viewpager.addOnPageChangeListener(object :
            TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout) {})

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewpager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        imageUriString = intent.getStringExtra("selected_image_uri")

        loadImageIntoCropView()
        val myAdapter = TabAdapter(supportFragmentManager, imageUriString!!)
        binding.viewpager.adapter = myAdapter

    }


    override fun onLayoutSelected(aspectX: Int, aspectY: Int) {
        if (aspectX == 0 && aspectY == 0) {
            // Free cropping
            binding.imglayoutSelectImage.setFixedAspectRatio(false)
        } else {
            binding.imglayoutSelectImage.setAspectRatio(aspectX, aspectY)
        }
        Log.e("LayoutActivity", "Aspect Ratio Set: $aspectX:$aspectY")
    }


    private fun loadImageIntoCropView() {
        imageUriString?.let { uri ->
            Glide.with(this)
                .asBitmap()
                .load(imageUriString)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        // Set the Bitmap into CropImageView
                        binding.imglayoutSelectImage.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Handle any cleanup here
                    }
                })
        }
    }

    private fun loadImage(uri: String?, width: Int, height: Int) {
        uri?.let {
            Glide.with(this)
                .asBitmap()
                .override(width, height)
                .load(it)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.imglayoutSelectImage.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Placeholder cleanup if needed
                    }
                })
        }
    }
}
