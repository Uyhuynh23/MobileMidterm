// MyOrderPagerAdapter.kt
package com.example.mobile_midterm.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mobile_midterm.Activity.HistoryFragment
import com.example.mobile_midterm.Activity.OngoingFragment

class MyOrderPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OngoingFragment()
            1 -> HistoryFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}
