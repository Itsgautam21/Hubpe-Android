package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Attribute(
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
)