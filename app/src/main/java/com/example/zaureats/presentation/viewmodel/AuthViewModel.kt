package com.example.zaureats.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.zaureats.domain.repository.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo
): ViewModel() {

    val isAuthLoading = authRepo.isAuthLoading
    val isSignedIn = authRepo.isSignedIn
    val currentUserId = authRepo.currentUserId


    init {
        isSignedIn.value = authRepo.currentUser != null
    }

    fun signUp(
        name: String,
        surname: String,
        address: String,
        email: String,
        pass: String,
        confirmPass: String,
        context: Context,
        onSuccess: () -> Unit
    ) {
        authRepo.signUp(name, surname,address, email, pass, confirmPass, context, onSuccess)
    }

    fun signIn(email: String, pass: String, context: Context){
        authRepo.signIn(email, pass, context)
    }

    fun signOut(){
        authRepo.signOut()
    }

    fun forgotPassword(email: String, context: Context){
        authRepo.forgotPassword(email, context)
    }
}