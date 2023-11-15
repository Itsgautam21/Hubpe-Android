package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Breakup(
    @SerializedName("item")
    val item: ItemX,
    @SerializedName("@ondc/org/item_id")
    val ondcorgitemId: String,
    @SerializedName("@ondc/org/item_quantity")
    val ondcorgitemQuantity: OndcorgitemQuantity,
    @SerializedName("@ondc/org/title_type")
    val ondcorgtitleType: String,
    @SerializedName("price")
    val price: PriceXXX,
    @SerializedName("title")
    val title: String
)