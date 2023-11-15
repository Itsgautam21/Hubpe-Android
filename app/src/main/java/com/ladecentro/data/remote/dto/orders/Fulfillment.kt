package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Fulfillment(
    @SerializedName("end")
    val end: End,
    @SerializedName("id")
    val id: String,
    @SerializedName("tracking")
    val tracking: Boolean,
    @SerializedName("type")
    val type: String
)