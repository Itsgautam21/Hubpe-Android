package com.ladecentro.common

import com.ladecentro.R.drawable
import com.ladecentro.common.OrderStatus.ACCEPTED
import com.ladecentro.common.OrderStatus.CANCELLED
import com.ladecentro.common.OrderStatus.COMPLETED
import com.ladecentro.common.OrderStatus.CREATED
import com.ladecentro.common.OrderStatus.IN_PROGRESS
import com.ladecentro.common.OrderStatus.NEW
import com.ladecentro.common.OrderStatus.PENDING
import com.ladecentro.data.remote.dto.orders.Breakup
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import kotlin.time.Duration

fun getOrderStatusIcon(status: String): Int = when (OrderStatus.fromValue(status)) {
    PENDING -> drawable.order_pending
    NEW -> drawable.order_pending
    CREATED -> drawable.order_accepted
    ACCEPTED -> drawable.order_accepted
    IN_PROGRESS -> drawable.order_ready
    COMPLETED -> drawable.order_complete
    CANCELLED -> drawable.order_cancel
}

fun getFormattedDateTime(date: String, format: String = "EEE, MMM dd, yyyy, HH:mm"): String =
    SimpleDateFormat(format, Locale.US).format(Date.from(Instant.parse(date)))

fun getQuoteBreakupTitle(breakup: Breakup): String {

    return when (breakup.titleType) {
        "item" -> "Item Total"
        "tax" -> "Tax"
        else -> breakup.title
    }
}

fun getReadableDuration(value: String?) {

    value?.let {
        val duration = Duration.parse(it).toString()
    }
}