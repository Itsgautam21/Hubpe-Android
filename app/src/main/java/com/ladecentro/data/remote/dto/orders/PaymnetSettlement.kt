package com.ladecentro.data.remote.dto.orders


import com.google.gson.annotations.SerializedName

data class PaymnetSettlement(
    @SerializedName("@ondc/org/buyer_app_finder_fee_amount")
    val ondcorgbuyerAppFinderFeeAmount: String,
    @SerializedName("@ondc/org/buyer_app_finder_fee_type")
    val ondcorgbuyerAppFinderFeeType: String,
    @SerializedName("@ondc/org/settlement_details")
    val ondcorgsettlementDetails: List<OndcorgsettlementDetail>
)