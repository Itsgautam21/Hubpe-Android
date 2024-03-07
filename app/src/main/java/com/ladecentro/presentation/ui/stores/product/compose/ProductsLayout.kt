package com.ladecentro.presentation.ui.stores.product.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadState.Loading
import androidx.paging.LoadState.NotLoading
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ladecentro.R.raw
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.theme.background
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent
import com.ladecentro.presentation.ui.search.compose.SampleSearchProduct
import com.ladecentro.presentation.ui.stores.details.compose.SearchStoreCompose
import com.ladecentro.presentation.ui.stores.product.ProductsViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ProductsLayout(vm: ProductsViewModel = hiltViewModel()) {

    val productSearch = vm.productSearch.collectAsLazyPagingItems()
    val composition by rememberLottieComposition(spec = RawRes(raw.loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(background)
        ) {
            LazyColumn {
                item {
                    TopBarProducts(category = vm.category, storeName = vm.storeId)
                }
                stickyHeader {
                    SearchStoreCompose(vm.category)
                }
                when (productSearch.loadState.refresh) {
                    is Loading -> {
                        item {
                            ShimmerContent()
                        }
                    }

                    is NotLoading -> {
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
                            is Loading -> {
                                item {
                                    LottieAnimation(
                                        composition = composition,
                                        progress = progress,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(card_background)
                                            .height(50.dp)
                                    )
                                }
                            }

                            is LoadState.Error -> {}
                            is NotLoading -> {}
                        }
                    }

                    is LoadState.Error -> {}
                }
            }
        }
    }
}