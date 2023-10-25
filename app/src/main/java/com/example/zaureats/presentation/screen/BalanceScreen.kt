package com.example.zaureats.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.zaureats.R
import com.example.zaureats.presentation.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val userData = userViewModel.userData.value
    var promoTf by remember { mutableStateOf("") }
    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            }, title = { Text(text = "Balance", fontWeight = FontWeight.Bold) })
        },
        content = { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                Column(Modifier.padding(20.dp)) {
                    val formattedBalance = String.format("%.2f", userData?.balance)

                    Text(text = "Your balance is: ")
                    Text(
                        text = "$formattedBalance zl",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(40.dp))
                    Text(text = "If you have a promo-code, enter here:")
                    TextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = colorResource(id = R.color.lightGray),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(20))
                            .padding(top = 20.dp, bottom = 20.dp)
                            .fillMaxWidth(),
                        label = { Text(text = "Enter code") },
                        value = promoTf,
                        onValueChange = { promoTf = it }
                    )
                    Button(colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.darkGreen)
                    ),onClick = {
                        userViewModel.getPromo(
                            promoTf,
                            onSuccess = {
                                Toast.makeText(context, "Added.", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = {
                                Toast.makeText(context, "Promo not found.", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            onAlreadyApplied = {
                                Toast.makeText(context, "Already applied.", Toast.LENGTH_SHORT)
                                    .show()
                            },
                        )
                    }) {
                        Text(text = "Submit")
                    }

                }
            }
        },
        bottomBar = {

        }
    )
}