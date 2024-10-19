package com.example.photoeditorpolishanything.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.photoeditorpolishanything.Fragment.BackgroundFragment
import com.example.photoeditorpolishanything.StoreFragment.FilterFragment
import com.example.photoeditorpolishanything.StoreFragment.LightFxFragment
import com.example.photoeditorpolishanything.StoreFragment.StickerFragment
import com.example.photoeditorpolishanything.StoreFragment.TextFragment

class StoreTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 5
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0)
        {
            return StickerFragment()
        }
        else if (position == 1)
        {
            return BackgroundFragment()
        }
        else if (position == 2)
        {
            return LightFxFragment()
        }
        else if (position == 3)
        {
            return TextFragment()
        }
        else if (position == 4)
        {
            return FilterFragment()
        }
        return null!!
    }
}