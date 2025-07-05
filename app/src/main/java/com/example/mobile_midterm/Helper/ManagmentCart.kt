package com.example.mobile_midterm.Helper

import android.content.Context
import android.widget.Toast

import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.Domain.UsersModel


class ManagmentCart(val context: Context) {

    private val tinyDB = TinyDB(context)

    fun insertItems(item: ItemsModel) {
        val listItem = getListCart()

        val index = listItem.indexOfFirst {
            it.title == item.title &&
                    it.shot == item.shot &&
                    it.select == item.select &&
                    it.size == item.size &&
                    it.ice == item.ice
        }

        if (index != -1) {
            listItem[index].numberInCart += item.numberInCart
        } else {
            listItem.add(item)
        }

        tinyDB.putListObject("CartList", listItem)
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show()
    }


    fun getListCart(): ArrayList<ItemsModel> {
        return tinyDB.getListObject("CartList") ?: arrayListOf()
    }

    fun minusItem(listItems: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (listItems[position].numberInCart == 1) {
            listItems.removeAt(position)
        } else {
            listItems[position].numberInCart--
        }
        tinyDB.putListObject("CartList", listItems)
        listener.onChanged()
    }
    fun removeItem(listItems: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        listItems.removeAt(position)
        tinyDB.putListObject("CartList", listItems)
        listener.onChanged()
    }


    fun plusItem(listItems: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        listItems[position].numberInCart++
        tinyDB.putListObject("CartList", listItems)
        listener.onChanged()
    }

    fun getTotalFee(): Double {
        val listItem = getListCart()
        var fee = 0.0
        for (item in listItem) {
            fee += item.price * item.numberInCart
        }
        return String.format("%.2f", fee).toDouble()
    }


    fun checkOut(cartList: ArrayList<ItemsModel>) {
        val tinyDB = TinyDB(context)

        // Move current ongoing orders to history (if not empty)
        val ongoingOrders = tinyDB.getListObject("OnGoingOrders")
        if (ongoingOrders.isNotEmpty()) {
            val historyOrders = tinyDB.getListObject("HistoryOrders")
            historyOrders.addAll(ongoingOrders)
            tinyDB.putListObject("HistoryOrders", historyOrders)
        }

        // Add current time & default address to each new order
        val currentTime = java.text.SimpleDateFormat("dd MMM yyyy | hh:mm a", java.util.Locale.getDefault()).format(java.util.Date())
        for (item in cartList) {
            item.orderTime = currentTime
            item.address = "227 Nguyen Van Cu, District 5, HCMC"
        }

        // Save to OnGoingOrders
        tinyDB.putListObject("OnGoingOrders", cartList)

        // Update User: add points always, add loyalty only if history added
        val user = tinyDB.getObject("User", UsersModel::class.java) ?: UsersModel()
        val totalPoints = cartList.sumOf { it.points * it.numberInCart }

        user.points += totalPoints
        if (ongoingOrders.isNotEmpty()) {
            val totalCups = ongoingOrders.sumOf { it.numberInCart }
            user.loyaltyCups += totalCups
        }

        tinyDB.putObject("User", user)

        // Clear cart
        clearCart()
    }

    fun clearCart() {
        tinyDB.putListObject("CartList", arrayListOf<ItemsModel>())}
}