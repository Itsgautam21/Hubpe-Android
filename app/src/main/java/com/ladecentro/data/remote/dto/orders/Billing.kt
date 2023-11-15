package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class Billing(
    @SerializedName("address")
    val address: Address,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("tax_number")
    val taxNumber: String,
    @SerializedName("updated_at")
    val updatedAt: String
)