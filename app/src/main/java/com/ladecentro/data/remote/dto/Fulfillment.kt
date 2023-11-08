package com.ladecentro.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Fulfillment(
    @SerializedName("delivery-time")
    val deliveryTime: String,
    @SerializedName("turn_around_time")
    val turnAroundTime: String,
    @SerializedName("type")
    val type: String
)