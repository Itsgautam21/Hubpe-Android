package com.ladecentro.presentation.ui.cart.details.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.common.bounceClick
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.theme.Typography
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_orange
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.cart.details.CartDetailViewModel
import com.ladecentro.presentation.ui.stores.details.compose.getItemTotal

@Composable
fun CartFooter(vm: CartDetailViewModel = hiltViewModel()) {

    vm.userCart.content?.let {
        Column {
            Surface(
                color = light_orange,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 0.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                shadowElevation = 8.dp
            ) {
                Text(
                    text = "Delivery In ${it.store.fulfillments[0].deliveryTime}",
                    fontFamily = fontFamilyHindBold,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = primary_orange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(0.dp),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                AnimatedVisibility(visible = !vm.userLocation.mobileNumber.isNullOrBlank()) {
                    Column {
                        CartDelivery(vm.userLocation) { vm.openSheet = true }
                        HorizontalDashDivider()
                        ViewDeliveryOptions(
                            price = getItemTotal(it),
                            buttonText = "View delivery options"
                        ) {
                            vm.deliveryOptionsSheet = true
                        }
                    }
                }
                if (vm.userLocation.mobileNumber.isNullOrBlank()) {
                    ViewDeliveryOptions(
                        price = getItemTotal(it),
                        buttonText = "Add Address to Proceed"
                    ) {
                        vm.openSheet = true
                    }
                }
            }
        }
    }
}

@Composable
fun CartDelivery(location: LocationRequest, onChangeClick: () -> Unit) {

    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Delivery at ${location.descriptor?.name}",
                style = Typography.titleSmall.copy(fontFamily = fontFamilyHindBold)
            )
            Text(
                text = location.descriptor?.longDesc ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = Typography.bodySmall.copy(fontWeight = FontWeight.Normal)
            )
            Text(
                text = "${location.address?.name} (${location.mobileNumber})",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = Typography.bodySmall.copy(
                    color = light_text,
                    fontWeight = FontWeight.Normal
                )
            )
        }
        Text(
            text = "Change",
            style = Typography.bodySmall.copy(color = primary_orange),
            modifier = Modifier
                .padding(top = 8.dp)
                .border(
                    1.dp,
                    primary_orange,
                    RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .bounceClick { onChangeClick() }
        )
    }
}

@Composable
fun ViewDeliveryOptions(price: String, buttonText: String, onButtonClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "₹$price",
                fontSize = 20.sp,
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
                text = "Total payable",
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
                contentColor = Color.Black
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = buttonText,
                fontSize = 14.sp,
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