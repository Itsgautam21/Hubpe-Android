package com.ladecentro.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("code")
    val code: String
)