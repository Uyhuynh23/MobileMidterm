package com.example.mobile_midterm.Activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_midterm.Helper.BottomNavHelper
import com.example.mobile_midterm.Helper.TinyDB
import com.example.mobile_midterm.R
import com.example.mobile_midterm.Domain.UsersModel
import com.example.mobile_midterm.databinding.ActivityDailySpinBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class DailySpinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDailySpinBinding
    private var isSpinning = false
    private val rewards = listOf(50, 100, 200, 500, 1000, 500, 100, 100)
    private lateinit var user: UsersModel
    private lateinit var tinyDB: TinyDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailySpinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tinyDB = TinyDB(this)
        user = tinyDB.getObject("User", UsersModel::class.java) ?: UsersModel()

        // Setup back button
        binding.backButton.setOnClickListener { finish() }


        // Check if already spun today
        val today = getCurrentDate()
        binding.SpinBtn.setOnClickListener {
                if(user.lastSpinDate == today)
                {
                    disableSpin(showToast = true)
                    return@setOnClickListener
                }
                else{
                    if (!isSpinning) {
                        spinWheel()
                    } else {
                        Toast.makeText(this, "Wait for the wheel to stop", Toast.LENGTH_SHORT).show()
                    }
                }

            }
    }


    private fun disableSpin(showToast: Boolean = true) {
        if (showToast) {
            Toast.makeText(this, "You’ve already spun today! Come back tomorrow.", Toast.LENGTH_LONG).show()
        }
    }


    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun spinWheel() {
        isSpinning = true

        val sectorCount = rewards.size
        val selectedIndex = Random.nextInt(sectorCount)
        val degreePerSector = 360 / sectorCount
        val rotation = (360 * 5) + (degreePerSector * selectedIndex) + degreePerSector / 2

        val animator = ObjectAnimator.ofFloat(binding.spinWheel, ImageView.ROTATION, 0f, rotation.toFloat())
        animator.duration = 3000
        animator.interpolator = DecelerateInterpolator()
        animator.start()

        animator.addUpdateListener {
            if (it.animatedFraction == 1f) {
                val reward = rewards[selectedIndex]
                handleReward(reward)
                isSpinning = false
                binding.SpinBtn.isEnabled = false // Disable after spinning
            }
        }
    }

    private fun handleReward(points: Int) {
        if (points > 0) {
            user.points += points
            Toast.makeText(this, "🎉 You won $points points!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "😢 Good luck next time!", Toast.LENGTH_SHORT).show()
        }

        user.lastSpinDate = getCurrentDate()
        tinyDB.putObject("User", user)

        // Disable spin after reward
        binding.SpinBtn.isEnabled = false
    }





}
