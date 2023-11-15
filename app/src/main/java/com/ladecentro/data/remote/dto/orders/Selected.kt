package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Selected(
    @SerializedName("count")
    val count: Int
)