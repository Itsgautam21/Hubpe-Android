package com.ladecentro.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LocationX(
    @SerializedName("city")
    val city: City,
    @SerializedName("country")
    val country: CountryXX,
    @SerializedName("descriptor")
    val descriptor: DescriptorXXXX,
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