package com.example.zaureats.presentation.screen

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.zaureats.R
import com.example.zaureats.common.NavParam
import com.example.zaureats.common.navigateTo
import com.example.zaureats.data.restaurant.RestaurantData
import com.example.zaureats.presentation.screen.components.MealItem
import com.example.zaureats.presentation.viewmodel.MealViewModel
import com.example.zaureats.presentation.viewmodel.RestaurantViewModel
import eu.wewox.modalsheet.ExperimentalSheetApi
import eu.wewox.modalsheet.ModalSheet
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RestaurantScreen(
    navController: NavController,
    restaurantData: RestaurantData,
    mealViewModel: MealViewModel,
    restaurantViewModel: RestaurantViewModel,
) {
    restaurantViewModel.restaurantData = restaurantData


    var restaurantIsClosed by remember { mutableStateOf(false) }

    val mealList = mealViewModel.mealList.observeAsState(listOf())
    val state = rememberCollapsingToolbarScaffoldState()
    var dropDownState by remember { mutableStateOf(false) }
    var clearBasketAlert by remember { mutableStateOf(false) }


    val tempBasketList = mealViewModel.tempBasketList.observeAsState(listOf())
    println("tempBasketList: $tempBasketList")

    LaunchedEffect(key1 = true) {
        mealViewModel.getMeals(restaurantId = restaurantData.restaurantId ?: "")
    }

    BackHandler(enabled = true, onBack = {
        if (tempBasketList.value.isNotEmpty()) {
            clearBasketAlert = true
        } else {
            navController.popBackStack()
        }
    })

    if (clearBasketAlert) {
        AlertDialog(
            onDismissRequest = { clearBasketAlert = false },
            title = {
                Text(text = "Choose other restaurants?")
            },
            text = {
                Text(text = "It will clear your current basket.")
            },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.darkGreen)
                ), onClick = {
                    navController.popBackStack()
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.darkGreen)
                ), onClick = {
                    clearBasketAlert = false
                }) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            val textSize = (18 + (30 - 18) * state.toolbarState.progress).sp
            val imageSize = (150 + 150 * state.toolbarState.progress).dp
            val imageBlur = (14 - 14 * state.toolbarState.progress).dp

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
                    .height(150.dp)
                    .pin()
            )
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .blur(imageBlur)
                    .fillMaxWidth()
                    .height(imageSize)
                    .pin(),
                painter = rememberImagePainter(data = restaurantData.restaurantImage),
                contentDescription = null
            )
            Text(
                text = "",
                modifier = Modifier
                    .road(Alignment.CenterStart, Alignment.BottomEnd)
                    .padding(60.dp, 16.dp, 16.dp, 16.dp),
                color = Color.White,
                fontSize = textSize
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        if (tempBasketList.value.isNotEmpty()) {
                            clearBasketAlert = true
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }

                    Text(
                        modifier = Modifier.padding(20.dp),
                        text = restaurantData.restaurantName ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                    IconButton(onClick = {
                        dropDownState = true
                    }) {
                        DropdownMenu(
                            expanded = dropDownState,
                            onDismissRequest = { dropDownState = false }) {
                            Text(text = "More info", modifier = Modifier
                                .clickable {
                                    navigateTo(
                                        navController,
                                        "restaurant_info_screen",
                                        NavParam("restData", restaurantData)
                                    )
                                }
                                .padding(start = 10.dp, end = 10.dp))
                        }
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                    }
                }
                if (tempBasketList.value.isNotEmpty()) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20))
                            .clickable {
                                navController.navigate("basket_screen")
                            }
                            .background(colorResource(id = R.color.darkGreen))
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "View basket",
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }

            }

            LazyColumn {
                item {
                    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                        val currentDate = LocalDate.now()

                        val dayOfWeek = currentDate.dayOfWeek.getDisplayName(
                            TextStyle.FULL,
                            Locale.ENGLISH
                        )

                        Spacer(modifier = Modifier.size(10.dp))
                        Text(text = restaurantData.restaurantDescription ?: "")
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(Modifier.padding(bottom = 10.dp)) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = "")
                            Text(text = restaurantData.rating ?: "")
                        }
                        Row(Modifier.padding(bottom = 10.dp)) {
                            Icon(imageVector = Icons.Default.Info, contentDescription = "")
                            when (dayOfWeek) {
                                "Monday" -> {
                                    if (restaurantData.openHours?.mondayIsClosed != true) {
                                        Text(text = "${restaurantData.openHours?.mondayStart} - ${restaurantData.openHours?.mondayEnd}")
                                    } else {
                                        restaurantIsClosed = true
                                        Text(text = "Restaurant is CLOSED.")
                                    }
                                }

                                "Tuesday" -> {
                                    if (restaurantData.openHours?.tuesdayIsClosed != true) {
                                        Text(text = "${restaurantData.openHours?.tuesdayStart} - ${restaurantData.openHours?.tuesdayEnd}")
                                    } else {
                                        restaurantIsClosed = true
                                        Text(text = "Restaurant is CLOSED.")
                                    }
                                }

                                "Wednesday" -> {

                                    if (restaurantData.openHours?.wednesdayIsClosed != true) {
                                        Text(text = "${restaurantData.openHours?.wednesdayStart} - ${restaurantData.openHours?.wednesdayEnd}")
                                    } else {
                                        restaurantIsClosed = true
                                        Text(text = "Restaurant is CLOSED.")
                                    }
                                }

                                "Thursday" -> {
                                    if (restaurantData.openHours?.thursdayIsClosed != true) {
                                        Text(text = "${restaurantData.openHours?.thursdayStart} - ${restaurantData.openHours?.thursdayEnd}")
                                    } else {
                                        restaurantIsClosed = true
                                        Text(text = "Restaurant is CLOSED.")
                                    }
                                }

                                "Friday" -> {
                                    if (restaurantData.openHours?.fridayIsClosed != true) {
                                        Text(text = "${restaurantData.openHours?.fridayStart} - ${restaurantData.openHours?.fridayEnd}")
                                    } else {
                                        restaurantIsClosed = true
                                        Text(text = "Restaurant is CLOSED.")
                                    }
                                }

                                "Saturday" -> {
                                    if (restaurantData.openHours?.saturdayIsClosed != true) {
                                        Text(text = "${restaurantData.openHours?.saturdayStart} - ${restaurantData.openHours?.saturdayEnd}")
                                    } else {
                                        restaurantIsClosed = true
                                        Text(text = "Restaurant is CLOSED.")
                                    }
                                }

                                "Sunday" -> {
                                    if (restaurantData.openHours?.sundayIsClosed != true) {
                                        Text(text = "${restaurantData.openHours?.sundayStart} - ${restaurantData.openHours?.sundayEnd}")
                                    } else {
                                        restaurantIsClosed = true
                                        Text(text = "Restaurant is CLOSED.")
                                    }
                                }
                            }
                        }
                        Row {
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "")
                            Text(
                                text = "Delivery in ${restaurantData.deliveryTime}-${
                                    restaurantData.deliveryTime?.toInt()?.plus(10)
                                } mins."
                            )
                        }


                        Divider(Modifier.padding(20.dp))
                    }

                }
                val uniqueCategories = mealList.value.distinctBy { it.category }

                items(uniqueCategories) {
                    Text(
                        modifier = Modifier.padding(20.dp),
                        text = "${it.category}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    val mealsWithCategory =
                        mealList.value.filter { meal -> meal.category == it.category }
                    mealsWithCategory.forEach { mealData ->
                        MealItem(
                            mealData = mealData,
                            mealViewModel = mealViewModel,
                            closedRest = restaurantIsClosed
                        )
                    }
                }
            }
        }

    }

    if (restaurantIsClosed) {
        RestaurantIsClosedBottomSheet()
    }
}


@OptIn(ExperimentalSheetApi::class)
@Composable
private fun RestaurantIsClosedBottomSheet() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.close_animation))
    var bottomSheetState by remember { mutableStateOf(true) }

    ModalSheet(visible = bottomSheetState, onVisibleChange = { bottomSheetState = it }) {
        Column(
            Modifier
                .size(400.dp)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Currently the restaurant is closed. \n But you can still check the menu.",
                fontWeight = FontWeight.Bold
            )

            LottieAnimation(modifier = Modifier.size(300.dp), composition = composition)

        }
    }
}