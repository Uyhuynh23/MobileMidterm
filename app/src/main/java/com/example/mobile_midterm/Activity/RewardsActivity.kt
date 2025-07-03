package com.example.mobile_midterm.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_midterm.Adapter.LoyaltyAdapter
import com.example.mobile_midterm.Adapter.RewardAdapter
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.Domain.UsersModel
import com.example.mobile_midterm.Helper.BottomNavHelper
import com.example.mobile_midterm.Helper.TinyDB
import com.example.mobile_midterm.databinding.ActivityRewardsBinding

class RewardsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRewardsBinding
    private lateinit var loyaltyAdapter: LoyaltyAdapter
    private lateinit var rewardAdapter: RewardAdapter

    private val totalCups = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.redeemButton.setOnClickListener {
            intent= Intent(this, RedeemActivity::class.java)
            startActivity(intent)
        }

        BottomNavHelper.setupNavigation(
            this,
            binding.Shop,
            binding.Gift,
            binding.Receipt,
            "gift"
        )

        setupLoyaltyCard()
        setupPointDisplay()
        setupRewardHistory()
    }

    override fun onResume() {
        super.onResume()
        setupPointDisplay()
        setupRewardHistory()
        setupLoyaltyCard()
    }

    private fun setupLoyaltyCard() {
        binding.progressBar.visibility = View.VISIBLE
        binding.cupRecycler.visibility = View.GONE

        val user = TinyDB(this).getObject("User", UsersModel::class.java) ?: UsersModel()
        val earnedCups = user.loyaltyCups.coerceAtMost(totalCups)

        binding.progressText.text = "$earnedCups / $totalCups"

        binding.cupRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loyaltyAdapter = LoyaltyAdapter(totalCups, earnedCups)
        binding.cupRecycler.adapter = loyaltyAdapter

        binding.progressBar.visibility = View.GONE
        binding.cupRecycler.visibility = View.VISIBLE
    }

    private fun setupPointDisplay() {
        val user = TinyDB(this).getObject("User", UsersModel::class.java) ?: UsersModel()
        binding.pointsText.text = user.points.toString()
    }

    private fun setupRewardHistory() {
        val historyOrders = TinyDB(this).getListObject("HistoryRewards")
        binding.historyRewardsRecycler.layoutManager = LinearLayoutManager(this)
        rewardAdapter = RewardAdapter(historyOrders as ArrayList<ItemsModel>)
        binding.historyRewardsRecycler.adapter = rewardAdapter
    }
}
