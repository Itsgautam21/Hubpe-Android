package com.ladecentro.presentation.ui.cart.details.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ladecentro.common.getQuoteBreakupTitle
import com.ladecentro.data.remote.dto.orders.Quote
import com.ladecentro.domain.model.PriceBreakUp
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.ui.order.details.compose.GrandTotal
import com.ladecentro.presentation.ui.order.details.compose.PriceBreakup

@Composable
fun PriceBreakDown(breakUp: List<PriceBreakUp>, total: String) {

    Surface(
        color = Color.White,
        contentColor = Color.Black,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, card_border)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            breakUp.forEach {
                PriceBreakup(breakUp = it)
            }
            HorizontalDashDivider()
            Spacer(modifier = Modifier.height(12.dp))
            GrandTotal(total)
        }
    }
}

fun getTotalPriceByFulfillment(breakup: List<PriceBreakUp>) =
    breakup.map { br -> br.price.toDouble() }.sumOf { it }.toString()

fun getQuoteBreakUp(quote: Quote, id: String) =
    quote.breakup.groupBy { br -> br.titleType }
        .mapValues { map ->
            val v = if (map.value.find { br -> br.itemId == id } == null) map.value
            else map.value.filter { br -> br.itemId == id }
            PriceBreakUp(
                name = getQuoteBreakupTitle(map.value[0]),
                mrp = "",
                price = v.map { it.price.value.toDouble() }.sumOf { it }.toString()
            )
        }.map { it.value }
