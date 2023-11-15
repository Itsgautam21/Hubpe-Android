package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("cancellable")
    val cancellable: Boolean,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("descriptor")
    val descriptor: Descriptor,
    @SerializedName("email")
    val email: String,
    @SerializedName("fssai_license_no")
    val fssaiLicenseNo: String,
    @SerializedName("fulfillments")
    val fulfillments: List<FulfillmentX>,
    @SerializedName("id")
    val id: String,
    @SerializedName("is_promoted")
    val isPromoted: Boolean,
    @SerializedName("locations")
    val locations: List<LocationX>,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("preferred_inventory")
    val preferredInventory: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("returnable")
    val returnable: Boolean,
    @SerializedName("sector")
    val sector: Sector,
    @SerializedName("type")
    val type: String
)