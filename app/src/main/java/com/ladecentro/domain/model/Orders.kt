package com.ladecentro.domain.model

data class Orders(
    val id: String,
    val displayOrderId: String,
    val store: Store,
    val items: List<Item>,
    val paymentAndStatus: PaymentAndStatus,
    val rating: String?
)

data class Store(
    val image: String,
    val name: String,
    val shortAddress: String
)

data class Item(val image: String, val quantity: Int, val name: String)

data class PaymentAndStatus(
    val createdDate: String,
    val status: String,
    val statusIcon: Int,
    val paymentType: String,
    val totalPrice: String
)