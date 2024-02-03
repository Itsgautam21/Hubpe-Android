package com.ladecentro.presentation.ui.cart.details.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R
import com.ladecentro.R.drawable
import com.ladecentro.common.bounceClick
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.presentation.theme.Typography
import com.ladecentro.presentation.theme.border_light_gray
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.cart.details.CartDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDeliveryOptions(vm: CartDetailViewModel = hiltViewModel()) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    AnimatedVisibility(vm.deliveryOptionsSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                vm.deliveryOptionsSheet = false
            },
            sheetState = sheetState,
            containerColor = card_background,
            contentColor = Color.Black,
            dragHandle = {
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
        ) {
            vm.userCart.content?.let {
                DeliveryOptionContent(it)
            }
        }
    }
}

@Composable
fun DeliveryOptionContent(cart: CartDto) {

    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(3) {
            SampleDeliveryOption()
        }
        cart.quote?.let { quote ->
            item {
                PriceBreakDown(quote)
            }
            item {
                Button(
                    onClick = { },
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
                        text = "Proceed to Pay ${quote.price.value}",
                        style = Typography.titleSmall.copy(
                            color = Companion.White
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SampleDeliveryOption() {

    Surface(
        color = Color.White,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, card_border)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = "Standard delivery",
                    style = Typography.bodySmall.copy(color = light_text)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tomorrow, 10:30pm ",
                    style = Typography.titleSmall.copy(fontFamily = fontFamilyHindBold)
                )
            }
        }
    }
}
