package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class End(
    @SerializedName("contact")
    val contact: Contact,
    @SerializedName("location")
    val location: Location,
    @SerializedName("person")
    val person: Person
)