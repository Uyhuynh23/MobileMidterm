package com.example.mobile_midterm.Repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobile_midterm.Domain.CategoryModel
import com.example.mobile_midterm.Domain.ItemsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainRepository {
    fun loadItems(): LiveData<MutableList<ItemsModel>> {
        val listData = MutableLiveData<MutableList<ItemsModel>>()

        val sampleItems = mutableListOf<ItemsModel>(
            ItemsModel(
                title = "Cappuccino",
                description = "Espresso with steamed milk foam",
                picUrl = arrayListOf("@drawable/cappuccino"),
                price = 3.50,
                rating = 4.5,
                points = 12,
                pointsRedeem = 1340,
                category = "Coffee"
            ),
            ItemsModel(
                title = "Americano",
                description = "Espresso diluted with hot water",
                picUrl = arrayListOf("@drawable/americano"),
                price = 2.80,
                rating = 4.2,
                points = 10,
                pointsRedeem = 1200,
                category = "Coffee"
            ),
            ItemsModel(
                title = "Mocha",
                description = "Espresso with chocolate and steamed milk",
                picUrl = arrayListOf("@drawable/mocha"),
                price = 4.20,
                rating = 4.6,
                points = 14,
                pointsRedeem = 1500,
                category = "Coffee"
            ),
            ItemsModel(
                title = "Flat White",
                description = "Espresso with microfoam milk",
                picUrl = arrayListOf("@drawable/flatwhite"),
                price = 3.80,
                rating = 4.3,
                points = 13,
                pointsRedeem = 1400,
                category = "Coffee"
            ),
            ItemsModel(
                title = "Green Tea Latte",
                description = "Matcha with steamed milk and light foam",
                picUrl = arrayListOf("@drawable/greentea"),
                price = 3.60,
                rating = 4.4,
                points = 11,
                pointsRedeem = 1250,
                category = "Tea"
            ),
            ItemsModel(
                title = "Peach Fruit Tea",
                description = "Refreshing peach-infused fruit tea",
                picUrl = arrayListOf("@drawable/peachtea"),
                price = 3.20,
                rating = 4.1,
                points = 10,
                pointsRedeem = 1150,
                category = "Fruit Tea"
            )
        )

        listData.value = sampleItems
        return listData
    }

    fun loadCategories(): LiveData<List<CategoryModel>> {
        val categoryList = listOf(
            CategoryModel("All", true),
            CategoryModel("Coffee"),
            CategoryModel("Tea"),
            CategoryModel("Fruit Tea"),
            CategoryModel("Favorite")
        )

        val data = MutableLiveData<List<CategoryModel>>()
        data.value = categoryList
        return data
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



