package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class FulfillmentX(
    @SerializedName("id")
    val id: String,
    @SerializedName("tracking")
    val tracking: Boolean,
    @SerializedName("turn_around_time")
    val turnAroundTime: String,
    @SerializedName("type")
    val type: String
)