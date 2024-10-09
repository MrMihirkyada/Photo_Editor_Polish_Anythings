package com.example.photoeditorpolishanything.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.photoeditorpolishanything.databinding.FragmentLayoutBinding

class LayoutFragment : Fragment() {

    private var _binding: FragmentLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLayoutBinding.inflate(inflater, container, false)
        val view = binding.root

//        initView()

        return view
    }

//    private fun initView()
//    {
//        val imageUriString = arguments?.getString("imageUri")
//        if (imageUriString != null) {
//            val imageUri = Uri.parse(imageUriString)
////            Glide.with(this)
////                .load(imageUri)
////                .into(binding.imgLayou1) // Assuming imgLayou1 is the ImageView in your fragment layout
//        }
//
//
//        binding.imgLayout1.setOnClickListener {
//            binding.imgLayout1.isDrawingCacheEnabled = true
//            binding.imgLayout1.buildDrawingCache()
//            val bitmap = binding.imgLayout1.drawingCache
//            if (bitmap != null) {
////                val croppedBitmap = cropRectangle(bitmap)
//                // Handle the cropped bitmap (e.g., display it or save it)
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    private fun divideBitmap(bitmap: Bitmap) {
//        // Example: Dividing the bitmap into equal parts
//        val width = bitmap.width
//        val height = bitmap.height
//
//        // Calculate dimensions for each section (assuming equal parts for simplicity)
//        val sectionWidth = width / 2  // Adjust this based on your layout division logic
//        val sectionHeight = height / 2 // Adjust this based on your layout division logic
//
//        // Create cropped bitmaps for each section
//        val section1 = cropRectangle(bitmap, 0, 0, sectionWidth, sectionHeight)
//        val section2 = cropRectangle(bitmap, sectionWidth, 0, sectionWidth, sectionHeight)
//        val section3 = cropRectangle(bitmap, 0, sectionHeight, sectionWidth, sectionHeight)
//        val section4 = cropRectangle(bitmap, sectionWidth, sectionHeight, sectionWidth, sectionHeight)
//
//        // Handle the cropped bitmaps as needed (e.g., display them or save them)
//        // Example: display the cropped bitmaps in ImageViews
////        binding.imgSection1.setImageBitmap(section1)
////        binding.imgSection2.setImageBitmap(section2)
////        binding.imgSection3.setImageBitmap(section3)
////        binding.imgSection4.setImageBitmap(section4)
//    }
//
//    private fun cropRectangle(bitmap: Bitmap, left: Int, top: Int, width: Int, height: Int): Bitmap {
//        return Bitmap.createBitmap(bitmap, left, top, width, height)
//    }
}