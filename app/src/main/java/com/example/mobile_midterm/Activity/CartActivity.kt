package com.example.mobile_midterm.Activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_midterm.Adapter.CartAdapter
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.Helper.ChangeNumberItemsListener
import com.example.mobile_midterm.Helper.ManagmentCart
import com.example.mobile_midterm.R
import com.example.mobile_midterm.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartItems: ArrayList<ItemsModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        setupViews()
        setupCartList()
        setupSwipeToDelete()
        updateCartSummary()
    }

    private fun setupViews() {
        binding.backButton.setOnClickListener { finish() }

        binding.checkOutBtn.setOnClickListener {
            if (cartItems.isNotEmpty()) {
                managmentCart.checkOut(cartItems)
                managmentCart.clearCart()
                navigateToTrackActivity()
            }
        }
    }

    private fun setupCartList() {
        cartItems = managmentCart.getListCart()
        cartAdapter = CartAdapter(cartItems, this, object : ChangeNumberItemsListener {
            override fun onChanged() {
                updateCartSummary()
            }
        })

        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }

        binding.checkOutBtn.isEnabled = cartItems.isNotEmpty()
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
                val position = vh.adapterPosition
                managmentCart.removeItem(cartItems, position, object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        cartAdapter.notifyItemRemoved(position)
                        updateCartSummary()
                        binding.checkOutBtn.isEnabled = cartItems.isNotEmpty()
                    }
                })
            }

            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                drawSwipeBackground(c, viewHolder, dX)
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.cartRecyclerView)
    }

    private fun drawSwipeBackground(canvas: Canvas, viewHolder: RecyclerView.ViewHolder, dX: Float) {
        val itemView = viewHolder.itemView
        val paint = Paint().apply { color = Color.parseColor("#FDEAEA") }
        val icon = ContextCompat.getDrawable(this, R.drawable.ic_bin_red)!!
        val cornerRadius = 30f

        val background = RectF(
            itemView.right.toFloat() + dX,
            itemView.top.toFloat(),
            itemView.right.toFloat(),
            itemView.bottom.toFloat()
        )

        canvas.drawRoundRect(background, cornerRadius, cornerRadius, paint)

        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + iconMargin
        val iconLeft = itemView.right - icon.intrinsicWidth - iconMargin
        val iconRight = itemView.right - iconMargin
        val iconBottom = iconTop + icon.intrinsicHeight

        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        icon.draw(canvas)
    }

    private fun updateCartSummary() {
        val total = managmentCart.getTotalFee().toInt()
        binding.totalPriceText.text = "$$total"
    }

    private fun navigateToTrackActivity() {
        val intent = Intent(this, TrackActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
