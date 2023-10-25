package com.example.zaureats.presentation.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.zaureats.R
import com.example.zaureats.common.NavParam
import com.example.zaureats.common.navigateTo
import com.example.zaureats.data.order.OrderData
import com.example.zaureats.presentation.viewmodel.OrderViewModel
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun OrderItem(
    orderData: OrderData,
    navController: NavController,
    orderViewModel: OrderViewModel
) {
    val basketData = orderData.basketData
    val restaurantData = orderData.restaurantData

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                orderViewModel.getOrder(
                    restId = restaurantData?.restaurantId ?: "",
                    basketId = basketData?.basketId ?: "",
                    onSuccess = { orderData ->
                        navigateTo(
                            navController,
                            "delivery_screen",
                            NavParam("orderData", orderData)
                        )
                    }
                )
            }
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                Modifier
                    .padding(bottom = 20.dp, top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(20)),
                    contentScale = ContentScale.Crop,
                    painter = rememberImagePainter(restaurantData?.restaurantImage),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.size(10.dp))
                Column {
                    Text(text = "${restaurantData?.restaurantName}")
                    Text(text = "${orderData.price} zl")
                    Row {
                        Text(
                            text = myFormatDate(orderData.time ?: Timestamp.now()),
                            fontSize = 12.sp
                        )
                        if (orderData.orderStatus?.ready == true && orderData.orderStatus.accepted == true) {
                            Text(
                                text = " | Ready",
                                color = colorResource(id = R.color.darkGreen),
                                fontSize = 12.sp
                            )
                        } else if (orderData.orderStatus?.accepted == true && orderData.orderStatus.ready == false) {
                            Text(text = " | Being prepared...", fontSize = 12.sp)
                        } else if (orderData.orderStatus?.rejected == true) {
                            Text(
                                text = " | Rejected",
                                color = colorResource(id = R.color.red),
                                fontSize = 12.sp
                            )
                        } else if (orderData.orderStatus?.accepted == false && orderData.orderStatus.rejected == false) {
                            Text(text = " | Waiting...", fontSize = 12.sp)
                        }
                    }
                }
            }
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
        }

        Divider(thickness = 0.7.dp)

    }
}


private fun myFormatDate(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("dd MMMM  HH:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}