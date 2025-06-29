package com.example.mobile_midterm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobile_midterm.Adapter.ItemsAdapter
import com.example.mobile_midterm.ViewModel.MainViewModel
import com.example.mobile_midterm.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loyaltyAdapter: LoyaltyAdapter

    private val viewModel= MainViewModel()

    private val totalCups = 7
    private val earnedCups = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLoyaltyCard()
        initItems()
    }

    private fun setupLoyaltyCard() {
        // Set text like "4 / 8"

        binding.progressBar.visibility = View.VISIBLE
        binding.cupRecycler.visibility = View.GONE
        binding.progressText.text = "$earnedCups / $totalCups"

        // Setup RecyclerView with horizontal layout
        binding.cupRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loyaltyAdapter = LoyaltyAdapter(totalCups, earnedCups)
        binding.cupRecycler.adapter = loyaltyAdapter

        binding.progressBar.visibility = View.GONE
        binding.cupRecycler.visibility = View.VISIBLE
    }

    private fun initItems(){

        binding.progressBarItem.visibility = View.VISIBLE
        viewModel.loadItems().observe(this) { items ->
            binding.coffeeRecycler.layoutManager = GridLayoutManager(this, 2)
            binding.coffeeRecycler.adapter = ItemsAdapter(items)
        }
        binding.progressBarItem.visibility = View.GONE
    }

}


