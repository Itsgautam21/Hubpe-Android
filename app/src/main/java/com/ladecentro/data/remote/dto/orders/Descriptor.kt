package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Descriptor(
    @SerializedName("code")
    val code: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("long_desc")
    val longDesc: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("short_desc")
    val shortDesc: String,
    @SerializedName("symbol")
    val symbol: String
)