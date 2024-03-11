package com.ladecentro.presentation.ui.stores.stores.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadState.Loading
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ladecentro.R.raw
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyAverSans
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent
import com.ladecentro.presentation.ui.stores.stores.StoresViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun StoreComposeUI(vm: StoresViewModel = hiltViewModel()) {

    val nonPromotedStore = vm.nonPromotedStores.collectAsLazyPagingItems()
    val composition by rememberLottieComposition(spec = RawRes(raw.loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )

    if (vm.storeState.isLoading) {
        ShimmerContent()
    }
    vm.storeState.content?.let { content ->

        LazyColumn {

            item {
                StoresBanner()
            }
            item {
                Text(
                    text = "Spotlight",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = fontFamilyAverSans,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(content.stores.filter { it.isPromoted }) { store ->
                SampleStores(store, vm.favourite) {
                    //vm.saveFavourites(store.id)
                }
                HorizontalDashDivider()
            }
            item {
                ShopByCategory()
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(contentAlignment = Alignment.Center) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = card_border,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .padding()
                    ) {
                        Text(
                            text = "Explore More",
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            fontFamily = fontFamilyHind,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            stickyHeader {
                FilterSortStores()
            }
            items(nonPromotedStore.itemCount, key = { it }) { index ->
                nonPromotedStore[index]?.let { store ->
                    SampleStores(store, vm.favourite) {
                        //vm.saveFavourites(store.id)
                    }
                    HorizontalDashDivider()
                }
            }
            when (nonPromotedStore.loadState.append) {
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
                is LoadState.NotLoading -> {}
            }
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(card_background)
                )
            }
            item {
                Spacer(modifier = Modifier.height(0.dp))
            }
        }
    }
}