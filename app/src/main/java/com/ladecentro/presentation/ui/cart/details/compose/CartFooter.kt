package com.ladecentro.presentation.ui.cart.details.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_orange
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.cart.details.CartDetailViewModel

@Composable
@Preview(showBackground = true)
fun CartFooter(vm: CartDetailViewModel = hiltViewModel()) {

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
                text = "Delivery In 20 min",
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
                    CartDelivery(
                        name = vm.userLocation.descriptor?.name ?: "",
                        shortAdd = vm.userLocation.descriptor?.longDesc ?: ""
                    ) { vm.openSheet = true }
                    HorizontalDashDivider()
                    ViewDeliveryOptions(price = "175", buttonText = "View delivery options") {
                        vm.deliveryOptionsSheet = true
                    }
                }
            }
            if (vm.userLocation.mobileNumber.isNullOrBlank()) {
                ViewDeliveryOptions(price = "175", buttonText = "Add Address to Proceed") {
                    vm.openSheet = true
                }
            }
        }
    }
}

@Composable
fun CartDelivery(name: String, shortAdd: String, onChangeClick: () -> Unit) {

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Delivery at $name",
                fontSize = 16.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = true
                    )
                )
            )
            Text(
                text = "Change",
                fontSize = 14.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.SemiBold,
                color = primary_orange,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = true
                    )
                ),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    onChangeClick()
                }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = shortAdd,
            fontSize = 13.sp,
            fontFamily = fontFamilyHind,
            fontWeight = FontWeight.Medium,
            color = light_text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = true
                )
            )
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