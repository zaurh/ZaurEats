package com.example.zaureats.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zaureats.data.MealData
import com.example.zaureats.domain.repository.MealRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealRepo: MealRepo
): ViewModel() {

    var mealList = MutableLiveData<List<MealData>>()
    var tempBasketList = MutableLiveData<List<MealData>>()
    var selectedItem = mutableStateListOf<Map<String, String>>(mapOf())


    fun getMeals(restaurantId: String) {
        mealList = mealRepo.getMeals(restaurantId)
    }
}