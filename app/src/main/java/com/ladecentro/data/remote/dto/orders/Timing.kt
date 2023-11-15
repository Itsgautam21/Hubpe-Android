package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Timing(
    @SerializedName("days")
    val days: String,
    @SerializedName("duration")
    val duration: String
)