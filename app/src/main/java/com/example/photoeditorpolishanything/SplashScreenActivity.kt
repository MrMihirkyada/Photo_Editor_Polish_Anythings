package com.example.photoeditorpolishanything

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.photoeditorpolishanything.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity()
{
    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.black)
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        initview()
    }

    private fun initview()
    {
        Handler().postDelayed({

            val i = Intent(this@SplashScreenActivity, DashboardActivity::class.java)
            startActivity(i)
            finish()

        },2000)
    }
}