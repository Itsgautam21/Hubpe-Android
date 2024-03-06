package com.ladecentro.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.ladecentro.data.remote.dto.orders.Payment
import com.ladecentro.data.remote.dto.orders.Quote
import com.ladecentro.domain.model.Address
import com.ladecentro.domain.model.LocationRequest
import java.util.Objects

data class CartDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("user_id") val userId: String? = null,
    @SerializedName("transaction_id") val transactionId: String? = null,
    @SerializedName("operation") val operation: String? = null,
    @SerializedName("ondc_response") val ondcResponse: Boolean? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("store") val store: Store,
    @SerializedName("items") val items: MutableList<Product>,
    @SerializedName("quote") val quote: Quote? = null,
    @SerializedName("location") val location: LocationRequest? = null,
    @SerializedName("billing") val billing: BillingRequest? = null,
    @SerializedName("fulfillments") val fulfillment: List<FulfillmentRequest>? = null,
    @SerializedName("payments") val payments: Payment? = null,
    @SerializedName("error") val error: CartError? = null
)

data class FulfillmentRequest(
    @SerializedName("id") val id: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("tracking") val tracking: Boolean? = null,
    @SerializedName("@ondc/org/category") val category: String? = null,
    @SerializedName("@ondc/org/TAT") val tat: String? = null,
    @SerializedName("@ondc/org/provider_name") val providerName: String? = null,
    @SerializedName("state") val state: Objects? = null,
    @SerializedName("end") val end: FulfillmentEndRequest? = null,
)

data class BillingRequest(
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: Address,
    @SerializedName("phone") val phone: String,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null
)

data class FulfillmentEndRequest(
    @SerializedName("location") val location: LocationRequest? = null,
    @SerializedName("contact") val contact: Contact? = null,
    @SerializedName("person") val person: Person? = null,
)

data class CartResponse(
    @SerializedName("status") val status: String,
    @SerializedName("cartId") val cartId: String
)

data class Contact(
    @SerializedName("phone") val phone: String
)

data class Person(
    @SerializedName("name") val name: String
)

data class CartError(
    @SerializedName("type") val type: String,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
)