package com.example.mobile_midterm.Adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.databinding.ViewholderItemsBinding
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.mobile_midterm.Activity.DetailActivity

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

        val imagePath = item[position].picUrl[0]
        if (item[position].isDrawable) {
            // Load from drawable resource
            val resourceId = context.resources.getIdentifier(imagePath, "drawable", context.packageName)
            Glide.with(context)
                .load(resourceId)
                .into(holder.binding.itemImage)
        } else {
            // Load from URL
            Glide.with(context)
                .load(imagePath)
                .into(holder.binding.itemImage)
        }

        holder.itemView.setOnClickListener{
            val intent= Intent(context, DetailActivity::class.java)
            intent.putExtra("object",item[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = item.size
}