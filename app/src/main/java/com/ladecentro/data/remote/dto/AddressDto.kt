package com.ladecentro.data.remote.dto

import com.google.gson.annotations.SerializedName

class CreateAddress(

    @SerializedName("address") val address: Address?,
    @SerializedName("city") val city: City?,
    @SerializedName("country") val country: CountryXX?,
    @SerializedName("descriptor") val descriptor: DescriptorXXXX?,
    @SerializedName("gps") val gps: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("mobile_number") val mobileNumber: String?,
    @SerializedName("primary") val primary: Boolean?
)