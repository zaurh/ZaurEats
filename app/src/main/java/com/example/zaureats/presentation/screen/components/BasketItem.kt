package com.example.zaureats.presentation.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.zaureats.R
import com.example.zaureats.common.priceDiscount
import com.example.zaureats.data.MealData
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun BasketItem(
    mealData: MealData,
    onDelete: () -> Unit = {},
    deleteState: Boolean = false
) {
    var deleteAlert by remember { mutableStateOf(false) }

    val delete = SwipeAction(
        icon = rememberVectorPainter(Icons.Default.Delete),
        background = colorResource(id = R.color.red),
        onSwipe = {
            deleteAlert = true
        }
    )

    if (deleteAlert) {
        AlertDialog(
            onDismissRequest = {
                deleteAlert = false
            },
            title = {
                Text(text = "Are you sure?")
            },
            text = {
                Text(text = "Do you want to remove it from the basket?")
            },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.darkGreen)
                ), onClick = {
                    onDelete()
                    deleteAlert = false
                }) {
                    Text(text = "Yes, confirm.")
                }
            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.darkGreen)
                ), onClick = {
                    deleteAlert = false
                }) {
                    Text(text = "No, cancel.")
                }
            }
        )
    }

    SwipeableActionsBox(
        modifier = Modifier.fillMaxSize(),
        endActions = if (deleteState) listOf(delete) else listOf(),
        swipeThreshold = 40.dp,
        backgroundUntilSwipeThreshold = Color.Transparent
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val discountedPrice =
                priceDiscount(mealData.price ?: 0.0, mealData.hasDiscount?.toDouble() ?: 0.0)
            val choicePrice = remember { mutableStateListOf<Int>() }
            val sum = choicePrice.toList().sum()
            val total = discountedPrice + sum
            val formattedTotalPrice = String.format("%.2f", total)



            LaunchedEffect(key1 = true) {
                val choices = listOf(
                    mealData.choiceDataSingle?.first,
                    mealData.choiceDataSingle?.second,
                    mealData.choiceDataSingle?.third,
                    mealData.choiceDataSingle?.fourth,
                    mealData.choiceDataSingle?.fifth,
                )

                for (choice in choices) {
                    choice?.map {
                        val totalPrice = it.value.toString().takeLast(4).dropLast(2).toInt()
                        choicePrice.add(totalPrice)
                    }
                }
            }
            Column {
                Text(text = "${mealData.name}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    text = "${mealData.description}",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    modifier = Modifier.width(300.dp)
                )
                Spacer(modifier = Modifier.size(5.dp))

                Text(
                    color = Color.White,
                    modifier = Modifier
                        .background(colorResource(id = R.color.red))
                        .padding(start = 5.dp, end = 5.dp),
                    text = "$formattedTotalPrice zł"
                )
                Spacer(modifier = Modifier.size(5.dp))
                Row {
                    if (mealData.vegan == true) {
                        Text(text = "\uD83C\uDF43Vegan")
                    }
                    if (mealData.spicy == true) {
                        Text(text = "\uD83C\uDF36Spicy️")
                    }
                    if (mealData.lactoseFree == true) {
                        Text(text = "\uD83E\uDD5BLactose Free")
                    }
                }

            }
            Image(
                modifier = Modifier.size(50.dp),
                painter = rememberImagePainter(data = mealData.image),
                contentDescription = ""
            )
        }
    }
    Divider(Modifier.padding(top = 20.dp, bottom = 20.dp))
}