package com.example.mobile_midterm

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_midterm.Adapter.CartAdapter
import com.example.mobile_midterm.Helper.ChangeNumberItemsListener
import com.example.mobile_midterm.Helper.ManagmentCart
import com.example.mobile_midterm.databinding.ActivityCartBinding

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
        if(managmentCart.getListCart().isEmpty())
            binding.checkOutBtn.isEnabled = false
        else
            binding.checkOutBtn.isEnabled = true

        binding.checkOutBtn.setOnClickListener {
            managmentCart.checkOut(managmentCart.getListCart())
            managmentCart.clearCart()
            intent = Intent(this, TrackActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun initCartList() {
        val cartItems = managmentCart.getListCart()
        val cartAdapter = CartAdapter(
            cartItems,
            this@CartActivity,
            object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculateCart()
                }
            }
        )

        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }

        // Swipe-to-delete with ItemTouchHelper
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                managmentCart.removeItem(cartItems, position, object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        cartAdapter.notifyItemRemoved(position)
                        calculateCart()
                    }
                })
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val paint = Paint().apply { color = Color.parseColor("#FDEAEA") }
                val icon = ContextCompat.getDrawable(this@CartActivity, R.drawable.ic_bin_red)!!
                val cornerRadius = 30f
                val background = RectF(
                    itemView.right.toFloat() + dX,
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat()
                )

                c.drawRoundRect(background, cornerRadius, cornerRadius, paint)

                val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
                val iconTop = itemView.top + iconMargin
                val iconLeft = itemView.right - icon.intrinsicWidth - iconMargin
                val iconRight = itemView.right - iconMargin
                val iconBottom = iconTop + icon.intrinsicHeight

                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                icon.draw(c)

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.cartRecyclerView)
    }


    private fun setVariable() {
        binding.backButton.setOnClickListener{finish()}
    }

    private fun calculateCart() {
        val total = Math.round(managmentCart.getTotalFee())

        binding.totalPriceText.text = "$$total"
    }
}