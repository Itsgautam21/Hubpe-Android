package com.ladecentro.presentation.ui.order.details.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R
import com.ladecentro.common.bounceClick
import com.ladecentro.common.getFormattedDateTime
import com.ladecentro.data.remote.dto.orders.OrderStatus
import com.ladecentro.domain.model.OrderDetails
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.theme.Montserrat
import com.ladecentro.presentation.theme.border_light_gray
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.order.details.OrderDetailsViewModel
import com.ladecentro.presentation.ui.order.orders.compose.Rating

@Composable
fun OrderTrackDetails(lastUpdatedAt: String, vm: OrderDetailsViewModel = hiltViewModel()) {

    val orderTrackState by vm.orderTrack.collectAsState()

    Card(
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        border = BorderStroke(1.dp, color = card_border)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text(
                    text = "Order Track",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = fontFamilyHind,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = lastUpdatedAt,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = fontFamilyHind,
                    color = light_gray
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            orderTrackState.content?.let { tracks ->
                val count = tracks.count { it.activities != null }
                tracks.forEachIndexed { index, orderStatus ->
                    Tracking(orderStatus, index == count - 1, index == tracks.size - 1)
                }
            }
        }
    }
}

@Composable
fun Tracking(track: OrderStatus, isCurrent: Boolean = false, isLast: Boolean = false) {

    var descState by remember { mutableStateOf(isCurrent) }
    Row(modifier = Modifier.height(Max), horizontalArrangement = Arrangement.Center) {
        Column {
            TrackCircle(!track.activities.isNullOrEmpty())
            if (!isLast) {
                VerticalDivider(
                    thickness = 1.dp,
                    color = border_light_gray,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp)
                )
            }

        }
        Column {
            Text(
                text = track.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                fontFamily = Montserrat,
                color = if (track.activities.isNullOrEmpty()) light_text else MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .offset(y = (-2).dp)
                    .bounceClick { descState = !descState },
                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
            )
            Column(modifier = Modifier.padding(12.dp)) {
                AnimatedVisibility(visible = descState) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.offset(y = (-6).dp)
                    ) {
                        track.activities?.forEach {
                            Column {
                                Text(
                                    text = it.message,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    fontFamily = fontFamilyHind,
                                    color = light_text
                                )
                                Text(
                                    text = getFormattedDateTime(it.orderTime),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    fontFamily = fontFamilyHind,
                                    color = light_text
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrackCircle(isComplete: Boolean = true) {
    Surface(
        modifier = Modifier.size(16.dp),
        shape = RoundedCornerShape(20.dp),
        color = if (isComplete) primary_orange else border_light_gray
    ) {
        Surface(
            modifier = Modifier.padding(4.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color.White
        ) {

        }
    }
}

@Composable
fun OrderDeliveredStatus(order: OrderDetails) {

    Card(
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        border = BorderStroke(1.dp, color = card_border)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Your Order has been Delivered",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = fontFamilyHind,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Delivered on ${order.lastUpdateOrderTrack}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        fontFamily = fontFamilyHind,
                        color = light_text
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.order_complete),
                    contentDescription = "Order Complete",
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDashDivider()
            Spacer(modifier = Modifier.height(12.dp))
            Rating(order.rating, order.orderId)
        }
    }
}

@Composable
fun OrderCancelCompose(order: OrderDetails) {

    Card(
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color(0xFFFFEFEF)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Row(modifier = Modifier.padding(20.dp)) {
            Image(
                painter = painterResource(id = R.drawable.order_cancel),
                contentDescription = "Order Complete",
                modifier = Modifier
                    .padding(top = 4.dp)
                    .height(16.dp)
                    .width(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Your Order has been Cancelled",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = fontFamilyHind,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cancelled on ${order.lastUpdateOrderTrack}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    fontFamily = fontFamilyHind,
                    color = light_text
                )
            }
        }
    }
}