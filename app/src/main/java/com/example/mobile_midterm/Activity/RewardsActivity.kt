package com.example.mobile_midterm.Activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_midterm.Adapter.LoyaltyAdapter
import com.example.mobile_midterm.Adapter.RewardAdapter
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.Domain.UsersModel
import com.example.mobile_midterm.Helper.BottomNavHelper
import com.example.mobile_midterm.Helper.TinyDB
import com.example.mobile_midterm.R
import com.example.mobile_midterm.databinding.ActivityRewardsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        binding.DailyReward.setOnClickListener{
            intent= Intent(this, DailySpinActivity::class.java)
            startActivity(intent)
        }

        BottomNavHelper.setupNavigation(
            this,
            binding.Shop,
            binding.Gift,
            binding.Receipt,
            "gift"
        )

        binding.DailyReward.setOnClickListener(){
            intent= Intent(this, DailySpinActivity::class.java)
            startActivity(intent)
        }

        setupLoyaltyCard()
        setupPointDisplay()
        setupRewardHistory()
        setButtonDailyReward()
    }

    override fun onResume() {
        super.onResume()
        setupPointDisplay()
        setupRewardHistory()
        setupLoyaltyCard()
        setButtonDailyReward()
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

        if (earnedCups == totalCups) {
            val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
            binding.LoyaltyCard.startAnimation(shake)

            binding.LoyaltyCard.setOnClickListener {
                binding.LoyaltyCard.clearAnimation()

                user.points += 1400
                user.loyaltyCups = 0
                TinyDB(this).putObject("User", user)

                setupLoyaltyCard() // refresh display
                setupPointDisplay() // refresh points

                AlertDialog.Builder(this)
                    .setTitle("Congratulations!")
                    .setMessage("You've earned 1400 points 🎉")
                    .setPositiveButton("OK", null)
                    .show()
            }
        } else {
            binding.LoyaltyCard.clearAnimation()
            binding.LoyaltyCard.setOnClickListener(null)
        }
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
    private var shakeAnimator: ObjectAnimator? = null

    private fun startShaking(view: View) {
        shakeAnimator?.cancel() // Stop if already shaking

        shakeAnimator = ObjectAnimator.ofFloat(view, "translationX", 0f, 10f, -10f, 10f, -10f, 0f).apply {
            duration = 600
            repeatCount = ObjectAnimator.INFINITE
            start()
        }
    }


    private fun setButtonDailyReward() {
        val tinyDB = TinyDB(this)
        val user = tinyDB.getObject("User", UsersModel::class.java) ?: UsersModel()
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        if (user.lastSpinDate != today) {
            startShaking(binding.DailyReward)
        } else {
            shakeAnimator?.cancel()
            shakeAnimator = null
        }
    }

}
