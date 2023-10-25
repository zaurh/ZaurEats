package com.example.zaureats.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.zaureats.R
import com.example.zaureats.common.NavParam
import com.example.zaureats.common.navigateTo
import com.example.zaureats.data.user.UserData
import com.example.zaureats.presentation.screen.components.RestaurantItem
import com.example.zaureats.presentation.viewmodel.AuthViewModel
import com.example.zaureats.presentation.viewmodel.MealViewModel
import com.example.zaureats.presentation.viewmodel.RestaurantViewModel
import com.example.zaureats.presentation.viewmodel.UserViewModel

@Composable
fun MainScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    restaurantViewModel: RestaurantViewModel,
    mealViewModel: MealViewModel,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val restaurantList = restaurantViewModel.restaurantList.value
    val userData = userViewModel.userData.value
    val currentUserId = authViewModel.currentUserId

    LaunchedEffect(true) {
        currentUserId?.let {
            userViewModel.getUserData(it)
        }
        mealViewModel.tempBasketList.value = emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = userData?.address ?: "", modifier = Modifier.clickable {
                    navigateTo(
                        navController, "settings_screen", NavParam(
                            "userData",
                            userData ?: UserData()
                        )
                    )
                })
                IconButton(onClick = {
                    navController.navigate("profile_screen")
                }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                }
            }

        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                MySearchBar(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    onSearch = {
                        restaurantViewModel.searchList(it)
                    },
                    restaurantViewModel = restaurantViewModel
                )
            }
            items(restaurantList) {
                RestaurantItem(restaurantData = it, navController = navController)
            }
        }

    }
}


@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
    restaurantViewModel: RestaurantViewModel
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf("") }


    Box(modifier = modifier) {
        BackHandler(enabled = text.isNotEmpty(),onBack = {
            text = ""
            restaurantViewModel.clearSearch()
            focusManager.clearFocus()
        })
        TextField(
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "")},
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = { Text(text = "Search...")},
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .shadow(5.dp)
                .background(colorResource(id = R.color.lightGray))
        )
    }
}