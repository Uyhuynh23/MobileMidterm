package com.example.mobile_midterm.Helper

import android.app.Activity
import android.content.Intent
import android.widget.ImageView
import com.example.mobile_midterm.Activity.MainActivity
import com.example.mobile_midterm.Activity.MyOrderActivity
import com.example.mobile_midterm.Activity.RewardsActivity

object BottomNavHelper {

    fun setupNavigation(
        activity: Activity,
        shopIcon: ImageView,
        giftIcon: ImageView,
        receiptIcon: ImageView,
        currentScreen: String // "shop", "gift", or "receipt"
    ) {
        // Set initial alpha based on current screen
        when (currentScreen) {
            "shop" -> {
                shopIcon.alpha = 1f
                giftIcon.alpha = 0.3f
                receiptIcon.alpha = 0.3f
            }
            "gift" -> {
                shopIcon.alpha = 0.3f
                giftIcon.alpha = 1f
                receiptIcon.alpha = 0.3f
            }
            "receipt" -> {
                shopIcon.alpha = 0.3f
                giftIcon.alpha = 0.3f
                receiptIcon.alpha = 1f
            }
        }

        // Set listeners
        shopIcon.setOnClickListener {
            if (currentScreen != "shop") {
                activity.startActivity(Intent(activity, MainActivity::class.java))
                activity.overridePendingTransition(0, 0)
            }
        }

        giftIcon.setOnClickListener {
            if (currentScreen != "gift") {
                activity.startActivity(Intent(activity, RewardsActivity::class.java))
                activity.overridePendingTransition(0, 0)
            }
        }

        receiptIcon.setOnClickListener {
            if (currentScreen != "receipt") {
                activity.startActivity(Intent(activity, MyOrderActivity::class.java))
                activity.overridePendingTransition(0, 0)
            }
        }
    }
}
