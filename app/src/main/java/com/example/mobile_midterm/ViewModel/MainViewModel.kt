package com.example.mobile_midterm.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mobile_midterm.Domain.CategoryModel
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.Repository.MainRepository

class MainViewModel: ViewModel() {

    private val repository = MainRepository()

    fun loadItems():LiveData<MutableList<ItemsModel>>{
        return repository.loadItems()
    }

    fun loadCategories(): LiveData<List<CategoryModel>> {
        return repository.loadCategories()

    }
}