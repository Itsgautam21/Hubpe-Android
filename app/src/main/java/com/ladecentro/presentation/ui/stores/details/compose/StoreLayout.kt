package com.ladecentro.presentation.ui.stores.details.compose

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.LifecycleEventObserver
import com.ladecentro.common.Intents
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.cart.details.CartDetailActivity
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent
import com.ladecentro.presentation.ui.stores.details.StoreViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun StoreLayout(vm: StoreViewModel = hiltViewModel()) {

    val context = LocalContext.current

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            if (vm.store.isLoading || vm.products.isLoading) {
                ShimmerContent()
            }
            vm.store.content?.let { store ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        store.categories?.let { categories ->
                            item {
                                StoreTopCompose(store)
                            }
                            stickyHeader {
                                SearchStoreCompose(store.descriptor.name)
                            }
                            item {
                                BannerPager(store = store)
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                            item {
                                StoreCategories(categories, store.id)
                            }
                            items(categories) { category ->
                                StoreProducts(category)
                            }
                            item {
                                Spacer(modifier = Modifier.height(100.dp))
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
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == ON_START) {
                vm.getCartFromLocal()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
}

fun getItemTotal(cart: CartDto): String = cart.items
    .sumOf { p -> p.price.value.toDouble() * p.quantity.selected.count }
    .toString()

@Composable
fun CartCompose(price: String, itemCount: String, onButtonClick: () -> Unit) {

    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(0.dp),
        color = Companion.White,
        shadowElevation = 4.dp,
        border = BorderStroke(1.3.dp, card_border)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "₹$price",
                    fontSize = 18.sp,
                    fontFamily = fontFamilyHindBold,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = true
                        )
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$itemCount Items Added",
                    fontSize = 12.sp,
                    fontFamily = fontFamilyHind,
                    color = light_text,
                    fontWeight = FontWeight.SemiBold,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = true
                        )
                    )
                )
            }
            Button(
                onClick = {
                    onButtonClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary_orange,
                    contentColor = Companion.Black
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Go to Cart",
                    fontSize = 16.sp,
                    fontFamily = fontFamilyHindBold,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = true
                        )
                    )
                )
            }
        }
    }
}
