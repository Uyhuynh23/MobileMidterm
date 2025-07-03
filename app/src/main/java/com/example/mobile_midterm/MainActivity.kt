package com.example.mobile_midterm

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobile_midterm.Adapter.ItemsAdapter
import com.example.mobile_midterm.Adapter.LoyaltyAdapter
import com.example.mobile_midterm.Domain.UsersModel
import com.example.mobile_midterm.Helper.BottomNavHelper
import com.example.mobile_midterm.Helper.TinyDB
import com.example.mobile_midterm.ViewModel.MainViewModel
import com.example.mobile_midterm.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loyaltyAdapter: LoyaltyAdapter

    private val viewModel= MainViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartIcon.setOnClickListener(){
            intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        binding.profileIcon.setOnClickListener(){
            intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        //Update profile
        setupProfile()

        BottomNavHelper.setupNavigation(
            this,
            binding.Shop,
            binding.Gift,
            binding.Receipt,
            "shop"
        )

        setupLoyaltyCard()
        initItems()
    }

    override fun onResume() {
        super.onResume()
        setupLoyaltyCard()
        setupProfile()
    }
    private fun setupProfile() {
        var tinyDB = TinyDB(this)
        var user = tinyDB.getObject("User", UsersModel::class.java) ?: UsersModel()
        binding.userName.text = user.fullName
    }
    private fun setupLoyaltyCard() {
        binding.progressBar.visibility = View.VISIBLE
        binding.cupRecycler.visibility = View.GONE

        // Load user data from TinyDB
        val tinyDB = TinyDB(this)
        val user = tinyDB.getObject("User", UsersModel::class.java) ?: UsersModel()

        val totalCups = 8
        val earnedCups = user.loyaltyCups.coerceAtMost(totalCups)

        // Update progress text
        binding.progressText.text = "$earnedCups / $totalCups"

        // Setup RecyclerView
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


