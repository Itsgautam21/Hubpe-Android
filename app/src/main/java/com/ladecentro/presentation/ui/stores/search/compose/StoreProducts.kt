package com.ladecentro.presentation.ui.stores.search.compose

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ladecentro.R
import com.ladecentro.common.Intents
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.theme.Typography
import com.ladecentro.presentation.ui.cart.details.CartDetailActivity
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent
import com.ladecentro.presentation.ui.search.compose.SampleSearchProduct
import com.ladecentro.presentation.ui.stores.details.compose.CartCompose
import com.ladecentro.presentation.ui.stores.details.compose.getItemTotal
import com.ladecentro.presentation.ui.stores.search.ProductSearchViewModel

@Composable
fun SearchStoreProducts(vm: ProductSearchViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val productSearch = vm.productSearch.collectAsLazyPagingItems()
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        if (vm.searchText.length < 3) {
            EmptySearchAnimation()
        } else {
            LazyColumn(modifier = Modifier) {

                when (productSearch.loadState.refresh) {
                    is LoadState.Loading -> {
                        item {
                            ShimmerContent()
                        }
                    }

                    is LoadState.NotLoading -> {
                        items(count = productSearch.itemCount, key = { d -> d }) { index ->
                            productSearch[index]?.let { product ->
                                Box(
                                    modifier = Modifier
                                        .background(Color.White)
                                        .padding(horizontal = 12.dp)
                                ) {
                                    SampleSearchProduct(product)
                                    HorizontalDashDivider()
                                }
                            }
                        }

                        when (productSearch.loadState.append) {
                            is LoadState.Loading -> {
                                item {
                                    LottieAnimation(
                                        composition = composition,
                                        progress = progress,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .height(50.dp)
                                    )
                                }
                            }

                            is LoadState.Error -> {}
                            is LoadState.NotLoading -> {}
                        }
                    }

                    is LoadState.Error -> {}
                }
            }
        }
        Column {
            AnimatedVisibility(visible = vm.cartState.content != null) {
                vm.cartState.content?.let { cart ->
                    CartCompose(
                        price = getItemTotal(cart),
                        itemCount = cart.items.sumOf { p -> p.quantity.selected.count }
                            .toString()
                    ) {
                        context.startActivity(
                            Intent(context, CartDetailActivity::class.java)
                                .putExtra(Intents.STORE_ID.name, vm.storeId)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptySearchAnimation() {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.empty_search_products))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(Color.White)
                .weight(1.5f)
        )
        Text(
            text = "Search products that will appear here",
            style = Typography.titleSmall,
            modifier = Modifier.weight(1f)
        )
    }
}