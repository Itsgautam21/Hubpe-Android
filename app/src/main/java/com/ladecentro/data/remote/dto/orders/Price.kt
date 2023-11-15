package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("computed_value")
    val computedValue: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("estimated_value")
    val estimatedValue: String,
    @SerializedName("listed_value")
    val listedValue: String,
    @SerializedName("maximum_value")
    val maximumValue: String,
    @SerializedName("offered_value")
    val offeredValue: String,
    @SerializedName("value")
    val value: String
)