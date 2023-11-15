package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String
)