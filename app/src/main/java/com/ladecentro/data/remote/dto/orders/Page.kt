package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("current")
    val current: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("total")
    val total: Int
)