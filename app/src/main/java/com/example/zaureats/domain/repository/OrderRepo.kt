package com.example.zaureats.domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.zaureats.data.BasketData
import com.example.zaureats.data.order.OrderData
import com.example.zaureats.data.order.OrderStatus
import com.example.zaureats.data.user.UserData
import com.example.zaureats.data.restaurant.RestaurantData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import javax.inject.Inject

class OrderRepo @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private var orderList = MutableLiveData<List<OrderData>>(emptyList())

    init {
        orderList = MutableLiveData()
    }


    fun addOrder(
        restaurantData: RestaurantData,
        userData: UserData,
        basketData: BasketData,
        orderStatus: OrderStatus,
        price: String,
        onSuccess: () -> Unit
    ) {
        val data = OrderData(
            orderId = basketData.basketId,
            restaurantData = restaurantData,
            userData = userData,
            basketData = basketData,
            orderStatus = orderStatus,
            price = price
        )
        firestore.collection("restaurants").document(restaurantData.restaurantId ?: "")
            .collection("orders").document(basketData.basketId ?: "null")
            .set(data).addOnSuccessListener {
                onSuccess()
            }
        firestore.collection("users").document(userData.userId ?: "")
            .collection("orders").document(basketData.basketId?: "null")
            .set(data)
    }

    fun getOrder(restId: String, basketId: String, onSuccess: (OrderData) -> Unit) {
        firestore.collection("restaurants").document(restId)
            .collection("orders").document(basketId)
            .addSnapshotListener { value, _ ->
                if (value != null) {
                    val orderData = value.toObject<OrderData>()
                    if (orderData != null) {
                        onSuccess(orderData)
                    }
                }
            }
    }

    fun getOrderHistory(userId: String): MutableLiveData<List<OrderData>>{
        firestore.collection("users").document(userId)
            .collection("orders").addSnapshotListener { value, _ ->
                if (value != null){
                    orderList.value = value.toObjects()
                }
            }
        return orderList
    }
}