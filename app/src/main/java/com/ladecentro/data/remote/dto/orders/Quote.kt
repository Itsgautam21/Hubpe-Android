package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("breakup")
    val breakup: List<Breakup>,
    @SerializedName("price")
    val price: PriceXXX,
    @SerializedName("ttl")
    val ttl: String
)