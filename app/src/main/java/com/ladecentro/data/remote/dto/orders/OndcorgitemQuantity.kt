package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class OndcorgitemQuantity(
    @SerializedName("count")
    val count: Int
)