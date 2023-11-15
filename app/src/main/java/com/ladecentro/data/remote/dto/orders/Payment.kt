package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("customer_email")
    val customerEmail: String,
    @SerializedName("customer_id")
    val customerId: String,
    @SerializedName("customer_phone")
    val customerPhone: String,
    @SerializedName("pg")
    val pg: String,
    @SerializedName("pg_order_id")
    val pgOrderId: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("transaction_id")
    val transactionId: String
)