package com.ladecentro.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city")
    val city: City,
    @SerializedName("country")
    val country: Country,
    @SerializedName("descriptor")
    val descriptor: DescriptorX,
    @SerializedName("gps")
    val gps: String,
    @SerializedName("landmark")
    val landmark: String,
    @SerializedName("primary")
    val primary: Boolean,
    @SerializedName("state")
    val state: String,
    @SerializedName("station_code")
    val stationCode: String,
    @SerializedName("timings")
    val timings: List<Timing>
)