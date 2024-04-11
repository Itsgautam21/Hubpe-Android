package com.ladecentro.domain.model

import androidx.compose.runtime.Stable
import com.ladecentro.data.remote.dto.orders.Track
import javax.annotation.concurrent.Immutable

@Stable
data class OrderDetails(
    val orderId: String,
    val displayOrderId: String,
    val status: String,
    val rating: String?,
    val deliveryDetails: DeliveryDetails,
    val orderDetails: OrderDetail,
    val paymentDetails: PaymentDetails,
    val lastUpdateOrderTrack: String,
    val refunds: List<PaymentDetails>? = null,
    val returns: List<ReturnDetail>? = null,
    val cancels: List<ReturnDetail>? = null,
)

data class DeliveryDetails(
    val type: String,
    val store: Store,
    val person: Store
)

data class OrderDetail(
    val totalPrice: String,
    val items: List<ItemDetails>,
    val priceBreakUp: List<PriceBreakUp>
)

@Immutable
data class ItemDetails(
    val id: String? = null,
    val image: String?,
    val quantity: Int,
    val name: String,
    val brand: String? = null,
    val description: String? = null,
    val price: String,
    val mrp: String,
    val cancellable: String = "",
    val returnable: String = "",
    val categoryId: String? = null,
    val selected: Boolean = false
)

data class PriceBreakUp(
    val name: String,
    val mrp: String,
    val price: String
)

data class PaymentDetails(
    val date: String,
    val price: String,
    val mode: String,
    val info: String,
    val refNo: String
)

data class ReturnDetail(
    val item: ItemDetails,
    val track: List<Track>? = null
)