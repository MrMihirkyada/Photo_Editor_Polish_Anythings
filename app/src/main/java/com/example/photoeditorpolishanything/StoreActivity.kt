package com.example.photoeditorpolishanything

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.photoeditorpolishanything.Adapter.StoreTabAdapter
import com.example.photoeditorpolishanything.databinding.ActivityStoreBinding
import com.google.android.material.tabs.TabLayout

class StoreActivity : AppCompatActivity() {


    lateinit var binding : ActivityStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.lightBlack)
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        initview()
    }

    private fun initview()
    {

        binding.imgback.setOnClickListener { onBackPressed()}

        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.sticker))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.backgrouond))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Light FX"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.aa))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.store_filter))

        val Myadapter = StoreTabAdapter(supportFragmentManager)
        binding.viewpager.adapter = Myadapter

        binding.viewpager.addOnPageChangeListener(object : TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout){})

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                if(tab != null)
                {
                    binding.viewpager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?)
            {

            }

            override fun onTabReselected(tab: TabLayout.Tab?)
            {

            }

        })
    }
}