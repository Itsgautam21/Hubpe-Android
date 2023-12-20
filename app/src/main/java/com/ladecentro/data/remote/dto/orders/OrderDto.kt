package com.ladecentro.data.remote.dto.orders

import com.google.gson.annotations.SerializedName
import com.ladecentro.common.getFormattedDateTime
import com.ladecentro.domain.model.DeliveryDetails
import com.ladecentro.domain.model.ItemDetails
import com.ladecentro.domain.model.OrderDetail
import com.ladecentro.domain.model.OrderDetails
import com.ladecentro.domain.model.PaymentDetails
import com.ladecentro.domain.model.PriceBreakUp

data class Order(
    @SerializedName("id") val id: String,
    @SerializedName("display_order_id") val displayOrderId: String,
    @SerializedName("ondc_response") val ondcResponse: Boolean,
    @SerializedName("type") val type: String,
    @SerializedName("state") val state: String,
    @SerializedName("store") val store: Store,
    @SerializedName("items") val items: List<Item>,
    @SerializedName("billing") val billing: Billing,
    @SerializedName("fulfillments") val fulfillments: List<Fulfillment>,
    @SerializedName("quote") val quote: Quote,
    @SerializedName("payment") val payment: List<Payment>,
    @SerializedName("paymnet_settlement") val paymentSettlement: PaymentSettlement,
    @SerializedName("order_track") val orderTrack: OrderTrack,
    @SerializedName("returns") val returns: List<Return>,
    @SerializedName("cancels") val cancels: List<Cancel>,
    @SerializedName("rating") val rating: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class Store(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("returnable") val returnable: Boolean,
    @SerializedName("cancellable") val cancellable: Boolean,
    @SerializedName("bppDetails") val bppDetails: BppDetails,
    @SerializedName("preferred_inventory") val preferredInventory: String,
    @SerializedName("descriptor") val descriptor: Descriptor,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("sector") val sector: Sector,
    @SerializedName("fssai_license_no") val fssaiLicenseNo: String,
    @SerializedName("fulfillments") val fulfillments: List<Fulfillment>,
    @SerializedName("locations") val locations: List<Location>,
    @SerializedName("isPromoted") val isPromoted: Boolean,
    @SerializedName("home_images") val homeImages: List<String>,
    @SerializedName("categories") val categories: List<Category>,
    @SerializedName("rating") val rating: Int,
    @SerializedName("review_count") val reviewCount: Int
)

data class BppDetails(
    @SerializedName("name") val name: String,
    @SerializedName("bpp_id") val bppId: String,
    @SerializedName("bpp_uri") val bppUri: String,
    @SerializedName("city_code") val cityCode: String
)

data class Descriptor(
    @SerializedName("name") val name: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("short_desc") val shortDesc: String,
    @SerializedName("long_desc") val longDesc: String,
)

data class Sector(
    @SerializedName("name") val name: String
)

data class Fulfillment(
    @SerializedName("type") val type: String,
    @SerializedName("turn_around_time") val turnAroundTime: String,
    @SerializedName("tracking") val tracking: Boolean
)

data class Location(
    @SerializedName("descriptor") val descriptor: Descriptor,
    @SerializedName("primary") val primary: Boolean,
    @SerializedName("gps") val gps: String,
    @SerializedName("station_code") val stationCode: String,
    @SerializedName("city") val city: City,
    @SerializedName("country") val country: Country,
    @SerializedName("state") val state: String,
    @SerializedName("landmark") val landmark: String,
    @SerializedName("timings") val timings: List<Timing>
)

data class City(
    @SerializedName("name") val name: String
)

data class Country(
    @SerializedName("code") val code: String
)

data class Timing(
    @SerializedName("duration") val duration: String,
    @SerializedName("is_open") val isOpen: Boolean,
    @SerializedName("days") val days: String
)

data class Category(
    @SerializedName("name") val name: String,
    @SerializedName("code") val code: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("count") val count: Int
)

data class Item(
    @SerializedName("brand") val brand: String,
    @SerializedName("id") val id: String,
    @SerializedName("product_id") val productId: String,
    @SerializedName("store_id") val storeId: String,
    @SerializedName("addons_count") val addonsCount: Int,
    @SerializedName("variant_count") val variantCount: Int,
    @SerializedName("descriptor") val descriptor: Descriptor,
    @SerializedName("store_descriptor") val storeDescriptor: StoreDescriptor,
    @SerializedName("price") val price: Price,
    @SerializedName("quantity") val quantity: Quantity,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("returnable") val returnable: Boolean,
    @SerializedName("cancellable") val cancellable: Boolean,
    @SerializedName("availability") val availability: String,
    @SerializedName("rating") val rating: Int
)

data class StoreDescriptor(
    @SerializedName("name") val name: String,
    @SerializedName("code") val code: String
)

data class Price(
    @SerializedName("currency") val currency: String,
    @SerializedName("value") val value: String,
    @SerializedName("listed_value") val listedValue: String,
    @SerializedName("offered_value") val offeredValue: String,
    @SerializedName("maximum_value") val maximumValue: String
)

data class Quantity(
    @SerializedName("maximum") val maximum: Maximum,
    @SerializedName("minimum") val minimum: Minimum,
    @SerializedName("selected") val selected: Selected
)

data class Maximum(
    @SerializedName("count") val count: Int
)

data class Minimum(
    @SerializedName("count") val count: Int
)

data class Selected(
    @SerializedName("count") val count: Int
)

data class Billing(
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: Address,
    @SerializedName("phone") val phone: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class Address(
    @SerializedName("name") val name: String,
    @SerializedName("building") val building: String,
    @SerializedName("locality") val locality: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("country") val country: String,
    @SerializedName("area_code") val areaCode: String
)

data class Quote(
    @SerializedName("price") val price: Price,
    @SerializedName("breakup") val breakup: List<Breakup>,
    @SerializedName("ttl") val ttl: String
)

data class Breakup(
    @SerializedName("@ondc/org/item_id") val itemId: String,
    @SerializedName("@ondc/org/item_quantity") val itemQuantity: ItemQuantity,
    @SerializedName("@ondc/org/title_type") val titleType: String,
    @SerializedName("item") val item: Item,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Price
)

data class ItemQuantity(
    @SerializedName("count") val count: Int
)

data class Payment(
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("state") val state: String,
    @SerializedName("transaction_id") val transactionId: String,
    @SerializedName("payment_ode") val paymentMode: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("pg") val pg: String,
    @SerializedName("pg_order_id") val pgOrderId: String,
    @SerializedName("customer_id") val customerId: String,
    @SerializedName("customer_phone") val customerPhone: String,
    @SerializedName("payment_transaction") val paymentTransaction: List<PaymentTransaction>
)

data class PaymentTransaction(
    @SerializedName("id") val id: String,
    @SerializedName("payment_method") val paymentMethod: String,
    @SerializedName("payment_methodType") val paymentMethodType: String,
    @SerializedName("ref_transaction_id") val refTransactionId: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("status") val status: String,
    @SerializedName("bank_rrn") val bankRrn: String,
    @SerializedName("bank_txn_id") val bankTxnId: String,
    @SerializedName("bank_created_at") val bankCreatedAt: String,
    @SerializedName("bank_response_code") val bankResponseCode: String,
    @SerializedName("bank_response") val bankResponse: String,
    @SerializedName("bank_error_code") val bankErrorCode: String,
    @SerializedName("bank_error_message") val bankErrorMessage: String,
    @SerializedName("payer_vpa") val payerVpa: String,
    @SerializedName("txnRequest_object") val txnRequestObject: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class PaymentSettlement(
    @SerializedName("@ondc/org/buyer_app_finder_fee_type") val buyerAppFinderFeeType: String,
    @SerializedName("@ondc/org/buyer_app_finder_fee_amount") val buyerAppFinderFeeAmount: String,
    @SerializedName("@ondc/org/settlement_details") val settlementDetails: List<SettlementDetail>
)

data class SettlementDetail(
    @SerializedName("settlement_counterparty") val settlementCounterparty: String,
    @SerializedName("settlement_phase") val settlementPhase: String,
    @SerializedName("settlement_type") val settlementType: String,
    @SerializedName("settlement_bank_account_no") val settlementBankAccountNo: String,
    @SerializedName("settlement_ifsc_code") val settlementIfscCode: String,
    @SerializedName("bank_name") val bankName: String,
    @SerializedName("branch_name") val branchName: String
)

data class OrderTrack(
    @SerializedName("last_updated_at") val lastUpdatedAt: String,
    @SerializedName("tracking_data") val trackingData: List<TrackingData>
)

data class TrackingData(
    @SerializedName("order_status") val orderStatus: String,
    @SerializedName("order_time") val orderTime: String,
    @SerializedName("status_type") val statusType: String?,
    @SerializedName("status_value") val statusValue: String?,
    @SerializedName("message") val message: String?
)

data class Return(
    @SerializedName("type") val type: String,
    @SerializedName("request_id") val requestId: String,
    @SerializedName("item_id") val itemId: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("descriptor") val descriptor: Descriptor,
    @SerializedName("price") val price: Price,
    @SerializedName("reason_code") val reasonCode: String,
    @SerializedName("reason_desc") val reasonDesc: String,
    @SerializedName("ttl_approval") val ttlApproval: String,
    @SerializedName("ttl_reverse_qc") val ttlReverseQc: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class Cancel(
    @SerializedName("type") val type: String,
    @SerializedName("request_id") val requestId: String,
    @SerializedName("item_id") val itemId: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("descriptor") val descriptor: Descriptor,
    @SerializedName("price") val price: Price,
    @SerializedName("reason_code") val reasonCode: String,
    @SerializedName("reason_desc") val reasonDesc: String,
    @SerializedName("ttl_approval") val ttlApproval: String,
    @SerializedName("ttl_reverse_qc") val ttlReverseQc: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("track") val track: List<Track>? = null
)

data class Track(
    @SerializedName("order_status") val orderStatus: String,
    @SerializedName("status_type") val statusType: String,
    @SerializedName("status_value") val statusValue: String,
    @SerializedName("order_time") val orderTime: String
)

fun Order.toOrderDetails(): OrderDetails =
    OrderDetails(
        orderId = id,
        displayOrderId = displayOrderId,
        status = state,
        rating = rating,
        deliveryDetails = DeliveryDetails(
            store = com.ladecentro.domain.model.Store(
                image = store.descriptor.images[0],
                name = store.descriptor.name,
                shortAddress = store.locations[0].descriptor.shortDesc
            ),
            person = com.ladecentro.domain.model.Store(
                image = "",
                name = billing.name,
                shortAddress = billing.address.building
            )
        ),
        orderDetails = OrderDetail(
            totalPrice = quote.price.value,
            items = items.map { item ->
                ItemDetails(
                    image = item.descriptor.images[0],
                    quantity = item.quantity.selected.count,
                    name = item.descriptor.name,
                    price = item.price.value,
                    mrp = item.price.maximumValue
                )
            },
            priceBreakUp = quote.breakup.groupBy { br -> br.titleType }
                .mapValues { br ->
                    br.value.map { it.price.value.toDouble() }.sumOf { it }.toString()
                }
                .map {
                    PriceBreakUp(
                        name = it.key,
                        mrp = "",
                        price = it.value
                    )
                }
        ),
        paymentDetails = PaymentDetails(
            date = payment[0].timestamp,
            price = payment[0].amount,
            mode = payment[0].paymentTransaction[0].paymentMethod ?: "COD",
            info = payment[0].paymentTransaction[0].payerVpa ?: "",
            refNo = payment[0].pgOrderId
        ),
        lastUpdateOrderTrack = getFormattedDateTime(orderTrack.lastUpdatedAt)
    )
