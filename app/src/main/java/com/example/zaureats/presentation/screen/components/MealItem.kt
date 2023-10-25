package com.example.zaureats.presentation.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.zaureats.R
import com.example.zaureats.common.priceDiscount
import com.example.zaureats.data.ChoiceData
import com.example.zaureats.data.MealData
import com.example.zaureats.presentation.viewmodel.MealViewModel
import eu.wewox.modalsheet.ExperimentalSheetApi
import eu.wewox.modalsheet.ModalSheet
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@OptIn(ExperimentalSheetApi::class)
@Composable
fun MealItem(
    mealData: MealData,
    mealViewModel: MealViewModel,
    closedRest: Boolean = false
) {
    var bottomSheetState by remember { mutableStateOf(false) }
    var choiceTitle by remember { mutableStateOf<Map<String, String>>(mapOf()) }

    val list = mealViewModel.selectedItem.toList()
    var priceItem by remember { mutableStateOf(0) }

    val priceList = mutableListOf<Int>()

    val tempBasketList = mealViewModel.tempBasketList.observeAsState(listOf())


    val discountedPrice = priceDiscount(
        mealData.price ?: 0.0,
        mealData.hasDiscount?.toDouble() ?: 0.0
    )
    val formattedPrice =
        DecimalFormat("#.##", DecimalFormatSymbols(Locale.US)).format(discountedPrice)



    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    bottomSheetState = true
                }
                .padding(start = 20.dp, end = 20.dp)

        ) {

            ModalSheet(
                visible = bottomSheetState, onVisibleChange = {
                    bottomSheetState = it
                }) {

                list.forEach {
                    it.values.map { value ->
                        if (value.contains("zl")) {
                            val price = value.takeLast(4).dropLast(2).toIntOrNull()
                            if (price != null) {
                                priceList.add(price) // Add each price as an integer to the list
                            }
                        }
                    }
                }
                priceItem = priceList.sum()


                mealViewModel.selectedItem.clear()

                Column(
                    modifier = Modifier
                        .height(700.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop,
                        painter = rememberImagePainter(data = mealData.image),
                        contentDescription = ""
                    )
                    Column(Modifier.padding(20.dp)) {

                        Text(
                            text = "${mealData.name}",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        if (mealData.hasDiscount != 0) {
                            Row {
                                Text(
                                    text = "${mealData.price} zł",
                                    textDecoration = TextDecoration.LineThrough
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                Text(
                                    color = Color.White,
                                    modifier = Modifier
                                        .background(Color.Red)
                                        .padding(start = 5.dp, end = 5.dp),
                                    text = "$formattedPrice zł"
                                )
                            }
                        } else {
                            Text(text = "${mealData.price} zł", color = Color.Blue)
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                        Text(text = "${mealData.description}")
                    }

                    val uniqueCategories = mealData.choiceData?.distinctBy { it.title }

                    uniqueCategories?.forEach { it ->

                        Column(Modifier.padding(20.dp)) {
                            choiceTitle = mapOf(it.title.toString() to it.toString())

                            Text(
                                modifier = Modifier.padding(bottom = 10.dp),
                                text = "${it.title}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            val mealsWithCategory =
                                mealData.choiceData.filter { choice -> choice.title == it.title }
                            mealsWithCategory.forEach {
                                ChoiceItem(choiceData = it, mealViewModel = mealViewModel)
                            }
                        }
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var orderPrice by remember {
                        mutableStateOf("0")
                    }
                    val finalPrice = formattedPrice.toDouble() + priceItem

                    orderPrice = finalPrice.toString()

                    Button(enabled = !closedRest, colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.darkGreen)
                    ), onClick = {
                        val tempList = tempBasketList.value.toMutableList()
                        tempList.add(
                            MealData(
                                mealId = mealData.mealId ?: "",
                                restaurantId = mealData.restaurantId ?: "",
                                name = mealData.name ?: "",
                                category = mealData.category ?: "",
                                description = mealData.description ?: "",
                                price = formattedPrice.toDouble(),
                                hasDiscount = mealData.hasDiscount ?: 0,
                                image = mealData.image ?: "",
                                choiceDataSingle = ChoiceData(
                                    first = list.getOrNull(0) ?: mapOf(),
                                    second = list.getOrNull(1) ?: mapOf(),
                                    third = list.getOrNull(2) ?: mapOf(),
                                    fourth = list.getOrNull(3) ?: mapOf(),
                                    fifth = list.getOrNull(4) ?: mapOf(),
                                )
                            )
                        )
                        mealViewModel.tempBasketList.value = tempList
                        bottomSheetState = false
                    }) {
                        Text(text = "Add to order  ${orderPrice}zl")
                    }
                }
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "${mealData.name}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(
                        text = "${mealData.description}",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray,
                        modifier = Modifier.width(300.dp)
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    if (mealData.hasDiscount != 0) {
                        Row {
                            Text(
                                text = "${mealData.price} zł",
                                textDecoration = TextDecoration.LineThrough
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                color = Color.White,
                                modifier = Modifier
                                    .background(Color.Red)
                                    .padding(start = 5.dp, end = 5.dp),
                                text = "$formattedPrice zł"
                            )
                        }
                    } else {
                        Text(text = "$formattedPrice zł", color = Color.Blue)
                    }
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

}
