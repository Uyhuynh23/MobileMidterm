package com.example.mobile_midterm.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.Domain.UsersModel
import com.example.mobile_midterm.Helper.TinyDB
import com.example.mobile_midterm.databinding.ViewholderRedeemBinding

class RedeemAdapter(private val redeemItems: List<ItemsModel>) :
    RecyclerView.Adapter<RedeemAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderRedeemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderRedeemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = redeemItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = redeemItems[position]
        val context = holder.itemView.context
        val binding = holder.binding

        binding.redeemTitle.text = item.title
        binding.redeemDate.text = "Valid until 04.07.21"
        binding.BtnRedeem.text = "${item.pointsRedeem} pts"

        // Load image
        if (item.isDrawable) {
            val imageId = context.resources.getIdentifier(item.picUrl[0], "drawable", context.packageName)
            Glide.with(context).load(imageId).into(binding.redeemImage)
        } else {
            Glide.with(context).load(item.picUrl[0]).into(binding.redeemImage)
        }

        // Handle redeem button click
        binding.BtnRedeem.setOnClickListener {
            val tinyDB = TinyDB(context)
            val user = tinyDB.getObject("User", UsersModel::class.java) ?: UsersModel()

            if (user.points >= item.pointsRedeem) {
                // Subtract points
                user.points -= item.pointsRedeem
                user.points += item.points
                tinyDB.putObject("User", user)

                // Save to history rewards
                val rewardHistory = tinyDB.getListObject("HistoryRewards")
                rewardHistory.add(item)
                tinyDB.putListObject("HistoryRewards", rewardHistory)

                Toast.makeText(context, "You have redeemed ${item.title}!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Not enough points to redeem this item.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
