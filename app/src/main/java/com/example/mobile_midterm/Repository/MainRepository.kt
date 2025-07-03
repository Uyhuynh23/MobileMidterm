package com.example.mobile_midterm.Repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobile_midterm.Domain.ItemsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainRepository {
    fun loadItems(): LiveData<MutableList<ItemsModel>> {
        val listData = MutableLiveData<MutableList<ItemsModel>>()

        // Create dummy items manually
        val sampleItems = mutableListOf<ItemsModel>()

        sampleItems.add(
            ItemsModel(
                title = "Cappuccino",
                description = "Rich espresso with steamed milk",
                picUrl = arrayListOf("@drawable/cappuccino"),
                price = 3.00,
                rating = 4.5,
                points = 12,
                pointsRedeem = 1340
            )
        )

        sampleItems.add(
            ItemsModel(
                title = "Americano",
                description = "Espresso with hot water",
                picUrl = arrayListOf("@drawable/americano"),
                price = 2.50,
                rating = 4.2,
                points = 12,
                pointsRedeem = 1340
            )
        )

        sampleItems.add(
            ItemsModel(
                title = "Mocha",
                description = "Espresso with chocolate and milk",
                picUrl = arrayListOf("@drawable/mocha"),
                price = 3.80,
                rating = 4.6,
                points = 12,
                pointsRedeem = 1340
            )
        )

        sampleItems.add(
            ItemsModel(
                title= "Flat White",
                description = "Espresso with steamed milk",
                picUrl = arrayListOf("@drawable/flatwhite"),
                price = 3.50,
                rating = 4.3,
                points = 12,
                pointsRedeem = 1340
            )
        )

        listData.value = sampleItems

        return listData
    }



    ////Firebase GET DATA
//    private val firebaseDatabase = FirebaseDatabase.getInstance()
//
//    fun loadItems(): LiveData<MutableList<ItemsModel>> {
//        val listData = MutableLiveData<MutableList<ItemsModel>>()
//        val ref = firebaseDatabase.getReference("Popular")
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val list = mutableListOf<ItemsModel>()
//                for (childSnapshot in snapshot.children) {
//                    val item = childSnapshot.getValue(ItemsModel::class.java)
//                    item?.let { list.add(it) }
//                }
//                listData.value = list
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//           return listData
//    }
//


        }



