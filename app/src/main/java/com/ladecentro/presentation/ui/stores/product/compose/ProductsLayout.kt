package com.ladecentro.presentation.ui.stores.product.compose

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
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
import com.ladecentro.common.Intents
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.theme.background
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.ui.cart.details.CartDetailActivity
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent
import com.ladecentro.presentation.ui.search.compose.SampleSearchProduct
import com.ladecentro.presentation.ui.stores.details.compose.CartCompose
import com.ladecentro.presentation.ui.stores.details.compose.getItemTotal
import com.ladecentro.presentation.ui.stores.product.ProductsViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProductsLayout(vm: ProductsViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val productSearch = vm.productSearch.collectAsLazyPagingItems()
    val composition by rememberLottieComposition(spec = RawRes(raw.loading))
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollBehaviourTop = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )

    Scaffold(topBar = {
        TopAppBarStoreProducts(
            scrollBehaviourTop = scrollBehaviourTop,
            scrollBehaviour = scrollBehaviour
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(background)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                LazyColumn(
                    modifier = Modifier
                        .nestedScroll(scrollBehaviourTop.nestedScrollConnection)
                        .nestedScroll(scrollBehaviour.nestedScrollConnection)
                ) {
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
    }
}