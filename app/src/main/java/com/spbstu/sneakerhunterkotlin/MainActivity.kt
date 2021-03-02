package com.spbstu.sneakerhunterkotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.spbstu.sneakerhunterkotlin.fragments.ContainerSearchFragment
import com.spbstu.sneakerhunterkotlin.fragments.ContainerLoginFragment
import java.util.*

class MainActivity : AppCompatActivity() {
    private inner class SectionsPagerAdapter internal constructor(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return ContainerSearchFragment()
                1 -> return ContainerLoginFragment()
            }
            return ContainerSearchFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter =
            SectionsPagerAdapter(supportFragmentManager)
        val viewPager = findViewById<View>(R.id.pager) as ViewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
        Objects.requireNonNull(tabLayout.getTabAt(0))
            ?.setIcon(R.drawable.baseline_search_24)
        Objects.requireNonNull(tabLayout.getTabAt(1))
            ?.setIcon(R.drawable.baseline_person_24)
    }

    companion object {
        var USER_ID: String? = null
    }
}