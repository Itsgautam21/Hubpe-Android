package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("billing")
    val billing: Billing,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("display_order_id")
    val displayOrderId: String,
    @SerializedName("fulfillments")
    val fulfillments: List<Fulfillment>,
    @SerializedName("id")
    val id: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("ondc_response")
    val ondcResponse: Boolean,
    @SerializedName("payment")
    val payment: List<Payment>,
    @SerializedName("paymnet_settlement")
    val paymnetSettlement: PaymnetSettlement,
    @SerializedName("quote")
    val quote: Quote,
    @SerializedName("state")
    val state: String,
    @SerializedName("store")
    val store: Store,
    @SerializedName("type")
    val type: String,
    val rating: String,
    @SerializedName("updated_at")
    val updatedAt: String
)