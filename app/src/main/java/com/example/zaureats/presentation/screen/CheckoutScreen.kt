package com.example.zaureats.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.example.zaureats.common.NavParam
import com.example.zaureats.common.navigateTo
import com.example.zaureats.data.user.AddressDetails
import com.example.zaureats.data.BasketData
import com.example.zaureats.data.order.OrderStatus
import com.example.zaureats.presentation.viewmodel.BasketViewModel
import com.example.zaureats.presentation.viewmodel.OrderViewModel
import com.example.zaureats.presentation.viewmodel.RestaurantViewModel
import com.example.zaureats.presentation.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    orderViewModel: OrderViewModel,
    basketViewModel: BasketViewModel,
    restaurantViewModel: RestaurantViewModel,
    userViewModel: UserViewModel,
    totalPrice: String
) {

    val context = LocalContext.current
    val userData = userViewModel.userData.value
    val restaurantData = restaurantViewModel.restaurantData
    val basketData = basketViewModel.basketData.value

    val totalPriceDouble = totalPrice.replace(",", ".").toDoubleOrNull() ?: 0.0
    val deliveryPrice = totalPriceDouble + restaurantData?.deliveryPrice?.toDouble()!!
    val formattedDeliveryPrice = String.format("%.2f", deliveryPrice)


    var alertState by remember { mutableStateOf(false) }

    var entranceTf by remember { mutableStateOf(userData?.addressDetails?.entrance ?: "") }
    var floorTf by remember { mutableStateOf(userData?.addressDetails?.floor ?: "") }
    var apartmentTf by remember { mutableStateOf(userData?.addressDetails?.apartment ?: "") }


    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            }, title = { Text(text = "Checkout") })
        },
        content = { it ->
            if (alertState) {
                AlertDialog(
                    onDismissRequest = {
                        alertState = false
                    },
                    title = {
                        Text(text = "Are you sure?")
                    },
                    text = {
                        Text(text = "Do you confirm to order?\nTotal price is $formattedDeliveryPrice zl")
                    },
                    confirmButton = {
                        Button(colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.darkGreen)
                        ), onClick = {
                            if (userData?.balance!! > deliveryPrice) {
                                orderViewModel.addOrder(
                                    restaurantData = restaurantData,
                                    userData = userData,
                                    basketData = basketData ?: BasketData(),
                                    orderStatus = OrderStatus(),
                                    price = formattedDeliveryPrice
                                ) {
                                    userViewModel.updateUserData(
                                        balance = userData.balance.toDouble() - deliveryPrice,
                                        onSuccess = {

                                        }
                                    )
                                }

                                orderViewModel.getOrder(
                                    restId = restaurantData.restaurantId ?: "",
                                    basketId = basketData?.basketId ?: "",
                                    onSuccess = { orderData ->
                                        navigateTo(
                                            navController,
                                            "delivery_screen",
                                            NavParam("orderData", orderData)
                                        )
                                    }
                                )


                            } else {
                                Toast.makeText(
                                    context,
                                    "You don't have enough balance.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                alertState = false
                            }

                        }) {
                            Text(text = "Yes, confirm.")
                        }
                    },
                    dismissButton = {
                        Button(colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.darkGreen)
                        ), onClick = {
                            alertState = false
                        }) {
                            Text(text = "No, cancel.")
                        }
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                Column(Modifier.padding(20.dp)) {
                    val formattedBalance = String.format("%.2f", userData?.balance)

                    Text(text = "Address", fontWeight = FontWeight.Bold)
                    Text(text = userData?.address ?: "")
                    Spacer(modifier = Modifier.size(30.dp))
                    Text(text = "Balance", fontWeight = FontWeight.Bold)
                    Text(text = "$formattedBalance zl")
                    Spacer(modifier = Modifier.size(30.dp))
                    Text(text = "Details", fontWeight = FontWeight.Bold)
                    CheckoutTextField(
                        text = "Entrance / Gate",
                        value = entranceTf,
                        onValueChange = { entranceTf = it },
                    )
                    Row {
                        CheckoutTextField(
                            text = "Floor",
                            value = floorTf,
                            onValueChange = { floorTf = it },
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .weight(5f)
                        )
                        CheckoutTextField(
                            text = "Apartment",
                            value = apartmentTf,
                            onValueChange = { apartmentTf = it },
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .weight(5f)
                        )
                    }
                    Spacer(modifier = Modifier.size(30.dp))

                    Text(text = "Prices", fontWeight = FontWeight.Bold)

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Meal")
                        Text(text = "$totalPrice zl")
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Delivery")
                        Text(text = "${restaurantData.deliveryPrice.toDouble()} zl")
                    }
                    Divider()
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(text = "Total")
                        Text(text = "$formattedDeliveryPrice zl")

                    }

                }
            }
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            colorResource(
                                id = R.color.darkGreen
                            )
                        )
                        .clickable {
                            userViewModel.updateUserData(
                                addressDetails = AddressDetails(
                                    entrance = entranceTf,
                                    floor = floorTf,
                                    apartment = apartmentTf
                                ), onSuccess = {

                                }
                            )

                            alertState = true
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(text = "Confirm order", fontSize = 20.sp, color = Color.White)
                        Text(
                            text = "$formattedDeliveryPrice zl",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp, color = Color.White
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun CheckoutTextField(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = colorResource(id = R.color.lightGray),
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .clip(RoundedCornerShape(20))
            .padding(top = 10.dp)
            .fillMaxWidth(),
        label = { Text(text = text) },
        value = value,
        onValueChange = onValueChange
    )
}