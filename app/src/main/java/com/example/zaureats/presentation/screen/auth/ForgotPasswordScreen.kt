package com.example.zaureats.presentation.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zaureats.R
import com.example.zaureats.common.MyProgressBar
import com.example.zaureats.presentation.screen.auth.components.AuthErrorMessage
import com.example.zaureats.presentation.screen.auth.components.AuthTextField
import com.example.zaureats.presentation.viewmodel.AuthViewModel

@Composable
fun ForgotPasswordScreen(
    authViewModel: AuthViewModel
) {

    val isLoading = authViewModel.isAuthLoading.value
    val focus = LocalFocusManager.current
    var emailTf by remember { mutableStateOf("") }
    var emailTfError by remember { mutableStateOf(false) }

    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.White
            )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(30.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Forgot Password",
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.size(40.dp))
            Text(
                text = "Enter the email associated with your account and we'll send an email with instructions to reset your password.",
                color = Color.Gray,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.size(20.dp))
            AuthTextField(
                value = emailTf,
                onValueChange = { emailTf = it },
                onDone = {
                    if (emailTf.isEmpty()) {
                        emailTfError = true
                    } else {
                        authViewModel.forgotPassword(emailTf, context)
                        focus.clearFocus()
                    }
                },
                leadingIcon = Icons.Default.Email,
                placeHolder = "Email",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                errorTf = emailTfError
            )

            if (emailTfError) {
                AuthErrorMessage(text = "Please enter email.")
            }
            Spacer(modifier = Modifier.size(30.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.darkGreen)
                ),
                onClick = {
                    if (emailTf.isEmpty()) {
                        emailTfError = true
                    } else {
                        authViewModel.forgotPassword(emailTf, context)
                        focus.clearFocus()
                    }
                }) {
                Text(
                    text = "Send",
                    color = Color.White,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
            }

        }

        if (isLoading) {
            MyProgressBar()
        }
    }

}