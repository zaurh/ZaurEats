package com.example.zaureats.presentation.screen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.zaureats.data.restaurant.RestaurantData

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantInfoScreen(
    navController: NavController,
    restaurantData: RestaurantData
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                }
            })
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                var radioRestaurantState by remember { mutableStateOf(true) }
                var radioDeliveryState by remember { mutableStateOf(false) }

                Column(
                    Modifier
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "${restaurantData.restaurantName}",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "${restaurantData.restaurantDescription}", fontSize = 20.sp)
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(text = "Address", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(text = "${restaurantData.restaurantAddress}")
                    Spacer(modifier = Modifier.size(40.dp))
                    Text(text = "Opening Hours", fontWeight = FontWeight.Bold, fontSize = 20.sp)


                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            Button(colors = ButtonDefaults.buttonColors(
                                containerColor = if (radioRestaurantState) Color.DarkGray else Color.LightGray
                            ), onClick = {
                                radioRestaurantState = true
                                radioDeliveryState = false
                            }) {
                                Text(text = "Restaurant")
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                            Button(colors = ButtonDefaults.buttonColors(
                                containerColor = if (radioDeliveryState) Color.DarkGray else Color.LightGray
                            ), onClick = {
                                radioRestaurantState = false
                                radioDeliveryState = true
                            }) {
                                Text(text = "Delivery")
                            }
                        }
                        Spacer(modifier = Modifier.size(30.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Monday: ")
                            if (restaurantData.openHours?.mondayIsClosed == true) {
                                Text(text = "CLOSED", color = Color.LightGray)
                            } else {
                                Text(text = if (radioRestaurantState) "${restaurantData.openHours?.mondayStart} - ${restaurantData.openHours?.mondayEnd}" else "${restaurantData.openForDelivery?.mondayStart} - ${restaurantData.openForDelivery?.mondayEnd}")
                            }
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Tuesday: ")
                            if (restaurantData.openHours?.tuesdayIsClosed == true) {
                                Text(text = "CLOSED", color = Color.LightGray)
                            } else {
                                Text(text = if (radioRestaurantState) "${restaurantData.openHours?.tuesdayStart} - ${restaurantData.openHours?.tuesdayEnd}" else "${restaurantData.openForDelivery?.tuesdayStart} - ${restaurantData.openForDelivery?.tuesdayEnd}")
                            }
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Wednesday: ")
                            if (restaurantData.openHours?.wednesdayIsClosed == true) {
                                Text(text = "CLOSED", color = Color.LightGray)
                            } else {
                                Text(text = if (radioRestaurantState) "${restaurantData.openHours?.wednesdayStart} - ${restaurantData.openHours?.wednesdayEnd}" else "${restaurantData.openForDelivery?.tuesdayStart} - ${restaurantData.openForDelivery?.tuesdayEnd}")
                            }
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Thursday: ")
                            if (restaurantData.openHours?.thursdayIsClosed == true) {
                                Text(text = "CLOSED", color = Color.LightGray)
                            } else {
                                Text(text = if (radioRestaurantState) "${restaurantData.openHours?.thursdayStart} - ${restaurantData.openHours?.thursdayEnd}" else "${restaurantData.openForDelivery?.tuesdayStart} - ${restaurantData.openForDelivery?.tuesdayEnd}")
                            }
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Friday: ")
                            if (restaurantData.openHours?.fridayIsClosed == true) {
                                Text(text = "CLOSED", color = Color.LightGray)
                            } else {
                                Text(text = if (radioRestaurantState) "${restaurantData.openHours?.fridayStart} - ${restaurantData.openHours?.fridayEnd}" else "${restaurantData.openForDelivery?.tuesdayStart} - ${restaurantData.openForDelivery?.tuesdayEnd}")
                            }
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Saturday: ")
                            if (restaurantData.openHours?.saturdayIsClosed == true) {
                                Text(text = "CLOSED", color = Color.LightGray)
                            } else {
                                Text(text = if (radioRestaurantState) "${restaurantData.openHours?.saturdayStart} - ${restaurantData.openHours?.saturdayEnd}" else "${restaurantData.openForDelivery?.tuesdayStart} - ${restaurantData.openForDelivery?.tuesdayEnd}")
                            }
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Sunday: ")
                            if (restaurantData.openHours?.sundayIsClosed == true) {
                                Text(text = "CLOSED", color = Color.LightGray)
                            } else {
                                Text(text = if (radioRestaurantState) "${restaurantData.openHours?.sundayStart} - ${restaurantData.openHours?.sundayEnd}" else "${restaurantData.openForDelivery?.tuesdayStart} - ${restaurantData.openForDelivery?.tuesdayEnd}")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(50.dp))
                    Text(
                        text = "Delivery Information",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Delivery cost")
                        Text(text = "${restaurantData.deliveryPrice}zł")
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Estimated delivery time")
                        Text(
                            text = "${restaurantData.deliveryTime}-${
                                restaurantData.deliveryTime?.toInt()?.plus(10)
                            } min"
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Restaurant rating")
                        Text(text = "${restaurantData.rating}★")
                    }

                    Spacer(modifier = Modifier.size(50.dp))
                    Text(text = "Contact details", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Restaurant contact")
                        Text(text = "${restaurantData.restaurantNumber}")
                    }


                }

            }
        },
        bottomBar = {

        }
    )

}