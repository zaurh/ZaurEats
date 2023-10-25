package com.example.zaureats.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zaureats.R
import com.example.zaureats.common.NavParam
import com.example.zaureats.common.navigateTo
import com.example.zaureats.data.user.UserData
import com.example.zaureats.presentation.viewmodel.AuthViewModel
import com.example.zaureats.presentation.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {
    val userData = userViewModel.userData.value
    val uriHandler = LocalUriHandler.current


    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            }, title = { Text(text = "Profile", fontWeight = FontWeight.Bold) })
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileItems(
                    icon = Icons.Default.ShoppingBasket,
                    text = "Order history",
                    onClick = {
                        navController.navigate("order_history_screen")
                    }
                )
                ProfileItems(icon = Icons.Default.Settings, text = "Settings", onClick = {
                    navigateTo(
                        navController,
                        "settings_screen",
                        NavParam("userData", userData ?: UserData())
                    )
                })
                ProfileItems(icon = Icons.Default.AccountBalanceWallet, text = "Balance") {
                    navController.navigate("balance_screen")
                }
                ProfileItems(icon = Icons.Default.Info, text = "About application", onClick = {
                    uriHandler.openUri("https://github.com/zaurh/zaureats")
                })
                Spacer(modifier = Modifier.size(40.dp))
                ProfileItems(
                    icon = Icons.Default.Logout, text = "Sign out", textColor = colorResource(
                        id = R.color.red
                    )
                ) {
                    authViewModel.signOut()
                    navController.navigate("sign_in_screen"){
                        popUpTo(0)
                    }
                }
            }
        },
        bottomBar = {

        }
    )
}


@Composable
fun ProfileItems(
    icon: ImageVector,
    text: String,
    textColor: Color = Color.Black,
    onClick: () -> Unit
) {
    Surface(
        Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Icon(imageVector = icon, contentDescription = "", tint = textColor)
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = text, color = textColor
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = textColor
            )
        }

    }
}

