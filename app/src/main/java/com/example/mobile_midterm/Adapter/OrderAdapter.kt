package com.example.mobile_midterm.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.databinding.ViewholderOrderBinding

class OrderAdapter(private val orders: List<ItemsModel>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewholderOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = orders[position]
        holder.binding.orderItemName.text = item.title
        holder.binding.orderItemPrice.text = "$%.2f".format(item.price)
        holder.binding.orderItemTime.text = item.orderTime // Custom field
        holder.binding.orderItemLocation.text = item.address // Or hardcoded
    }
}
