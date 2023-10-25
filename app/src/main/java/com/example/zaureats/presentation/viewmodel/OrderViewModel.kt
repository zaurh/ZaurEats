package com.example.zaureats.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zaureats.data.BasketData
import com.example.zaureats.data.order.OrderData
import com.example.zaureats.data.order.OrderStatus
import com.example.zaureats.data.user.UserData
import com.example.zaureats.data.restaurant.RestaurantData
import com.example.zaureats.domain.repository.AuthRepo
import com.example.zaureats.domain.repository.OrderRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    authRepo: AuthRepo
): ViewModel() {

    var orderList = MutableLiveData<List<OrderData>>()


    init {
        authRepo.currentUserId?.let {
            orderList = orderRepo.getOrderHistory(it)
        }
    }

    fun addOrder(
        restaurantData: RestaurantData,
        userData: UserData,
        basketData: BasketData,
        orderStatus: OrderStatus,
        price: String,
        onSuccess: () -> Unit
    ) {
        orderRepo.addOrder(
            restaurantData, userData, basketData, orderStatus,price, onSuccess
        )
    }

    fun getOrder(restId: String, basketId: String, onSuccess: (OrderData) -> Unit) {
        orderRepo.getOrder(restId, basketId, onSuccess)
    }

}