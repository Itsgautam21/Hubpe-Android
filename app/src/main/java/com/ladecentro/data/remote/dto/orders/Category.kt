package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("code")
    val code: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("name")
    val name: String
)