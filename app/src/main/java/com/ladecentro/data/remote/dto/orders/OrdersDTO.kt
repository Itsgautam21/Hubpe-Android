package com.ladecentro.data.remote.dto.orders

import com.google.gson.annotations.SerializedName
import com.ladecentro.common.OrderStatus
import com.ladecentro.common.getFormattedDateTime
import com.ladecentro.common.getOrderStatusIcon
import com.ladecentro.domain.model.Item
import com.ladecentro.domain.model.Orders
import com.ladecentro.domain.model.PaymentAndStatus
import com.ladecentro.domain.model.Store

data class OrdersDTO(
    @SerializedName("orders") val orders: List<Order>,
    @SerializedName("page") val page: Page
)

data class Page(
    @SerializedName("size") val size: Int,
    @SerializedName("current") val current: Int,
    @SerializedName("total") val total: Int,
)

fun OrdersDTO.toOrders(): List<Orders> {

    return this.orders.map { order ->
        Orders(
            id = order.id,
            displayOrderId = order.displayOrderId,
            store = Store(
                image = order.store.descriptor.images.getOrNull(0),
                name = order.store.descriptor.name,
                shortAddress = order.store.locations[0].descriptor.shortDesc
            ),
            items = order.items.map { item ->
                Item(
                    image = item.descriptor.images.getOrNull(0),
                    quantity = item.quantity.selected.count,
                    name = item.descriptor.name
                )
            },
            paymentAndStatus = PaymentAndStatus(
                createdDate = getFormattedDateTime(order.createdAt),
                status = OrderStatus.fromValue(order.state).value,
                statusIcon = getOrderStatusIcon(order.state),
                paymentType = order.payment[0].paymentTransaction[0].paymentMethod ?: "COD",
                totalPrice = order.quote.price.value
            ),
            rating = order.rating
        )
    }
}

data class UpdateOrderRequest(val type: String, val rating: String)