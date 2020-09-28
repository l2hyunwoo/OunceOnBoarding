package com.example.ounceonboarding.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.ounceonboarding.R

private const val NUM_PAGES = 3

class MainActivity : FragmentActivity() {

    private lateinit var vp_slider : ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ViewPager2 인스턴스화
        vp_slider = findViewById(R.id.vp_scroll_tutorial)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        vp_slider.adapter = pagerAdapter
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> ImageSlideFragment(R.drawable.hashtag_image)
                1 -> ImageSlideFragment(R.drawable.nomad_image)
                else -> ImageSlideFragment(R.drawable.record_image)
            }
        }
    }
}