package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class DescriptorXX(
    @SerializedName("long_desc")
    val longDesc: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("short_desc")
    val shortDesc: String
)