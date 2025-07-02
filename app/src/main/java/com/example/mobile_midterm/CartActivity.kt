package com.example.mobile_midterm

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_midterm.Adapter.CartAdapter
import com.example.mobile_midterm.Helper.ChangeNumberItemsListener
import com.example.mobile_midterm.Helper.ManagmentCart
import com.example.mobile_midterm.databinding.ActivityCartBinding
import com.example.mobile_midterm.databinding.ActivityDetailBinding

class CartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding
    lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        calculateCart()
        setVariable()
        initCartList()
        checkOut()

   }

    private fun checkOut() {
        binding.checkOutBtn.setOnClickListener {
            managmentCart.checkOut(managmentCart.getListCart())
            managmentCart.clearCart()
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initCartList(){
        binding.apply{
            cartRecyclerView.layoutManager=
                LinearLayoutManager(this@CartActivity,LinearLayoutManager.VERTICAL,false)
            cartRecyclerView.adapter= CartAdapter(
                managmentCart.getListCart(),
                this@CartActivity,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculateCart()
                    }
                })
        }
    }

    private fun setVariable() {
        binding.backButton.setOnClickListener{finish()}
    }

    private fun calculateCart() {
        val total = Math.round(managmentCart.getTotalFee())

        binding.totalPriceText.text = "$$total"
    }
}