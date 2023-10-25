package com.example.zaureats.presentation.screen

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.example.zaureats.data.order.OrderData
import com.example.zaureats.presentation.screen.components.BasketItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryScreen(
    orderData: OrderData,
    navController: NavController
) {

    val dialerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){}
    val context = LocalContext.current

    BackHandler(onBack = {
       navController.navigate("order_history_screen"){
           popUpTo("main")
       }
    })

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            }, title = { Text(text = "Delivery", fontWeight = FontWeight.Bold) })
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .padding(20.dp)
                        .clip(RoundedCornerShape(20))
                ) {
                    Column(
                        Modifier
                            .padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Your order status", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.size(50.dp))
                        if (orderData.orderStatus?.rejected == true){
                            Text(
                                text = "Restaurant rejected your order.",
                                color = colorResource(id = R.color.red),
                                fontSize = 16.sp
                            )
                        }else{
                            Text(
                                text = "Restaurant received your order.",
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }
                        
                        Text(
                            text = "Your order is preparing...",
                            color = if (orderData.orderStatus?.accepted == true) {
                                Color.Black
                            } else {
                                Color.LightGray
                            },
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Your order is ready.",
                            color = if (orderData.orderStatus?.ready == true) {
                                Color.Black
                            } else {
                                Color.LightGray
                            },
                            fontSize = 16.sp
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .padding(20.dp)
                        .clip(RoundedCornerShape(20))
                ) {
                    Row(modifier = Modifier
                        .background(colorResource(id = R.color.darkGreen))
                        .clickable {
                            val uri = Uri.parse("tel:${orderData.restaurantData?.restaurantNumber}")
                            val dialIntent = Intent(Intent.ACTION_DIAL, uri)

                            try {
                                dialerLauncher.launch(dialIntent)
                            } catch (e: ActivityNotFoundException) {
                                Toast
                                    .makeText(context, "Dial app not found.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        .padding(20.dp)) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = "", tint = Color.White)
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(text = "Call restaurant", color = Color.White)
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Order list", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.size(20.dp))
                    orderData.basketData?.mealData?.forEach{ mealData ->
                        BasketItem(mealData = mealData)
                    }
                }


            }
        },
        bottomBar = {

        }
    )
}
