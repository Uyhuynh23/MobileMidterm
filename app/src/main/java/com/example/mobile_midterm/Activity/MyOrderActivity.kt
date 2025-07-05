package com.example.mobile_midterm.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_midterm.Adapter.MyOrderPagerAdapter
import com.example.mobile_midterm.Helper.BottomNavHelper
import com.example.mobile_midterm.databinding.ActivityMyOrderBinding
import com.google.android.material.tabs.TabLayoutMediator

class MyOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupBottomNav()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = MyOrderPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "On going"
                1 -> "History"
                else -> ""
            }
        }.attach()
    }

    private fun setupBottomNav() {
        BottomNavHelper.setupNavigation(
            this,
            binding.Shop,
            binding.Gift,
            binding.Receipt,
            "receipt"
        )
    }
}
