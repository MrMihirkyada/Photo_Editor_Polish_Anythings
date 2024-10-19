package com.example.photoeditorpolishanything.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.photoeditorpolishanything.StoreFragment.Store_All_LightFx_Fragment


class Store_lightfx_Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Fragment
    {
        if (position == 0)
        {
            return Store_All_LightFx_Fragment()
        }
//        else if (position == 1)
//        {
//            return BackgroundFragment()
//        }
//        else if (position == 2)
//        {
//            return TextFragment()
//        }
//        else if (position == 3)
//        {
//            return FilterFragment()
//        }
        return null!!
    }
}
