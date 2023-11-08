package com.ladecentro.data.remote.dto


import com.google.gson.annotations.SerializedName

data class DescriptorXXXX(
    @SerializedName("long_desc")
    val longDesc: String,
    @SerializedName("name")
    val name: String
)