package com.example.photoeditorpolishanything.Adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.photoeditorpolishanything.Fragment.BorderFragment
import com.example.photoeditorpolishanything.Fragment.LayoutFragment
import com.example.photoeditorpolishanything.Fragment.MarginFragment
import com.example.photoeditorpolishanything.Fragment.RatioFragment

class TabAdapter(fm: FragmentManager, private val imageUri: String) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> RatioFragment().apply {
                arguments = createArguments()
            }
            1 -> LayoutFragment().apply {
                arguments = createArguments()
            }
            2 -> MarginFragment().apply {
                arguments = createArguments()
            }
            3 -> BorderFragment().apply {
                arguments = createArguments()
            }
            else -> LayoutFragment().apply {
                arguments = createArguments()
            }
        }
    }

    private fun createArguments(): Bundle {
        return Bundle().apply {
            putString("imageUri", imageUri)
        }
    }
}
