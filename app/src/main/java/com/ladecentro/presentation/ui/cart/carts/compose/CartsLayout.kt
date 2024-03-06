package com.ladecentro.presentation.ui.cart.carts.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.LifecycleEventObserver
import com.ladecentro.presentation.theme.background
import com.ladecentro.presentation.ui.cart.carts.CartViewModel

@Composable
fun CartsLayout(vm: CartViewModel = hiltViewModel()) {

    Scaffold(
        topBar = {
            TopAppBarCart()
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(background)
        ) {
            CartItemsList(vm.userCart)
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == ON_START) {
                vm.setUserCart()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
}