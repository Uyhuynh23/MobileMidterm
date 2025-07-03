package com.example.mobile_midterm.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_midterm.Adapter.RedeemAdapter
import com.example.mobile_midterm.Repository.MainRepository
import com.example.mobile_midterm.databinding.ActivityRedeemBinding

class RedeemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRedeemBinding
    private lateinit var redeemAdapter: RedeemAdapter
    private val repository = MainRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRedeemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        loadRedeemItems()
    }

    private fun setupUI() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.redeemRecycler.layoutManager = LinearLayoutManager(this)
    }

    private fun loadRedeemItems() {
        repository.loadItems().observe(this) { itemList ->
            // Only show items that can be redeemed
            val redeemableItems = itemList.filter { it.pointsRedeem > 0 }
            redeemAdapter = RedeemAdapter(redeemableItems)
            binding.redeemRecycler.adapter = redeemAdapter
        }
    }
}
