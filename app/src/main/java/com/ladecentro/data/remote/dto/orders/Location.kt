package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("address")
    val address: AddressX,
    @SerializedName("gps")
    val gps: String
)