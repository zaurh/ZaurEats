package com.example.zaureats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.zaureats.data.user.AddressDetails
import com.example.zaureats.domain.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {

    val userData = userRepo.userData

    fun updateUserData(
        userId: String? = userData.value?.userId,
        name: String? = userData.value?.name,
        surname: String? = userData.value?.surname,
        address: String? = userData.value?.address,
        addressDetails: AddressDetails? = userData.value?.addressDetails,
        phoneNumber: String? = userData.value?.phoneNumber,
        balance: Double? = userData.value?.balance,
        promoList: List<String>? = userData.value?.promoList,
        onSuccess: () -> Unit
    ) {
        userRepo.updateUserData(
            userId = userId ?: "",
            name = name ?: "",
            surname = surname ?: "",
            address = address ?: "",
            addressDetails = addressDetails ?: AddressDetails(),
            phoneNumber = phoneNumber ?: "",
            balance = balance ?: 0.0,
            promoList = promoList ?: listOf(),
            onSuccess = onSuccess
        )
    }

    fun getPromo(
        code: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
        onAlreadyApplied: () -> Unit
    ) {
        userRepo.getPromo(code, onSuccess, onFailure, onAlreadyApplied)
    }

    fun getUserData(userId: String) {
        userRepo.getUserData(userId = userId)
    }
}