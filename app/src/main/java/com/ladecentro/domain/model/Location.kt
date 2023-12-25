package com.ladecentro.domain.model

import com.google.gson.annotations.SerializedName

data class ProfileRequest(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("type") val type: List<String>? = null,
    @SerializedName("operation") val operation: String? = null,
    @SerializedName("locations") val locations: List<LocationRequest>? = null
)

data class LocationRequest(
    @SerializedName("id") val id: String? = null,
    @SerializedName("mobile_number") val mobileNumber: String? = null,
    @SerializedName("descriptor") val descriptor: Descriptor? = null,
    @SerializedName("primary") val primary: Boolean? = null,
    @SerializedName("gps") val gps: String? = null,
    @SerializedName("address") val address: Address? = null,
    @SerializedName("city") val city: City? = null,
    @SerializedName("country") val country: Country? = null
)

data class Descriptor(
    @SerializedName("name") val name: String? = null,
    @SerializedName("long_desc") val longDesc: String? = null
)

data class Address(
    @SerializedName("name") val name: String? = null,
    @SerializedName("building") val building: String? = null,
    @SerializedName("locality") val locality: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("state") val state: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("area_code") val areaCode: String? = null
)

data class City(
    @SerializedName("name") val name: String? = null
)

data class Country(
    @SerializedName("name") val name: String? = null,
    @SerializedName("code") val code: String? = null
)