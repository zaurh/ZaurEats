package com.example.zaureats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.zaureats.data.MealData
import com.example.zaureats.domain.repository.BasketRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val basketRepo: BasketRepo
): ViewModel() {

    var basketData = basketRepo.basketData


    fun addToBasket(
        userId: String,
        mealData: List<MealData>,
        onSuccess: () -> Unit
    ) {
        basketRepo.addToBasket(
            userId, mealData, onSuccess
        )
    }
}