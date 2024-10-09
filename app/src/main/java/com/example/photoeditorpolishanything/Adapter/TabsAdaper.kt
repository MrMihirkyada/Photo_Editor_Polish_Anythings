package com.example.photoeditorpolishanything.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.photoeditorpolishanything.Fragment.AllFragment
import com.example.photoeditorpolishanything.Fragment.BirthdayFragment
import com.example.photoeditorpolishanything.Fragment.CalenderFragment
import com.example.photoeditorpolishanything.Fragment.ChristmasFragment
import com.example.photoeditorpolishanything.Fragment.FamilyFragment
import com.example.photoeditorpolishanything.Fragment.FrameFragment
import com.example.photoeditorpolishanything.Fragment.GraduationFragment
import com.example.photoeditorpolishanything.Fragment.LoveFragment
import com.example.photoeditorpolishanything.Fragment.MotherDayFragment
import com.example.photoeditorpolishanything.Fragment.NewyearFragment
import com.example.photoeditorpolishanything.Fragment.PrideFragment
import com.example.photoeditorpolishanything.Fragment.SchoolLifeFragment
import com.example.photoeditorpolishanything.Fragment.SummerFragment
import com.example.photoeditorpolishanything.Fragment.TravelFragment
import com.example.photoeditorpolishanything.Fragment.WinterFragment


class TabsAdaper(fm: FragmentManager) : FragmentPagerAdapter(fm)
{
    override fun getCount(): Int {
        return 15
    }

    override fun getItem(position: Int): Fragment
    {
        if (position == 0)
        {
            return AllFragment()
        }
        else if (position == 1)
        {
            return BirthdayFragment()
        }
        else if (position == 2)
        {
            return CalenderFragment()
        }
        else if (position == 3)
        {
            return ChristmasFragment()
        }
        else if (position == 4)
        {
            return FamilyFragment()
        }
        else if (position == 5)
        {
            return FrameFragment()
        }
        else if (position == 6)
        {
            return GraduationFragment()
        }
        else if (position == 7)
        {
            return LoveFragment()
        }
        else if (position == 8)
        {
            return MotherDayFragment()
        }
        else if (position == 9)
        {
            return NewyearFragment()
        }
        else if (position == 10)
        {
            return PrideFragment()
        }
        else if (position == 11)
        {
            return SchoolLifeFragment()
        }
        else if (position == 12)
        {
            return SummerFragment()
        }
        else if (position == 13)
        {
            return TravelFragment()
        }
        else if (position == 14)
        {
            return WinterFragment()
        }
        return null!!
    }
}
