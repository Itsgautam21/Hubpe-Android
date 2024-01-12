package com.ladecentro.presentation.ui.order.orders.compose

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadState.Loading
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ladecentro.R.raw.*
import com.ladecentro.common.Intents
import com.ladecentro.common.OrderStatus
import com.ladecentro.common.bounceClick
import com.ladecentro.domain.model.Item
import com.ladecentro.domain.model.Orders
import com.ladecentro.domain.model.PaymentAndStatus
import com.ladecentro.domain.model.Store
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.common.RatingBar
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.doppio_one
import com.ladecentro.presentation.theme.fontFamilyFredoka
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.order.details.OrderDetailsActivity
import com.ladecentro.presentation.ui.order.orders.OrdersViewModel

@Composable
fun SampleMyOrder(order: Orders, vm: OrdersViewModel = hiltViewModel()) {

    val context = LocalContext.current

    Card(
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, card_border),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick(true) {
                context.startActivity(
                    Intent(context, OrderDetailsActivity::class.java).putExtra(
                        Intents.ORDER_ID.name,
                        order.id
                    )
                )
            },
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
    ) {
        Column(modifier = Modifier.background(card_background)) {
            StoreDetails(order.store)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            order.items.forEach {
                ItemDetails(it)
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                HorizontalDivider(thickness = 1.dp, color = card_border)
                Card(
                    border = BorderStroke(1.dp, card_border), colors = CardDefaults.cardColors(
                        contentColor = Color.Black, containerColor = Color.White
                    )
                ) {
                    Text(
                        text = "Ordered ID : #${order.displayOrderId}",
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        fontFamily = fontFamilyHind,
                        color = light_gray,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            PaymentsAndStatus(order.paymentAndStatus)
            Spacer(modifier = Modifier.height(4.dp))
            if (order.paymentAndStatus.status == OrderStatus.COMPLETED.value) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDashDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Rating(order.rating) {
                    vm.updateOrderRating(it, order.id)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoreDetails(store: Store) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, card_border),
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            AsyncImage(
                model = store.image,
                contentDescription = "store logo",
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Inside
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = store.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = fontFamilyHind,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = store.shortAddress,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                fontFamily = fontFamilyHind,
                color = light_gray,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "forward arrow",
            modifier = Modifier.size(28.dp),
        )
    }
    HorizontalDivider(thickness = 1.dp, color = card_border)
}

@Composable
fun ItemDetails(item: Item) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(0.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                LoadImage(
                    image = item.image,
                    modifier = Modifier.size(30.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "${item.quantity} x ${item.name}",
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                fontFamily = fontFamilyHind,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun PaymentsAndStatus(paymentAndStatus: PaymentAndStatus) {
    Row {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.weight(1f)) {
            Text(
                text = paymentAndStatus.createdDate,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                fontFamily = fontFamilyHind,
                color = light_gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = paymentAndStatus.statusIcon),
                    contentDescription = "Order Complete",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = paymentAndStatus.status,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    fontFamily = fontFamilyFredoka,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)) {
            Text(
                text = paymentAndStatus.paymentType,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                fontFamily = fontFamilyHind,
                color = light_gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "₹${paymentAndStatus.totalPrice}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = doppio_one,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun Rating(rating: String?, onRatingClick: (rating: String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
            Text(
                text = if (rating.isNullOrBlank()) "How would you rate this order?" else "You Rated",
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                fontFamily = fontFamilyHind,
                color = light_gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            RatingBar(rating = rating?.toInt() ?: 0) {
                onRatingClick(it.toString())
            }
        }
        Button(
            onClick = { }, colors = ButtonDefaults.buttonColors(
                containerColor = primary_orange, contentColor = Color.White
            ), shape = RoundedCornerShape(12.dp), contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            Icon(
                imageVector = Rounded.Refresh,
                contentDescription = "Reorder",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Reorder",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                fontFamily = fontFamilyHind,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyOrders(vm: OrdersViewModel = hiltViewModel()) {

    val state = vm.ordersState.collectAsLazyPagingItems()
    var visibleState by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val composition by rememberLottieComposition(spec = RawRes(loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )
    SwipeRefresh(state = swipeRefreshState, onRefresh = vm::getAllOrders) {

        AnimatedVisibility(visible = visibleState) {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp
                ), verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(count = state.itemCount, key = { it }) {
                    state[it]?.let { order ->
                        Box(modifier = Modifier.animateItemPlacement()) {
                            SampleMyOrder(order)
                        }
                    }
                }
                when (state.loadState.append) {
                    is Loading -> {
                        item {
                            LottieAnimation(
                                composition = composition,
                                progress = progress,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            )
                        }
                    }

                    is LoadState.Error -> {}
                    is LoadState.NotLoading -> {}
                }
            }

        }
        when (state.loadState.refresh) {
            is Loading -> {
                swipeRefreshState.isRefreshing = true
                ShimmerContent()
                visibleState = false
            }

            is LoadState.NotLoading -> {
                swipeRefreshState.isRefreshing = false
                visibleState = true
            }

            is LoadState.Error -> {
                swipeRefreshState.isRefreshing = false
                visibleState = false
            }
        }
    }
}