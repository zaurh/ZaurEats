package com.example.zaureats.data.order

data class OrderStatus(
    val accepted: Boolean? = false,
    val ready: Boolean? = false,
    val rejected: Boolean? = false
)
