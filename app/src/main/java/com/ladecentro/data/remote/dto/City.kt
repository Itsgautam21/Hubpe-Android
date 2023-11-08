package com.ladecentro.data.remote.dto


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("name")
    val name: String
)