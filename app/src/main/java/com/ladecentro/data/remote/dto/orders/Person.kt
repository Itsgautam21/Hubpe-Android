package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("dob")
    val dob: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String
)