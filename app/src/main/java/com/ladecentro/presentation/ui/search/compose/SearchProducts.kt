package com.ladecentro.presentation.ui.search.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.LoadState.Loading
import androidx.paging.LoadState.NotLoading
import androidx.paging.compose.LazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ladecentro.R.raw
import com.ladecentro.common.bounceClick
import com.ladecentro.data.remote.dto.Product
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.doppio_one
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_orange
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent

@Composable
fun SampleSearchProduct(product: Product) {

    Card(
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Companion.Black
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.bounceClick { }
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.storeDescriptor.name ?: "",
                    fontSize = 13.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.Bold,
                    color = primary_orange,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.descriptor.name,
                    fontSize = 15.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "₹${product.price.value}",
                    fontSize = 15.sp,
                    fontFamily = doppio_one,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.height(126.dp)) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, card_border),
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Companion.White
                ) {
                    LoadImage(
                        image = product.descriptor.images.getOrNull(index = 0),
                        modifier = Modifier.size(110.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
                TextButton(
                    onClick = { },
                    border = BorderStroke(1.dp, primary_orange),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .defaultMinSize(minHeight = 32.dp)
                        .height(32.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = light_orange,
                        contentColor = Companion.Black
                    ),
                    elevation = ButtonDefaults.buttonElevation(3.dp)
                ) {
                    Text(
                        text = "ADD",
                        fontSize = 13.sp,
                        fontFamily = fontFamilyHind,
                        fontWeight = FontWeight.Bold,
                        color = primary_orange,
                        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                    )
                }
            }
        }
    }
}

@Composable
fun SearchProducts(productSearch: LazyPagingItems<Product>, onLoad: () -> Unit) {

    val composition by rememberLottieComposition(spec = RawRes(raw.loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )

    when (productSearch.loadState.refresh) {

        is Loading -> {
            ShimmerContent()
        }

        is NotLoading -> {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                items(count = productSearch.itemCount, key = { it }) { index ->
                    productSearch[index]?.let { product ->
                        SampleSearchProduct(product)
                        HorizontalDashDivider()
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
            onLoad()
        }

        is LoadState.Error -> {}
    }
}