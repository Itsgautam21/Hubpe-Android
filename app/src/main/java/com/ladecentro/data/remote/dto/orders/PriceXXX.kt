package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class PriceXXX(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("value")
    val value: String
)