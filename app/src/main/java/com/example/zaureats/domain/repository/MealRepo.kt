package com.example.zaureats.domain.repository

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.example.zaureats.data.MealData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import javax.inject.Inject

class MealRepo @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private var mealList = MutableLiveData<List<MealData>>(emptyList())
    var mealData = mutableStateOf<MealData?>(null)


    init {
        mealList = MutableLiveData()
    }

    fun getMeals(restaurantId: String): MutableLiveData<List<MealData>> {
        firestore.collection("restaurants").document(restaurantId)
            .collection("meals")
            .addSnapshotListener { value, _ ->
                if (value != null) {
                    mealList.value = value.toObjects()
                }
            }
        return mealList
    }
}