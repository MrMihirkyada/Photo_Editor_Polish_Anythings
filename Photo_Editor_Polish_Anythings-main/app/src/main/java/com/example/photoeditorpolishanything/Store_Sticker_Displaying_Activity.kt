package com.example.photoeditorpolishanything

import android.app.ActivityOptions
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.photoeditorpolishanything.databinding.ActivityStoreStickerDisplayingBinding

class Store_Sticker_Displaying_Activity : AppCompatActivity() {

    lateinit var binding : ActivityStoreStickerDisplayingBinding

    companion object{
        var contexte : Context? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreStickerDisplayingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)

        }

        initView()
    }

    private fun initView() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_out_up, R.anim.slide_in_up)
        finishAfterTransition()    }
}