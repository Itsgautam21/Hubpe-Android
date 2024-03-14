package com.ladecentro.presentation.ui.cart.carts.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.LifecycleEventObserver
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ladecentro.R
import com.ladecentro.presentation.common.SimpleDialog
import com.ladecentro.presentation.theme.Typography
import com.ladecentro.presentation.theme.background
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.poppins
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.cart.carts.CartViewModel
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent

@Composable
fun CartsLayout(vm: CartViewModel = hiltViewModel()) {

    var dialogState by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBarCart(
                enabled = vm.userCart.content?.isNotEmpty() ?: false
            ) {
                dialogState = true
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(background)
        ) {
            if (vm.userCart.isLoading) {
                ShimmerContent()
            }
            vm.userCart.content?.let { carts ->
                if (carts.isEmpty()) {
                    EmptyCartAnimation()
                } else {
                    CartItemsList(carts)
                }
            }
            if (dialogState) {
                SimpleDialog(
                    dismissRequest = { dialogState = false },
                    negativeClick = { dialogState = false },
                    body = "Are you sure want to delete all carts?",
                    positiveClick = {
                        vm.deleteAllCarts()
                        dialogState = false
                    }
                )
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

@Composable
fun EmptyCartAnimation() {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.empty_cart))
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
            .background(Color.White)
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .fillMaxHeight(.5f)
                .background(Color.White)

        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Your cart is Empty",
            style = Typography.titleMedium.copy(fontFamily = poppins)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "You have no items in your shopping cart. Browse the store and items to cart before continuing",
            style = Typography.bodyMedium.copy(
                color = light_gray,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = primary_orange,
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(48.dp)
        ) {
            Text(
                text = "Start Shopping",
                style = Typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}