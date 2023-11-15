package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Sector(
    @SerializedName("name")
    val name: String
)