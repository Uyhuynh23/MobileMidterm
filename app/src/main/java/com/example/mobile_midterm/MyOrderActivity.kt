package com.example.mobile_midterm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobile_midterm.Adapter.MyOrderPagerAdapter
import com.example.mobile_midterm.databinding.ActivityMyOrderBinding
import com.google.android.material.tabs.TabLayoutMediator

class MyOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MyOrderPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "On going" else "History"
        }.attach()
    }
}