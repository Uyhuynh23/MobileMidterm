package com.example.mobile_midterm.Adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_midterm.Domain.CategoryModel
import com.example.mobile_midterm.R
import com.example.mobile_midterm.databinding.ViewholderCategoryBinding

class CategoryAdapter(
    private val categories: List<CategoryModel>,
    private val onCategorySelected: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        val context = holder.itemView.context

        holder.binding.titleCat.text = category.name
        holder.binding.titleCat.setTypeface(null, if (category.isSelected) Typeface.BOLD else Typeface.NORMAL)

        holder.binding.titleCat.setBackgroundResource(
            if (category.isSelected) R.drawable.darkblue_button else R.drawable.white_bg
        )

        holder.binding.titleCat.setTextColor(
            ContextCompat.getColor(
                context,
                if (category.isSelected) R.color.white else R.color.dark_blue
            )
        )

        holder.binding.titleCat.setOnClickListener {
            // Reset selection
            categories.forEach { it.isSelected = false }
            category.isSelected = true
            notifyDataSetChanged()
            onCategorySelected(category.name)
        }
    }
}
