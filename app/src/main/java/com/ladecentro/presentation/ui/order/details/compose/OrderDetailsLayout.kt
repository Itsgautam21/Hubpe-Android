package com.ladecentro.presentation.ui.order.details.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ladecentro.common.OrderStatus
import com.ladecentro.presentation.common.SimpleTopAppBar
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.ui.order.details.OrderDetailsViewModel
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent

@Composable
fun OrderDetailsLayout(vm: OrderDetailsViewModel = hiltViewModel()) {

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val orderState by vm.orderDetails.collectAsState()

    Scaffold(topBar = {
        SimpleTopAppBar(title = "Order Details")
    }) {
        SwipeRefresh(state = swipeRefreshState, onRefresh = {}) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(card_background)
                    .verticalScroll(rememberScrollState())
            ) {
                if (orderState.isLoading) {
                    ShimmerContent()
                }
                orderState.content?.let {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DeliveryDetails(it.deliveryDetails)
                        OrderDetails(it.orderDetails, it.displayOrderId)
                        if (it.status.equals(OrderStatus.COMPLETED.value, true)) {
                            OrderDeliveredStatus(it)
                        }
                        if (it.status.equals(OrderStatus.CANCELLED.value, true)) {
                            OrderCancelCompose(it)
                        }
                        OrderTrackDetails(it.lastUpdateOrderTrack)
                        PaymentDetails(it.paymentDetails)
                        RaiseCancelReturn()
                    }
                }
            }
        }
    }
}