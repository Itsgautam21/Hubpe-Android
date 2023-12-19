package com.ladecentro.data.remote.dto.orders

import com.google.gson.annotations.SerializedName

data class OrderStatus(
    @SerializedName("title") val title: String,
    @SerializedName("activities") val activities: List<OrderActivity>?
)

data class OrderActivity(
    @SerializedName("order_status") val orderStatus: String,
    @SerializedName("status_type") val statusType: String,
    @SerializedName("status_value") val statusValue: String,
    @SerializedName("message") val message: String,
    @SerializedName("order_time") val orderTime: String
)