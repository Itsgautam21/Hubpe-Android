package com.ladecentro.presentation.ui.order.details.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ladecentro.domain.model.ItemDetails
import com.ladecentro.domain.model.OrderDetail
import com.ladecentro.domain.model.PriceBreakUp
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.theme.border_light_gray
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.doppio_one
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.light_text

@Composable
fun OrderDetails(orderDetail: OrderDetail, displayOrderId: String) {

    Card(
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, color = card_border)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text(
                    text = "Order Details",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = fontFamilyHind,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "#$displayOrderId",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = fontFamilyHind,
                    color = light_gray,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            orderDetail.items.forEach {
                SampleItem(item = it)
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDashDivider()
            Spacer(modifier = Modifier.height(16.dp))
            orderDetail.priceBreakUp.forEach {
                PriceBreakup(it)
            }
            HorizontalDashDivider()
            Spacer(modifier = Modifier.height(12.dp))
            GrandTotal(orderDetail.totalPrice)
        }
    }
}

@Composable
fun SampleItem(item: ItemDetails) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = "item image",
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = item.name,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            fontFamily = fontFamilyHind,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(horizontalAlignment = Alignment.End) {

            Box(contentAlignment = Alignment.Center, modifier = Modifier.width(Max)) {
                Text(
                    text = "₹${item.mrp}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    fontFamily = fontFamilyHind,
                    color = light_text,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = light_text,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = "${item.quantity} x ₹${item.price}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                fontFamily = fontFamilyHind,
                color = MaterialTheme.colorScheme.primary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun GrandTotal(price: String) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Grand Total",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            fontFamily = fontFamilyHind,
            color = MaterialTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "₹${price}",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            fontFamily = doppio_one,
            color = MaterialTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun PriceBreakup(breakUp: PriceBreakUp) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = breakUp.name,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            fontFamily = fontFamilyHind,
            color = MaterialTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
            maxLines = 1
        )
        Box(contentAlignment = Alignment.Center, modifier = Modifier.width(Max)) {
            Text(
                text = breakUp.mrp,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                fontFamily = fontFamilyHind,
                color = border_light_gray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = border_light_gray,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "₹${breakUp.price}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            fontFamily = fontFamilyHind,
            color = MaterialTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}