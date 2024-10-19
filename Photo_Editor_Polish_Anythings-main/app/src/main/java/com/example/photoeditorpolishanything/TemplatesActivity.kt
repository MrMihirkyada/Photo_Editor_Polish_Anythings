package com.example.photoeditorpolishanything

import LocaleHelper
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.photoeditorpolishanything.Adapter.CollageTemplateAdapter
import com.example.photoeditorpolishanything.Adapter.TabsAdaper
import com.example.photoeditorpolishanything.Model.ImageItem
import com.example.photoeditorpolishanything.databinding.ActivityTemplatesBinding
import com.google.android.material.tabs.TabLayout

class TemplatesActivity : BaseActivity()
{
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CollageTemplateAdapter
    lateinit var binding : ActivityTemplatesBinding
    private val PICK_IMAGE_REQUEST = 1


    companion object{
        @SuppressLint("StaticFieldLeak")
        var contexts : Context? = null
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityTemplatesBinding.inflate(layoutInflater)
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

    override fun updateUIForSelectedLanguage() {
        super.updateUIForSelectedLanguage()
        val context = LocaleHelper.onAttach(this)
        val resources = context.resources

        binding.tabLayout.getTabAt(0)?.text = resources.getString(R.string.All)
        binding.tabLayout.getTabAt(1)?.text = resources.getString(R.string.birthday)
        binding.tabLayout.getTabAt(2)?.text = resources.getString(R.string.Calendar)
        binding.tabLayout.getTabAt(3)?.text = resources.getString(R.string.Christmas)
        binding.tabLayout.getTabAt(4)?.text = resources.getString(R.string.Family)
        binding.tabLayout.getTabAt(5)?.text = resources.getString(R.string.Frame)
        binding.tabLayout.getTabAt(6)?.text = resources.getString(R.string.Graduation)
        binding.tabLayout.getTabAt(7)?.text = resources.getString(R.string.Love)
        binding.tabLayout.getTabAt(8)?.text = resources.getString(R.string.Motherday)
        binding.tabLayout.getTabAt(9)?.text = resources.getString(R.string.Newyear)
        binding.tabLayout.getTabAt(10)?.text = resources.getString(R.string.Pride)
        binding.tabLayout.getTabAt(11)?.text = resources.getString(R.string.Schoollife)
        binding.tabLayout.getTabAt(12)?.text = resources.getString(R.string.Summer)
        binding.tabLayout.getTabAt(13)?.text = resources.getString(R.string.Travel)
        binding.tabLayout.getTabAt(14)?.text = resources.getString(R.string.Winter)
    }



    private fun initView() {

        binding.imgback.setOnClickListener { onBackPressed()}

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.All))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.birthday))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Calendar))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Christmas))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Family))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Frame))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Graduation))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Love))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Motherday))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Newyear))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Pride))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Schoollife))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Summer))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Travel))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.Winter))

        val Myadapter = TabsAdaper(supportFragmentManager)
        binding.viewpager.adapter = Myadapter

        binding.viewpager.addOnPageChangeListener(object : TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout) {})

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {

            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                if (tab != null)
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

        for (i in 0 until binding.tabLayout.tabCount)
        {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(i) as ViewGroup
            val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(15, 0, 15, 0) // Adjust margins as needed
            tab.requestLayout()
        }
    }

    class FetchImagesTasks(val context: Context, val callback: (List<ImageItem>) -> Unit) : AsyncTask<Void, Void, List<ImageItem>>()
    {
        override fun doInBackground(vararg params: Void?): List<ImageItem>
        {
            val images = mutableListOf<ImageItem>()
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.Media._ID)
            val cursor = context.contentResolver.query(
                uri,projection,null,null,MediaStore.Images.Media.DATE_ADDED + " DESC")

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (it.moveToNext()) {
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

    fun openGallery()
    {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null)
        {
            val selectedImageUri = data.data
            // Use the selectedImageUri to load the image in your ImageView or pass it to another activity
        }
    }
}