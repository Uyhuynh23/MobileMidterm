package com.example.mobile_midterm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class LoyaltyAdapter(
    private val totalCups: Int,
    private val earnedCups: Int
) : RecyclerView.Adapter<LoyaltyAdapter.CupViewHolder>() {

    class CupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.cupImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_loyalty_cup, parent, false)
        return CupViewHolder(view)
    }

    override fun getItemCount(): Int = totalCups

    override fun onBindViewHolder(holder: CupViewHolder, position: Int) {
        val imageRes = if (position < earnedCups) R.drawable.cup_filled else R.drawable.cup_empty
        holder.imageView.setImageResource(imageRes)
    }
}
