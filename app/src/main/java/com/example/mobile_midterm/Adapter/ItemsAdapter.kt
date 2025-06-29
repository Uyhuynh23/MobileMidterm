package com.example.mobile_midterm.Adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.databinding.ViewholderItemsBinding
import android.content.Context
import android.view.LayoutInflater
import com.bumptech.glide.Glide

class ItemsAdapter(val item:MutableList<ItemsModel>):
    RecyclerView.Adapter<ItemsAdapter.Viewholder>() {
    lateinit var context:Context
    
    class Viewholder(val binding:ViewholderItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context=parent.context
        val  binding=ViewholderItemsBinding.inflate(LayoutInflater.from(context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: ItemsAdapter.Viewholder, position: Int) {
       holder.binding.itemName.text=item[position].title

        Glide.with(context)
             .load(item[position].picUrl[0])
             .into(holder.binding.itemImage)
    }

    override fun getItemCount(): Int = item.size
}