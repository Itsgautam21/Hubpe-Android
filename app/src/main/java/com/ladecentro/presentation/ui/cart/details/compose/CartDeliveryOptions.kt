package com.ladecentro.presentation.ui.cart.details.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ladecentro.R.drawable
import com.ladecentro.R.raw
import com.ladecentro.common.bounceClick
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.data.remote.dto.FulfillmentRequest
import com.ladecentro.presentation.theme.Typography
import com.ladecentro.presentation.theme.background
import com.ladecentro.presentation.theme.border_light_gray
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.cart.details.CartDetailViewModel
import kotlin.time.Duration

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CartDeliveryOptions(vm: CartDetailViewModel = hiltViewModel()) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    AnimatedVisibility(vm.deliveryOptionsSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                vm.deliveryOptionsSheet = false
            },
            sheetState = sheetState,
            containerColor = background,
            contentColor = Color.Black,
            properties = ModalBottomSheetDefaults.properties(shouldDismissOnBackPress = false),
            dragHandle = {
                if (!vm.userOrder.isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Companion.White)
                            .padding(top = 24.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Choose delivery / pickup",
                            style = Typography.titleMedium.copy(fontFamily = fontFamilyHindBold)
                        )
                        Icon(
                            painter = painterResource(id = drawable.order_cancel),
                            contentDescription = "close dialog",
                            tint = border_light_gray,
                            modifier = Modifier
                                .size(24.dp)
                                .bounceClick {
                                    vm.deliveryOptionsSheet = false
                                }
                        )
                    }
                }
            }
        ) {
            if (vm.userCart.isLoading) {
                CartLoadingAnimate()
            }
            if (vm.userOrder.isLoading) {
                OrderLoadingAnimate()
            }
            vm.userCart.content?.let {
                if (!vm.userCart.isLoading && vm.userCart.error == null) {
                    DeliveryOptionContent(
                        it, vm.selectFulfillment, onToPayClick = vm::createOrder
                    ) { request ->
                        vm.selectFulfillment = request
                    }
                }
            }
            vm.userCart.error?.let {
                if (!vm.userCart.isLoading) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.5f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = it,
                            style = Typography.titleMedium.copy(fontFamily = fontFamilyHindBold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = { vm.createCartServer() }) {
                            Text(
                                text = "Try again",
                                style = Typography.bodyLarge.copy(color = primary_orange)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DeliveryOptionContent(
    cart: CartDto,
    selected: FulfillmentRequest?,
    onToPayClick: () -> Unit,
    onFulfillmentClick: (request: FulfillmentRequest) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        cart.fulfillment?.let { f ->
            items(f) {
                SampleDeliveryOption(
                    it, selected,
                    cart.quote!!.breakup.find { br -> br.itemId == it.id && br.titleType == "delivery" }!!.price.value
                )
                { id -> onFulfillmentClick(id) }
            }
        }
        cart.quote?.let { quote ->
            val breakup = getQuoteBreakUp(quote, selected?.id!!)
            val grandTotal = getTotalPriceByFulfillment(breakup)
            item {
                PriceBreakDown(breakup, grandTotal)
            }
            item {
                Button(
                    onClick = onToPayClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(48.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary_orange,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        buildAnnotatedString {
                            pushStyle(
                                Typography.titleSmall.copy(color = Companion.White).toSpanStyle()
                            )
                            append("Proceed to Pay ")
                            pushStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamilyHindBold
                                )
                            )
                            append("₹${grandTotal}/-")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SampleDeliveryOption(
    fulfillment: FulfillmentRequest,
    selected: FulfillmentRequest?,
    price: String,
    onFulfillmentClick: (request: FulfillmentRequest) -> Unit
) {
    val transition = updateTransition(selected, label = "update border color")
    val update = transition.animateColor(label = "animate color",
        transitionSpec = { tween(durationMillis = 300) }) { state ->
        if (state == null || selected != fulfillment) card_border
        else primary_orange
    }
    Surface(
        color = Color.White,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick {
                onFulfillmentClick(fulfillment)
            },
        border = BorderStroke(1.5.dp, update.value)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = fulfillment.category ?: "",
                    style = Typography.bodySmall.copy(color = light_text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = fulfillment.tat?.let { Duration.parse(it).toString() } ?: "",
                    style = Typography.titleSmall.copy(fontFamily = fontFamilyHindBold)
                )
            }
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = "₹$price",
                    style = Typography.bodySmall.copy(
                        color = Companion.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun CartLoadingAnimate() {

    val composition by rememberLottieComposition(spec = RawRes(raw.cart_loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.fillMaxSize(0.7f)
        )
    }
}

@Composable
fun OrderLoadingAnimate() {

    val composition by rememberLottieComposition(spec = RawRes(raw.order_loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Companion.White),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.fillMaxSize(0.5f)
        )
    }
}
