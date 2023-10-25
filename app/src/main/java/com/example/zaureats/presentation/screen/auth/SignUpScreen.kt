package com.example.zaureats.presentation.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.zaureats.R
import com.example.zaureats.common.MyCheckSignedIn
import com.example.zaureats.common.MyProgressBar
import com.example.zaureats.presentation.screen.auth.components.AuthTextField
import com.example.zaureats.presentation.screen.auth.components.AuthErrorMessage
import com.example.zaureats.presentation.screen.auth.components.AuthPassTrailingIcon
import com.example.zaureats.presentation.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    MyCheckSignedIn(navController = navController, authViewModel = authViewModel)

    val context = LocalContext.current
    val isLoading = authViewModel.isAuthLoading.value
    val focus = LocalFocusManager.current

    var nameTf by remember { mutableStateOf("") }
    var nameTfError by remember { mutableStateOf(false) }

    var surnameTf by remember { mutableStateOf("") }

    var addressTf by remember { mutableStateOf("") }
    var addressTfError by remember { mutableStateOf(false) }

    var emailTf by remember { mutableStateOf("") }
    var emailTfError by remember { mutableStateOf(false) }

    var passwordTf by remember { mutableStateOf("") }
    var passwordTfError by remember { mutableStateOf(false) }

    var confirmPasswordTf by remember { mutableStateOf("") }
    var confirmPasswordTfError by remember { mutableStateOf(false) }

    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
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
                text = "Sign up",
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.size(40.dp))
            Row() {
                AuthTextField(
                    modifier = Modifier.weight(5f),
                    errorTf = nameTfError,
                    value = nameTf,
                    onValueChange = { nameTf = it },
                    onDone = {
                        focus.clearFocus()
                    },
                    leadingIcon = Icons.Default.Person,
                    placeHolder = "Name"
                )
                Spacer(modifier = Modifier.size(8.dp))
                AuthTextField(
                    modifier = Modifier.weight(5f),
                    value = surnameTf,
                    onValueChange = { surnameTf = it },
                    onDone = {
                        focus.clearFocus()
                    },
                    placeHolder = "Surname"
                )
            }
            if (nameTfError) {
                AuthErrorMessage(text = "Please enter your name.")
            }

            Spacer(modifier = Modifier.size(8.dp))
            AuthTextField(
                errorTf = addressTfError,
                value = addressTf,
                onValueChange = { addressTf = it },
                onDone = {
                    focus.clearFocus()
                },
                leadingIcon = Icons.Default.MyLocation,
                placeHolder = "Address"
            )
            if (addressTfError) {
                AuthErrorMessage(text = "Please enter your address.")
            }
            Spacer(modifier = Modifier.size(8.dp))
            AuthTextField(
                errorTf = emailTfError,
                value = emailTf,
                onValueChange = { emailTf = it },
                onDone = {
                    focus.clearFocus()
                },
                placeHolder = "Email",
                leadingIcon = Icons.Default.Email
            )
            if (emailTfError) {
                AuthErrorMessage(text = "Please enter email.")
            }
            Spacer(modifier = Modifier.size(8.dp))
            AuthTextField(
                errorTf = passwordTfError,
                value = passwordTf,
                onValueChange = { passwordTf = it },
                onDone = {
                    focus.clearFocus()
                },
                placeHolder = "Password",
                leadingIcon = Icons.Default.Lock,
                passwordVisibility = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    AuthPassTrailingIcon(
                        error = passwordTfError,
                        onClick = {
                            passwordVisibility = !passwordVisibility
                        },
                        visibility = passwordVisibility
                    )
                }
            )
            if (passwordTfError) {
                AuthErrorMessage(text = "Please enter password.")
            }
            Spacer(modifier = Modifier.size(8.dp))
            AuthTextField(
                errorTf = confirmPasswordTfError,
                value = confirmPasswordTf,
                onValueChange = { confirmPasswordTf = it },
                onDone = {
                    focus.clearFocus()
                },
                placeHolder = "Confirm Password",
                leadingIcon = Icons.Default.Lock,
                passwordVisibility = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    AuthPassTrailingIcon(
                        error = confirmPasswordTfError,
                        onClick = {
                            confirmPasswordVisibility = !confirmPasswordVisibility
                        },
                        visibility = confirmPasswordVisibility
                    )
                }
            )
            if (confirmPasswordTfError) {
                AuthErrorMessage(text = "Please enter password again.")
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
                    nameTfError = nameTf.isEmpty()
                    addressTfError = addressTf.isEmpty()
                    emailTfError = emailTf.isEmpty()
                    passwordTfError = passwordTf.isEmpty()
                    confirmPasswordTfError = confirmPasswordTf.isEmpty()
                    if (!nameTfError && !addressTfError && !emailTfError && !passwordTfError && !confirmPasswordTfError) {
                        authViewModel.signUp(
                            name = nameTf,
                            surname = surnameTf,
                            address = addressTf,
                            email = emailTf,
                            pass = passwordTf,
                            confirmPass = confirmPasswordTf,
                            context = context,
                            onSuccess = {

                            }
                        )
                    }
                    focus.clearFocus()
                }) {
                Text(
                    text = "Sign up",
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



