package com.ladecentro.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchDto(
    @SerializedName("stores") val stores: List<Store>,
    @SerializedName("products") val products: List<Product>,
    @SerializedName("productsPage") val productsPage: PageInfo,
    @SerializedName("storesPage") val storesPage: PageInfo
)

data class Store(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("email") val email: String?,
    @SerializedName("returnable") val returnable: Boolean,
    @SerializedName("cancellable") val cancellable: Boolean,
    @SerializedName("bpp_details") val bppDetails: BppDetails?,
    @SerializedName("preferred_inventory") val preferredInventory: String,
    @SerializedName("distance") val distance: Distance,
    @SerializedName("descriptor") val descriptor: StoreDescriptor,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("sector") val sector: Sector,
    @SerializedName("fulfillments") val fulfillments: List<Fulfillment>,
    @SerializedName("locations") val locations: List<StoreLocation>,
    @SerializedName("is_promoted") val isPromoted: Boolean,
    @SerializedName("promoted_value") val promotedValue: Int?,
    @SerializedName("rating") val rating: Double,
    @SerializedName("review_count") val reviewCount: Int
)

data class Product(
    @SerializedName("brand") val brand: String,
    @SerializedName("id") val id: String,
    @SerializedName("product_id") val productId: String,
    @SerializedName("store_id") val storeId: String,
    @SerializedName("addons_count") val addonsCount: Int,
    @SerializedName("variant_count") val variantCount: Int,
    @SerializedName("descriptor") val descriptor: ProductDescriptor,
    @SerializedName("store_descriptor") val storeDescriptor: StoreDescriptor,
    @SerializedName("price") val price: Price,
    @SerializedName("quantity") val quantity: Quantity,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("returnable") val returnable: Boolean,
    @SerializedName("cancellable") val cancellable: Boolean,
    @SerializedName("availability") val availability: String,
    @SerializedName("rating") val rating: Double
)

data class PageInfo(
    @SerializedName("size") val size: Int,
    @SerializedName("current") val current: Int,
    @SerializedName("total") val total: Int
)


data class Distance(
    @SerializedName("distance") val distance: Double,
    @SerializedName("unit") val unit: String
)

data class StoreDescriptor(
    @SerializedName("name") val name: String,
    @SerializedName("long_desc") val longDesc: String,
    @SerializedName("images") val images: List<String>
)


data class StoreLocation(
    @SerializedName("descriptor") val descriptor: LocationDescriptor,
    @SerializedName("primary") val primary: Boolean,
    @SerializedName("gps") val gps: String,
    @SerializedName("station_code") val stationCode: String,
    @SerializedName("city") val city: City,
    @SerializedName("country") val country: Country,
    @SerializedName("state") val state: String,
    @SerializedName("landmark") val landmark: String,
    @SerializedName("timings") val timings: List<Timing>
)

data class LocationDescriptor(
    @SerializedName("name") val name: String,
    @SerializedName("short_desc") val shortDesc: String,
    @SerializedName("long_desc") val longDesc: String
)

data class Timing(
    @SerializedName("duration") val duration: String,
    @SerializedName("is-open") val isOpen: Boolean,
    @SerializedName("days") val days: String
)

data class ProductDescriptor(
    @SerializedName("name") val name: String,
    @SerializedName("short_desc") val shortDesc: String,
    @SerializedName("images") val images: List<String>
)

data class Price(
    @SerializedName("currency") val currency: String,
    @SerializedName("value") val value: String,
    @SerializedName("listed_value") val listedValue: String,
    @SerializedName("offered_value") val offeredValue: String,
    @SerializedName("maximum_value") val maximumValue: String
)

data class Quantity(
    @SerializedName("maximum") val maximum: Count,
    @SerializedName("minimum") val minimum: Count
)

data class Count(
    @SerializedName("count") val count: Int
)

data class SearchRequest(
    val term: String? = null,
    val location: String? = null,
    val page: Int? = null,
    val size: Int? = null
)
