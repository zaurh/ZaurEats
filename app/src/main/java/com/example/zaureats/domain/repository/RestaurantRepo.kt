package com.example.zaureats.domain.repository

import androidx.compose.runtime.mutableStateOf
import com.example.zaureats.data.restaurant.RestaurantData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import javax.inject.Inject

class RestaurantRepo @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    var restaurantList = mutableStateOf<List<RestaurantData>>(listOf())

    init {
        getRestaurants()
    }

    private fun getRestaurants() {
        firestore.collection("restaurants").addSnapshotListener { value, _ ->
            if (value != null) {
                restaurantList.value = value.toObjects()
            }
        }
    }
}