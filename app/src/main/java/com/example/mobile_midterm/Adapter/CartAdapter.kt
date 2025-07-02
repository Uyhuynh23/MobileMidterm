package com.example.mobile_midterm.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.Helper.ChangeNumberItemsListener
import com.example.mobile_midterm.Helper.ManagmentCart
import com.example.mobile_midterm.databinding.ViewholderCartBinding

class CartAdapter(
    private val listItemSelected: ArrayList<ItemsModel>,
    context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener? = null
) : RecyclerView.Adapter<CartAdapter.Viewholder>() {
    class Viewholder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    private val managmentCart = ManagmentCart(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.Viewholder {
        val binding =
            ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.Viewholder, @SuppressLint("RecyclerView") position: Int) {
        val item = listItemSelected[position]

        holder.binding.ItemNameCart.text = item.title
        holder.binding.optionIce.text = item.ice
        holder.binding.optionShot.text = item.shot
        holder.binding.optionSize.text = item.size
        holder.binding.optionSelect.text = item.select


        holder.binding.TotalItemPrice.text = "$${Math.round(item.numberInCart * item.price)}"
        holder.binding.itemQuantity.text = "x" + item.numberInCart.toString()

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .apply(RequestOptions().transform(CenterCrop()))
            .into(holder.binding.picCart)

        holder.binding.plusEachItem.setOnClickListener {
            managmentCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }

            })
        }

        holder.binding.minusEachItem.setOnClickListener {
            managmentCart.minusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }

            })
        }

        holder.binding.removeItemBtn.setOnClickListener {
            managmentCart.removeItem(
                listItemSelected,
                position,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        notifyDataSetChanged()
                        changeNumberItemsListener?.onChanged()
                    }

                })
        }
    }

    override fun getItemCount(): Int = listItemSelected.size

}