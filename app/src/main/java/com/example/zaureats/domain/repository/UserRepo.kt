package com.example.zaureats.domain.repository

import androidx.compose.runtime.mutableStateOf
import com.example.zaureats.data.user.AddressDetails
import com.example.zaureats.data.PromoData
import com.example.zaureats.data.user.UserData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    val userData = mutableStateOf<UserData?>(null)
    private val promoData = mutableStateOf<PromoData?>(null)

    fun addUser(
        userId: String,
        name: String,
        surname: String,
        address: String? = null,
        phoneNumber: String? = null,
        onSuccess: () -> Unit
    ) {
        val userData = UserData(
            userId = userId,
            name = name,
            surname = surname,
            address = address,
            phoneNumber = phoneNumber
        )
        firestore.collection("users").document(userId).set(userData)
            .addOnSuccessListener {
                onSuccess()
            }
    }

    fun getUserData(userId: String) {
        firestore.collection("users").document(userId).get().addOnSuccessListener {
            userData.value = it.toObject<UserData>()
        }
    }

    fun updateUserData(
        userId: String? = userData.value?.userId,
        name: String? = userData.value?.name,
        surname: String? = userData.value?.surname,
        address: String? = userData.value?.address,
        addressDetails: AddressDetails? = userData.value?.addressDetails,
        phoneNumber: String? = userData.value?.phoneNumber,
        balance: Double? = userData.value?.balance,
        promoList: List<String>? = userData.value?.promoList,
        onSuccess: () -> Unit = {}
    ) {
        val user = UserData(
            userId = userId,
            name = name,
            surname = surname,
            address = address,
            addressDetails = addressDetails,
            phoneNumber = phoneNumber,
            balance = balance,
            promoList = promoList
        )
        firestore.collection("users").document(userId ?: "").update(user.toMap())
            .addOnSuccessListener {
                this.userData.value = user
                onSuccess()
            }
    }


    fun getPromo(
        code: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
        onAlreadyApplied: () -> Unit
    ) {
        firestore.collection("promos")
            .whereEqualTo("code", code)
            .addSnapshotListener { value, error ->
                val promoDocument = value?.firstOrNull()
                if (promoDocument != null) {
                    this.promoData.value = promoDocument.toObject()
                    updateUserData(
                        balance = userData.value?.balance?.plus(promoData.value?.price ?: 0.0),
                        promoList = if (userData.value?.promoList?.contains(code) == true){
                            onAlreadyApplied()
                            return@addSnapshotListener
                        }else{
                            userData.value?.promoList?.plus(code)
                        }

                    )

                    onSuccess()
                } else {
                    onFailure(Exception(error))
                }
            }
    }


}
