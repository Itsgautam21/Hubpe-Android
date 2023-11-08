package com.ladecentro.data.remote.dto


import com.google.gson.annotations.SerializedName

data class BppDetails(
    @SerializedName("bpp_id")
    val bppId: String,
    @SerializedName("bpp_uri")
    val bppUri: String,
    @SerializedName("city_code")
    val cityCode: String,
    @SerializedName("name")
    val name: String
)