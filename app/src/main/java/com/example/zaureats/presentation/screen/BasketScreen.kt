package com.example.zaureats.presentation.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.zaureats.R
import com.example.zaureats.common.priceDiscount
import com.example.zaureats.presentation.screen.components.BasketItem
import com.example.zaureats.presentation.viewmodel.AuthViewModel
import com.example.zaureats.presentation.viewmodel.BasketViewModel
import com.example.zaureats.presentation.viewmodel.MealViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BasketScreen(
    navController: NavController,
    mealViewModel: MealViewModel,
    basketViewModel: BasketViewModel,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current
    val userId = authViewModel.currentUserId
    val tempBasket = mealViewModel.tempBasketList.observeAsState(mutableListOf())

    val choicePrice = remember { mutableStateListOf<Int>() }
    val discountedPriceList = mutableListOf<Double>()
    val sum = choicePrice.toList().sum()

    tempBasket.value.forEach { mealData ->
        val discountedPrice =
            priceDiscount(mealData.price ?: 0.0, mealData.hasDiscount?.toDouble() ?: 0.0)
        discountedPriceList.add(discountedPrice)
    }
    val priceSum = discountedPriceList.sum()
    val total = sum + priceSum
    val formattedTotal = String.format("%.2f", total)





    LaunchedEffect(key1 = true) {
        val choices = listOf(
            tempBasket.value.map { it.choiceDataSingle?.first },
            tempBasket.value.map { it.choiceDataSingle?.second },
            tempBasket.value.map { it.choiceDataSingle?.third },
            tempBasket.value.map { it.choiceDataSingle?.fourth },
            tempBasket.value.map { it.choiceDataSingle?.fifth }
        )

        for (choice in choices) {
            choice.map {
                if (it != null) {
                    for (i in it) {
                        val totalPrice = i.value.toString().takeLast(4).dropLast(2).toInt()
                        choicePrice.add(totalPrice)
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            }, title = { Text(text = "Your order") })
        },
        content = { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                LazyColumn(modifier = Modifier.padding(20.dp)) {
                    items(tempBasket.value) {
                        BasketItem(mealData = it, onDelete = {
                            val mutableTempBasket = tempBasket.value.toMutableList()
                            mutableTempBasket.remove(it)
                            mealViewModel.tempBasketList.value = mutableTempBasket
                        }, deleteState = true)
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
                            if (tempBasket.value.isNotEmpty()) {

                                navController.navigate("checkout_screen/$formattedTotal")
                                basketViewModel.addToBasket(
                                    userId = userId ?: "null",
                                    mealData = tempBasket.value,
                                    onSuccess = {

                                    }
                                )
                            } else {
                                Toast
                                    .makeText(context, "Your basket is empty.", Toast.LENGTH_SHORT)
                                    .show()
                            }


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
                        Text(text = "Go to checkout", fontSize = 20.sp, color = Color.White)
                        Text(
                            text = "Total: $formattedTotal zl",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    )

}