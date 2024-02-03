package com.ladecentro.presentation.ui.cart.details.compose

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.data.remote.dto.toProductDetail
import com.ladecentro.data.remote.dto.toStoreDetail
import com.ladecentro.domain.model.ItemDetails
import com.ladecentro.domain.model.Store
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.ui.cart.details.CartDetailViewModel

@Composable
fun CartDetails(vm: CartDetailViewModel = hiltViewModel()) {

    val context = LocalContext.current as Activity

    Surface(
        color = Color.White,
        contentColor = Companion.Black,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, card_border)
    ) {
        Column {
            vm.userCart.content?.let { cart ->
                CartStore(cart.store.toStoreDetail())
                cart.items.forEach { p ->
                    CartItems(
                        p.toProductDetail(),
                        onMinusClick = { vm.updateCart(p, operation = "-") },
                        onPlusClick = { vm.updateCart(p, operation = "+") }
                    )
                }
            }
            LaunchedEffect(key1 = vm.deleteCart) {
                if (vm.deleteCart.content != null) {
                    context.finish()
                }
            }
        }
    }
}

@Composable
fun CartStore(store: Store) {

    Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Companion.White,
            border = BorderStroke(1.dp, card_border)
        ) {
            LoadImage(image = store.image, modifier = Modifier.size(54.dp))
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = store.name,
                fontFamily = fontFamilyHindBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = store.shortAddress ?: "",
                fontFamily = fontFamilyHind,
                fontSize = 12.sp,
                color = light_gray,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
            )
        }
    }
    HorizontalDivider(thickness = 1.dp, color = card_border)
}

@Composable
fun CartItems(product: ItemDetails, onPlusClick: () -> Unit, onMinusClick: () -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Companion.White,
            border = BorderStroke(1.dp, card_border)
        ) {
            LoadImage(
                image = product.image,
                modifier = Modifier.size(47.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = product.name,
                fontFamily = fontFamilyHind,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                maxLines = 2,

                overflow = TextOverflow.Ellipsis,
                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))

            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "₹${product.price}",
                fontFamily = fontFamilyHindBold,
                fontSize = 14.sp,
                color = light_gray,
                fontWeight = FontWeight.Bold
            )
        }
        Surface(
            color = Companion.White,
            shape = MaterialTheme.shapes.large,
            contentColor = Companion.Black,
            border = BorderStroke(1.dp, card_border)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { onMinusClick() }, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = product.quantity.toString(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamilyHindBold,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = { onPlusClick() }, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Rounded.Add,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
    Row(modifier = Modifier.padding(start = 80.dp)) {
        HorizontalDashDivider()
    }
}