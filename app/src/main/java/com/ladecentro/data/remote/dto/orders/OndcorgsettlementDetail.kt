package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class OndcorgsettlementDetail(
    @SerializedName("settlement_type")
    val settlementType: String,
    @SerializedName("upi_address")
    val upiAddress: String
)