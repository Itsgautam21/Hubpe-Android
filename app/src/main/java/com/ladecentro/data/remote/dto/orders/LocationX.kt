package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class LocationX(
    @SerializedName("descriptor")
    val descriptor: DescriptorXX,
    @SerializedName("gps")
    val gps: String,
    @SerializedName("primary")
    val primary: Boolean,
    @SerializedName("timings")
    val timings: List<Timing>
)