package com.ladecentro.presentation.ui.cart.details.compose

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R.drawable
import com.ladecentro.common.Intents
import com.ladecentro.common.bounceClick
import com.ladecentro.data.remote.dto.toProductDetail
import com.ladecentro.data.remote.dto.toStoreDetail
import com.ladecentro.domain.model.ItemDetails
import com.ladecentro.domain.model.Store
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.theme.Typography
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.cart.details.CartDetailViewModel
import com.ladecentro.presentation.ui.stores.details.StoreActivity

@Composable
fun CartDetails(vm: CartDetailViewModel = hiltViewModel()) {

    val context = LocalContext.current as Activity

    Surface(
        color = Color.White,
        contentColor = Companion.Black,
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            vm.userCart.content?.let { cart ->
                CartStore(cart.store.toStoreDetail())
                cart.items.forEach { p ->
                    CartItems(
                        p.toProductDetail(),
                        !vm.userCart.isLoading,
                        onMinusClick = { vm.updateCart(p, operation = "-") },
                        onPlusClick = { vm.updateCart(p, operation = "+") }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp)
                        .bounceClick {
                            context.startActivity(
                                Intent(context, StoreActivity::class.java).putExtra(
                                    Intents.STORE_ID.name, cart.store.id
                                )
                            )
                            context.finish()
                        }
                ) {
                    Icon(
                        imageVector = Rounded.Add, contentDescription = null, tint = light_text,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Add more items",
                        style = Typography.bodyLarge.copy(color = light_text)
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
            Spacer(modifier = Modifier.height(4.dp))
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
fun CartItems(product: ItemDetails, isEnable: Boolean = true, onPlusClick: () -> Unit, onMinusClick: () -> Unit) {

    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Companion.White,
            border = BorderStroke(1.dp, card_border)
        ) {
            LoadImage(
                image = product.image,
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = "${product.cancellable} & ${product.returnable}",
                style = Typography.labelMedium.copy(color = light_gray)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = product.name,
                style = Typography.bodyLarge.copy(letterSpacing = 0.sp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "₹${product.price}",
                    style = Typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = fontFamilyHindBold
                    ),
                )
                Spacer(modifier = Modifier.width(6.dp))
                if (product.price.toDouble() != product.mrp.toDouble()) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.width(Max)) {
                        Text(
                            text = "₹${product.mrp}",
                            style = Typography.bodyMedium.copy(color = light_text),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = light_text,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            if (product.quantity < 1) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Item out of stock",
                    style = Typography.bodySmall.copy(color = MaterialTheme.colorScheme.error),
                    modifier = Modifier
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.error,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
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
                if (product.quantity > 0) {
                    IconButton(onClick = onMinusClick, modifier = Modifier.size(32.dp), enabled = isEnable) {
                        Icon(
                            painter = painterResource(id = drawable.icons_minus),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
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
                    IconButton(onClick = onPlusClick, modifier = Modifier.size(32.dp), enabled = isEnable) {
                        Icon(
                            imageVector = Rounded.Add,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    IconButton(onClick = { onMinusClick() }, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
    Row(modifier = Modifier.padding(start = 72.dp)) {
        HorizontalDashDivider()
    }
}

@Composable
@Preview(showBackground = true)
fun CartInfo() {

    Surface(color = Color.White, shape = MaterialTheme.shapes.medium) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Before you proceed, please note!",
                style = Typography.bodyLarge.copy(fontFamily = fontFamilyHindBold)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    painter = painterResource(id = drawable.order_cancel),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 3.dp)
                        .size(12.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    buildAnnotatedString {
                        pushStyle(
                            Typography.bodySmall.copy(fontWeight = FontWeight.Normal).toSpanStyle()
                        )
                        pushStyle(Typography.bodySmall.copy(lineHeight = 16.sp).toParagraphStyle())
                        append("This order")
                        pushStyle(
                            Typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamilyHindBold
                            ).toSpanStyle()
                        )
                        append(" cannot be cancelled ")
                        pop()
                        append("once accepted by the store.")
                    }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Rounded.Info,
                    contentDescription = null,
                    modifier = Modifier.padding(top = 1.dp).size(14.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    buildAnnotatedString {
                        pushStyle(
                            Typography.bodySmall.copy(fontWeight = FontWeight.Normal).toSpanStyle()
                        )
                        pushStyle(Typography.bodySmall.copy(lineHeight = 16.sp).toParagraphStyle())
                        append("All items are")
                        pushStyle(
                            Typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamilyHindBold
                            ).toSpanStyle()
                        )
                        append(" non-returnable ")
                        pop()
                        append("after order is delivered.")
                    }
                )
            }
            HorizontalDashDivider()
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Cancellation/refund policies are set by the store",
                    style = Typography.bodySmall.copy(
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.sp
                    ),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(24.dp))
                TextButton(
                    onClick = {},
                    shape = MaterialTheme.shapes.small,
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    modifier = Modifier
                        .offset(x = 8.dp)
                        .defaultMinSize(minHeight = 28.dp)
                        .height(28.dp)
                ) {
                    Text(
                        text = "Read Policy",
                        style = Typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = primary_orange
                        )
                    )
                    Spacer(modifier = Modifier.width(0.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = primary_orange
                    )
                }
            }
        }
    }
}