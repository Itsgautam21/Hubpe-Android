package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("area_code")
    val areaCode: String,
    @SerializedName("building")
    val building: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("door")
    val door: String,
    @SerializedName("locality")
    val locality: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("ward")
    val ward: String
)