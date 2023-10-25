package com.example.zaureats.presentation.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zaureats.R
import com.example.zaureats.data.user.AddressDetails
import com.example.zaureats.data.user.UserData
import com.example.zaureats.presentation.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    userData: UserData,
    navController: NavController,
    userViewModel: UserViewModel
) {
    var nameTf by remember { mutableStateOf(userData.name ?: "") }
    var surnameTf by remember { mutableStateOf(userData.surname ?: "") }
    var addressTf by remember { mutableStateOf(userData.address ?: "") }
    var entranceTf by remember { mutableStateOf(userData.addressDetails?.entrance ?: "") }
    var floorTf by remember { mutableStateOf(userData.addressDetails?.floor ?: "") }
    var apartmentTf by remember { mutableStateOf(userData.addressDetails?.apartment ?: "") }
    var phoneNumberTf by remember { mutableStateOf(userData.phoneNumber ?: "") }


    BackHandler(onBack = {
        userViewModel.updateUserData(
            name = nameTf,
            surname = surnameTf,
            address = addressTf,
            addressDetails = AddressDetails(entranceTf, floorTf, apartmentTf),
            phoneNumber = phoneNumberTf,
            onSuccess = {

            }
        )
        navController.popBackStack()
    })
    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = {
                    userViewModel.updateUserData(
                        name = nameTf,
                        surname = surnameTf,
                        address = addressTf,
                        addressDetails = AddressDetails(entranceTf, floorTf, apartmentTf),
                        phoneNumber = phoneNumberTf,
                        onSuccess = {}
                    )
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            }, title = { Text(text = "Settings", fontWeight = FontWeight.Bold) })
        },
        content = { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                SettingsTextField(
                    text = "First Name",
                    value = nameTf,
                    onValueChange = { nameTf = it },
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                )
                SettingsTextField(
                    text = "Last name",
                    value = surnameTf,
                    onValueChange = { surnameTf = it },
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                )
                SettingsTextField(
                    text = "Address",
                    value = addressTf,
                    onValueChange = { addressTf = it },
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                )
                SettingsTextField(
                    text = "Entrance",
                    value = entranceTf,
                    onValueChange = { entranceTf = it },
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                )
                Row {
                    SettingsTextField(
                        text = "Floor",
                        value = floorTf,
                        onValueChange = { floorTf = it },
                        modifier = Modifier
                            .padding(start = 20.dp, end = 10.dp)
                            .weight(5f)
                    )
                    SettingsTextField(
                        text = "Apartment",
                        value = apartmentTf,
                        onValueChange = { apartmentTf = it },
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp)
                            .weight(5f)
                    )
                }
                SettingsTextField(
                    text = "Phone number",
                    value = phoneNumberTf,
                    onValueChange = { phoneNumberTf = it },
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                )
            }
        },
        bottomBar = {

        }
    )
}

@Composable
private fun SettingsTextField(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
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