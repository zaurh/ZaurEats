package com.example.zaureats

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.zaureats.data.order.OrderData
import com.example.zaureats.data.user.UserData
import com.example.zaureats.data.restaurant.RestaurantData
import com.example.zaureats.presentation.screen.BalanceScreen
import com.example.zaureats.presentation.screen.BasketScreen
import com.example.zaureats.presentation.screen.CheckoutScreen
import com.example.zaureats.presentation.screen.DeliveryScreen
import com.example.zaureats.presentation.screen.MainScreen
import com.example.zaureats.presentation.screen.OrderHistoryScreen
import com.example.zaureats.presentation.screen.ProfileScreen
import com.example.zaureats.presentation.screen.RestaurantInfoScreen
import com.example.zaureats.presentation.screen.RestaurantScreen
import com.example.zaureats.presentation.screen.SettingsScreen
import com.example.zaureats.presentation.screen.SplashScreen
import com.example.zaureats.presentation.screen.auth.ForgotPasswordScreen
import com.example.zaureats.presentation.screen.auth.SignInScreen
import com.example.zaureats.presentation.screen.auth.SignUpScreen
import com.example.zaureats.presentation.viewmodel.AuthViewModel
import com.example.zaureats.presentation.viewmodel.BasketViewModel
import com.example.zaureats.presentation.viewmodel.MealViewModel
import com.example.zaureats.presentation.viewmodel.OrderViewModel
import com.example.zaureats.presentation.viewmodel.RestaurantViewModel
import com.example.zaureats.presentation.viewmodel.UserViewModel
import com.example.zaureats.ui.theme.ZaurEatsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZaurEatsTheme {
                Navig()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navig() {
    val authViewModel = hiltViewModel<AuthViewModel>()
    val orderViewModel = hiltViewModel<OrderViewModel>()
    val basketViewModel = hiltViewModel<BasketViewModel>()
    val mealViewModel = hiltViewModel<MealViewModel>()
    val restaurantViewModel = hiltViewModel<RestaurantViewModel>()
    val userViewModel = hiltViewModel<UserViewModel>()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("main") {
            MainScreen(
                navController = navController,
                restaurantViewModel = restaurantViewModel,
                userViewModel = userViewModel,
                mealViewModel = mealViewModel
            )
        }
        composable("splash_screen") {
            SplashScreen(
                navController = navController
            )
        }
        composable("sign_in_screen") {
            SignInScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable("sign_up_screen") {
            SignUpScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable("forgot_password_screen") {
            ForgotPasswordScreen(
                authViewModel = authViewModel
            )
        }
        composable("restaurant_screen") {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<RestaurantData>("restData")
            result?.let {
                RestaurantScreen(
                    navController = navController,
                    restaurantData = it,
                    mealViewModel = mealViewModel,
                    restaurantViewModel = restaurantViewModel
                )
            }
        }
        composable("restaurant_info_screen") {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<RestaurantData>("restData")
            result?.let {
                RestaurantInfoScreen(navController = navController, restaurantData = it)
            }
        }
        composable("profile_screen") {
            ProfileScreen(
                navController = navController,
                authViewModel = authViewModel,
                userViewModel = userViewModel
            )
        }
        composable("order_history_screen") {
            OrderHistoryScreen(
                navController = navController
            )
        }

        composable("settings_screen") {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<UserData>("userData")
            result?.let {
                SettingsScreen(
                    navController = navController,
                    userData = it,
                    userViewModel = userViewModel
                )
            }
        }

        composable("balance_screen") {
            BalanceScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composable("basket_screen") {
            BasketScreen(
                navController = navController,
                basketViewModel = basketViewModel,
                mealViewModel = mealViewModel,
                authViewModel = authViewModel
            )
        }
        composable("checkout_screen/{totalPrice}", arguments = listOf(
            navArgument("totalPrice") { type = NavType.StringType }
        )) {
            val totalPrice = it.arguments?.getString("totalPrice")

            CheckoutScreen(
                navController = navController,
                orderViewModel = orderViewModel,
                basketViewModel = basketViewModel,
                restaurantViewModel = restaurantViewModel,
                userViewModel = userViewModel,
                totalPrice = totalPrice ?: "0.0"
            )

        }
        composable("delivery_screen") {
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<OrderData>("orderData")
            result?.let {
            DeliveryScreen(
                navController = navController,
                orderData = it)}
        }
    }
}

