package com.example.mobile_midterm.Helper

import android.content.Context
import android.widget.Toast

import com.example.mobile_midterm.Domain.ItemsModel


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
        return fee
    }

    fun checkOut(arraylist: ArrayList<ItemsModel>) {
        tinyDB.putListObject("HistoryList", arraylist)
    }

    fun clearCart() {
        tinyDB.putListObject("CartList", arrayListOf<ItemsModel>())}
}