package com.example.mobile_midterm.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.databinding.ViewholderRewardBinding

class RewardAdapter(private val rewardList: List<ItemsModel>) :
    RecyclerView.Adapter<RewardAdapter.RewardViewHolder>() {

    class RewardViewHolder(val binding: ViewholderRewardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val binding = ViewholderRewardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RewardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        val item = rewardList[position]
        holder.binding.rewardTitle.text = item.title
        holder.binding.rewardTime.text = item.orderTime
        holder.binding.pointsText.text = "+ ${item.points} Pts"
    }

    override fun getItemCount(): Int = rewardList.size
}
