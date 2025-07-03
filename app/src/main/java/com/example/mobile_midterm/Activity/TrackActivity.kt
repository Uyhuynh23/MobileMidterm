package com.example.mobile_midterm.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_midterm.databinding.ActivityTrackBinding

class TrackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trackOrderBtn.setOnClickListener {
            // Navigate back to MainActivity and clear back stack
            val intent = Intent(this,  MyOrderActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
