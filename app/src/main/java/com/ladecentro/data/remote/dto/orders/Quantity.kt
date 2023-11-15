package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Quantity(
    @SerializedName("maximum")
    val maximum: Maximum,
    @SerializedName("minimum")
    val minimum: Minimum,
    @SerializedName("selected")
    val selected: Selected
)