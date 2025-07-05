package com.example.mobile_midterm.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_midterm.Adapter.CategoryAdapter
import com.example.mobile_midterm.Adapter.ItemsAdapter
import com.example.mobile_midterm.Adapter.LoyaltyAdapter
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.Domain.UsersModel
import com.example.mobile_midterm.Helper.BottomNavHelper
import com.example.mobile_midterm.Helper.TinyDB
import com.example.mobile_midterm.R
import com.example.mobile_midterm.ViewModel.MainViewModel
import com.example.mobile_midterm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loyaltyAdapter: LoyaltyAdapter
    private lateinit var tinyDB: TinyDB
    private val viewModel = MainViewModel()

    private var allItems: List<ItemsModel> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tinyDB = TinyDB(this)

        setupUI()
        setupBottomNav()
        loadProfile()
        setupLoyaltyCard()
        loadItems()
        loadCategories()
    }

    override fun onResume() {
        super.onResume()
        loadProfile()
        setupLoyaltyCard()
        loadItems()
    }

    // --- UI SETUP ---

    private fun setupUI() {
        binding.cartIcon.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        binding.profileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun setupBottomNav() {
        BottomNavHelper.setupNavigation(
            this,
            binding.Shop,
            binding.Gift,
            binding.Receipt,
            "shop"
        )
    }

    private fun loadProfile() {
        val user = tinyDB.getObject("User", UsersModel::class.java) ?: UsersModel()
        binding.userName.text = user.fullName
    }

    private fun setupLoyaltyCard() {
        binding.progressBar.visibility = View.VISIBLE
        binding.cupRecycler.visibility = View.GONE

        val user = tinyDB.getObject("User", UsersModel::class.java) ?: UsersModel()
        val totalCups = 8
        val earnedCups = user.loyaltyCups.coerceAtMost(totalCups)

        binding.progressText.text = "$earnedCups / $totalCups"
        binding.cupRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loyaltyAdapter = LoyaltyAdapter(totalCups, earnedCups)
        binding.cupRecycler.adapter = loyaltyAdapter

        binding.progressBar.visibility = View.GONE
        binding.cupRecycler.visibility = View.VISIBLE

        if (earnedCups == totalCups) {
            val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
            binding.LoyaltyCard.startAnimation(shake)

            binding.LoyaltyCard.setOnClickListener {
                // Stop animation first
                binding.LoyaltyCard.clearAnimation()

                // Reward
                user.points += 1400
                user.loyaltyCups = 0
                tinyDB.putObject("User", user)

                // Refresh UI
                setupLoyaltyCard()

                // Show dialog
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


    // --- DATA LOADING ---

    private fun loadItems() {
        binding.progressBarItem.visibility = View.VISIBLE

        viewModel.loadItems().observe(this) { items ->
            val favorites = tinyDB.getListObject("Favorite")

            // Sync favorite status
            items.forEach { item ->
                item.isFavorite = favorites.any { fav -> fav.title == item.title }
            }

            allItems = items
            binding.coffeeRecycler.layoutManager = GridLayoutManager(this, 2)
            filterItemsByCategory("All")

            binding.progressBarItem.visibility = View.GONE
        }
    }

    private fun loadCategories() {
        viewModel.loadCategories().observe(this) { categoryList ->
            val categoryAdapter = CategoryAdapter(categoryList) { selectedCategory ->
                filterItemsByCategory(selectedCategory)
            }

            binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerView.adapter = categoryAdapter
        }
    }

    private fun filterItemsByCategory(category: String) {
        val filtered = when (category) {
            "All" -> allItems
            "Favorite" -> {
                val favorites = tinyDB.getListObject("Favorite")
                allItems.filter { item -> favorites.any { it.title == item.title } }
            }
            else -> allItems.filter { it.category.equals(category, ignoreCase = true) }
        }

        binding.coffeeRecycler.adapter = ItemsAdapter(filtered.toMutableList())
    }
}
