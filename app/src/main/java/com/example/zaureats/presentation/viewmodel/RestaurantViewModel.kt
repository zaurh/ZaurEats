package com.example.zaureats.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaureats.data.restaurant.RestaurantData
import com.example.zaureats.domain.repository.RestaurantRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    restaurantRepo: RestaurantRepo
): ViewModel() {

    val restaurantList = restaurantRepo.restaurantList
    var restaurantData: RestaurantData? by mutableStateOf(null)

    private var isSearchStarting = true
    private var initialRestaurants = listOf<RestaurantData>()


    //Search Users
    fun searchList(query: String) {
        val listToSearch = if (isSearchStarting) {
            restaurantList.value
        } else {
            initialRestaurants
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                restaurantList.value = initialRestaurants
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.restaurantTags?.contains(
                    query.trim(),
                    ignoreCase = true
                ) == true || it.restaurantName!!.contains(
                    query.trim(),
                    ignoreCase = true
                )
            }
            if (isSearchStarting) {
                initialRestaurants = restaurantList.value
                isSearchStarting = false
            }
            restaurantList.value = results
        }
    }

    fun clearSearch() {
        restaurantList.value = initialRestaurants
        isSearchStarting = true
    }
}