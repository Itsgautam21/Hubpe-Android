package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Minimum(
    @SerializedName("count")
    val count: Int
)