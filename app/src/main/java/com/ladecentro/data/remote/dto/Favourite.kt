package com.ladecentro.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Favourite(
    @SerializedName("bpp_details")
    val bppDetails: BppDetails,
    @SerializedName("cancellable")
    val cancellable: Boolean,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("descriptor")
    val descriptor: Descriptor,
    @SerializedName("email")
    val email: String,
    @SerializedName("fssai_license_no")
    val fssaiLicenseNo: String,
    @SerializedName("fulfillments")
    val fulfillments: List<Fulfillment>,
    @SerializedName("home_images")
    val homeImages: List<Any>,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_promoted")
    val isPromoted: Boolean,
    @SerializedName("locations")
    val locations: List<Location>,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("preferred_inventory")
    val preferredInventory: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("returnable")
    val returnable: Boolean,
    @SerializedName("review_count")
    val reviewCount: Int,
    @SerializedName("sector")
    val sector: Sector,
    @SerializedName("type")
    val type: String
)