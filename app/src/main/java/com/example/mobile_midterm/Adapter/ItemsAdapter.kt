package com.example.mobile_midterm.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobile_midterm.Activity.DetailActivity
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.Helper.TinyDB
import com.example.mobile_midterm.R
import com.example.mobile_midterm.databinding.ViewholderItemsBinding

class ItemsAdapter(private val item: MutableList<ItemsModel>) :
    RecyclerView.Adapter<ItemsAdapter.Viewholder>() {

    private lateinit var context: Context

    class Viewholder(val binding: ViewholderItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val currentItem = item[position]
        val binding = holder.binding

        binding.itemName.text = currentItem.title

        // Load image
        val imagePath = currentItem.picUrl[0]
        if (currentItem.isDrawable) {
            val resourceId = context.resources.getIdentifier(imagePath, "drawable", context.packageName)
            Glide.with(context).load(resourceId).into(binding.itemImage)
        } else {
            Glide.with(context).load(imagePath).into(binding.itemImage)
        }

        // Handle favorite icon
        updateHeartIcon(binding, currentItem.isFavorite)

        binding.favIcon.setOnClickListener {
            currentItem.isFavorite = !currentItem.isFavorite
            updateHeartIcon(binding, currentItem.isFavorite)
            saveFavoritesToTinyDB()
        }

        // Go to detail
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", currentItem)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = item.size

    private fun updateHeartIcon(binding: ViewholderItemsBinding, isFavorite: Boolean) {
        val iconRes = if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
        binding.favIcon.setImageResource(iconRes)
    }

    private fun saveFavoritesToTinyDB() {
        val favorites = ArrayList(item.filter { it.isFavorite })
        TinyDB(context).putListObject("Favorite", favorites)
    }
}
