package com.ladecentro.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Timing(
    @SerializedName("days")
    val days: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("is-open")
    val isOpen: Boolean
)