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
import com.ladecentro.data.remote.dto.orders.Quote
import com.ladecentro.data.remote.dto.orders.toPriceBreakup
import com.ladecentro.domain.model.PriceBreakUp
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.ui.order.details.compose.GrandTotal
import com.ladecentro.presentation.ui.order.details.compose.PriceBreakup

@Composable
fun PriceBreakDown(quote: Quote) {

    Surface(
        color = Color.White,
        contentColor = Color.Black,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, card_border)
    ) {

        val list = listOf(
            PriceBreakUp("item", "456.0", "163"),
            PriceBreakUp("item", "456.0", "163"),
            PriceBreakUp("item", "456.0", "163"),
            PriceBreakUp("item", "456.0", "163"),
        )
        Column(modifier = Modifier.padding(16.dp)) {
            quote.toPriceBreakup().forEach {
                PriceBreakup(breakUp = it)
            }
            HorizontalDashDivider()
            Spacer(modifier = Modifier.height(12.dp))
            GrandTotal(quote.price.value)
        }
    }
}