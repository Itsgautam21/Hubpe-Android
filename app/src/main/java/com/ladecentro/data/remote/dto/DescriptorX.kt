package com.ladecentro.data.remote.dto


import com.google.gson.annotations.SerializedName

data class DescriptorX(
    @SerializedName("long_desc")
    val longDesc: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("short_desc")
    val shortDesc: String
)