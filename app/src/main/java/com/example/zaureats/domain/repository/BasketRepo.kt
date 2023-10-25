package com.example.zaureats.domain.repository

import androidx.compose.runtime.mutableStateOf
import com.example.zaureats.data.BasketData
import com.example.zaureats.data.MealData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.UUID
import javax.inject.Inject

class BasketRepo @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    var basketData = mutableStateOf<BasketData?>(null)


    fun addToBasket(
        userId: String,
        mealList: List<MealData>,
        onSuccess: () -> Unit
    ) {
        val randomId = UUID.randomUUID().toString()

        val data = BasketData(
            basketId = randomId,
            mealData = mealList
        )

        firestore.collection("users").document(userId)
            .collection("basket").document(userId)
            .set(data).addOnSuccessListener {
                onSuccess()
            }
        getBasket(userId)
    }

    private fun getBasket(userId: String){
        firestore.collection("users").document(userId)
            .collection("basket").document(userId)
            .addSnapshotListener { value, _ ->
                if (value != null) {
                    basketData.value = value.toObject()
                }
            }
    }
}