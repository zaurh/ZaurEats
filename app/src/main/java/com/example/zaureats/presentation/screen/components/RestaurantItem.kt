package com.example.zaureats.presentation.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.zaureats.common.NavParam
import com.example.zaureats.common.navigateTo
import com.example.zaureats.data.restaurant.RestaurantData

@Composable
fun RestaurantItem(
    restaurantData: RestaurantData,
    navController: NavController
) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(modifier = Modifier.clickable {
            navigateTo(navController, "restaurant_screen", NavParam("restData", restaurantData))
        }) {

            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                painter = rememberImagePainter(data = restaurantData.restaurantImage),
                contentDescription = ""
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${restaurantData.restaurantName}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(text = "\uD83D\uDD52 ${restaurantData.deliveryTime} min")
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp)
            ) {
                Text(text = "${restaurantData.deliveryPrice}zł • ")
                Text(text = "${restaurantData.rating}★")
            }
        }

    }
}