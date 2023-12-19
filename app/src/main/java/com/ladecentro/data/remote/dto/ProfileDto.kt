package com.ladecentro.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProfileDto(

    @SerializedName("favourites")
    val favourites: List<Favourite>,

    @SerializedName("history")
    val history: List<History>,

    @SerializedName("id")
    val id: String,

    @SerializedName("locations")
    val locations: List<LocationXX>,

    @SerializedName("name")
    val name: String,

    @SerializedName("phone")
    val phone: String,

    val type: String,

    val operation: String
)