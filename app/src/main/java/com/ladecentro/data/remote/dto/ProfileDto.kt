package com.ladecentro.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("id") val id: String,
    @SerializedName("mobile_number") val mobileNumber: String,
    @SerializedName("descriptor") val descriptor: Descriptor,
    @SerializedName("primary") val primary: Boolean,
    @SerializedName("gps") val gps: String,
    @SerializedName("address") val address: Address,
    @SerializedName("city") val city: City,
    @SerializedName("country") val country: Country
)

data class Descriptor(
    @SerializedName("name") val name: String,
    @SerializedName("long_desc") val longDesc: String
)

data class Address(
    @SerializedName("name") val name: String,
    @SerializedName("building") val building: String,
    @SerializedName("locality") val locality: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("country") val country: String,
    @SerializedName("area_code") val areaCode: String
)

data class City(
    @SerializedName("name") val name: String
)

data class Country(
    @SerializedName("name") val name: String,
    @SerializedName("code") val code: String
)

data class Favourite(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("returnable") val returnable: Boolean,
    @SerializedName("cancellable") val cancellable: Boolean,
    @SerializedName("bpp_details") val bppDetails: BppDetails,
    @SerializedName("preferred_inventory") val preferredInventory: String,
    @SerializedName("descriptor") val descriptor: Descriptor,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("sector") val sector: Sector,
    @SerializedName("fssai_license_no") val fssaiLicenseNo: String,
    @SerializedName("fulfillments") val fulfillments: List<Fulfillment>,
    @SerializedName("locations") val locations: List<Location>,
    @SerializedName("is_promoted") val isPromoted: Boolean,
    @SerializedName("home_images") val homeImages: List<String>,
    @SerializedName("rating") val rating: Double,
    @SerializedName("review_count") val reviewCount: Int
)

data class BppDetails(
    @SerializedName("name") val name: String,
    @SerializedName("domain") val domain: String,
    @SerializedName("bpp_id") val bppId: String,
    @SerializedName("bpp_uri") val bppUri: String,
    @SerializedName("city_code") val cityCode: String
)

data class Sector(
    @SerializedName("name") val name: String
)

data class Fulfillment(
    @SerializedName("type") val type: String,
    @SerializedName("turn_around_time") val turnAroundTime: String,
    @SerializedName("delivery-time") val deliveryTime: String
)

data class History(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("returnable") val returnable: Boolean,
    @SerializedName("cancellable") val cancellable: Boolean,
    @SerializedName("bpp_details") val bppDetails: BppDetails,
    @SerializedName("preferred_inventory") val preferredInventory: String,
    @SerializedName("descriptor") val descriptor: Descriptor,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("sector") val sector: Sector,
    @SerializedName("fulfillments") val fulfillments: List<Fulfillment>,
    @SerializedName("locations") val locations: List<Location>,
    @SerializedName("is_promoted") val isPromoted: Boolean,
    @SerializedName("promoted_value") val promotedValue: Int,
    @SerializedName("home_images") val homeImages: List<String>,
    @SerializedName("promo_products") val promoProducts: List<String>,
    @SerializedName("rating") val rating: Double,
    @SerializedName("review_count") val reviewCount: Int
)

data class ProfileDto(
    @SerializedName("id") val id: String,
    @SerializedName("token") val token: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("locations") val locations: List<Location>,
    @SerializedName("favourites") val favourites: List<Favourite>,
    @SerializedName("history") val history: List<History>
)
