package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("addons_count")
    val addonsCount: Int,
    @SerializedName("attributes")
    val attributes: List<Attribute>,
    @SerializedName("availability")
    val availability: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("cancellable")
    val cancellable: Boolean,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("descriptor")
    val descriptor: Descriptor,
    @SerializedName("id")
    val id: String,
    @SerializedName("price")
    val price: Price,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("quantity")
    val quantity: Quantity,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("returnable")
    val returnable: Boolean,
    @SerializedName("store_descriptor")
    val storeDescriptor: StoreDescriptor,
    @SerializedName("store_id")
    val storeId: String,
    @SerializedName("variant_count")
    val variantCount: Int
)