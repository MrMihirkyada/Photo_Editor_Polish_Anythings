package com.example.photoeditorpolishanything

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.photoeditorpolishanything.databinding.ActivityMakeupBinding

class Makeup_Activity : AppCompatActivity() {

    lateinit var binding: ActivityMakeupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeupBinding.inflate(layoutInflater)
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

    private fun initView() {

        binding.imgcross.setOnClickListener {
            onBackPressed()
        }

        binding.lnrSetting.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE
            binding.lnrskbSettingvisibility.visibility = View.VISIBLE
            binding.lnrSettingvisibility.visibility = View.VISIBLE
        }

        binding.lnrLipColor.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE
            binding.lnrskbvisibilityLip.visibility = View.VISIBLE
            binding.lnrvisibilityLipcolor.visibility = View.VISIBLE
        }

        binding.lnrBlush.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE
            binding.lnrskbvisibilityBlush.visibility = View.VISIBLE
            binding.lnrvisibilityBlush.visibility = View.VISIBLE
        }

        binding.lnrContour.setOnClickListener {
            binding.lnrvisibility.visibility = View.GONE
            binding.lnrskbvisibilityContour.visibility = View.VISIBLE
            binding.lnrvisibilityContour.visibility = View.VISIBLE
        }

    }

    override fun onBackPressed() {
        if (binding.lnrskbSettingvisibility.visibility == View.VISIBLE ||
            binding.lnrSettingvisibility.visibility == View.VISIBLE ||
            binding.lnrskbvisibilityLip.visibility == View.VISIBLE ||
            binding.lnrvisibilityLipcolor.visibility == View.VISIBLE ||
            binding.lnrskbvisibilityBlush.visibility == View.VISIBLE ||
            binding.lnrvisibilityBlush.visibility == View.VISIBLE ||
            binding.lnrskbvisibilityContour.visibility == View.VISIBLE ||
            binding.lnrvisibilityContour.visibility == View.VISIBLE
        ) {

            // Hide all the views
            binding.lnrskbSettingvisibility.visibility = View.GONE
            binding.lnrSettingvisibility.visibility = View.GONE
            binding.lnrskbvisibilityLip.visibility = View.GONE
            binding.lnrvisibilityLipcolor.visibility = View.GONE
            binding.lnrskbvisibilityBlush.visibility = View.GONE
            binding.lnrvisibilityBlush.visibility = View.GONE
            binding.lnrskbvisibilityContour.visibility = View.GONE
            binding.lnrvisibilityContour.visibility = View.GONE

            // Show the main view
            binding.lnrvisibility.visibility = View.VISIBLE
        } else {
            // If all views are already hidden, proceed with the default back button behavior
            super.onBackPressed()
        }
    }

}