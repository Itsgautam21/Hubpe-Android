package com.ladecentro.presentation.ui.cart.carts.compose

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ladecentro.common.Intents
import com.ladecentro.common.bounceClick
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.data.remote.dto.toProductDetail
import com.ladecentro.data.remote.dto.toStoreDetail
import com.ladecentro.domain.model.ItemDetails
import com.ladecentro.domain.model.Store
import com.ladecentro.presentation.common.HorizontalDashDivider
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.ui.cart.details.CartDetailActivity
import com.ladecentro.presentation.ui.stores.details.StoreActivity
import com.ladecentro.presentation.ui.stores.details.compose.getItemTotal

@Composable
fun SampleCart(cartDto: CartDto) {

    val context = LocalContext.current

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, contentColor = Color.Black
        ),
        border = BorderStroke(1.dp, card_border),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        CartsSampleStore(cartDto.store.toStoreDetail()) {
            context.startActivity(
                Intent(context, StoreActivity::class.java)
                    .putExtra(Intents.STORE_ID.name, cartDto.store.id)
            )
        }
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            cartDto.items.forEach {
                CartsSampleItem(it.toProductDetail())
            }
            CartsGrandTotal(getItemTotal(cartDto)) {
                context.startActivity(
                    Intent(context, CartDetailActivity::class.java)
                        .putExtra(Intents.STORE_ID.name, cartDto.store.id)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun CartsSampleStore(store: Store, onClick: () -> Unit) {

    Column(modifier = Modifier.bounceClick {
        onClick()
    }) {
        Row(
            modifier = Modifier.padding(bottom = 10.dp, top = 10.dp, start = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = Color.White,
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
                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = fontFamilyHindBold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = store.shortAddress ?: "",
                    style = MaterialTheme.typography.bodySmall.copy(color = light_gray),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.basicMarquee()
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "delete cart",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, color = card_border)
    }
}

@Composable
fun CartsSampleItem(item: ItemDetails) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color.White,
            border = BorderStroke(1.dp, card_border)
        ) {
            LoadImage(
                image = item.image,
                modifier = Modifier.size(47.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "${item.quantity} x ${item.name}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "₹${item.price}",
                color = light_gray,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = fontFamilyHindBold, color = light_gray
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDashDivider()
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CartsGrandTotal(price: String, onViewCart: () -> Unit) {

    Row(
        modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Total :",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = fontFamilyHindBold,
                fontWeight = Companion.Bold
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "₹$price",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = fontFamilyHindBold,
                fontWeight = Companion.Bold
            ),
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = {
                onViewCart()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(horizontal = 12.dp),
            modifier = Modifier.defaultMinSize(minHeight = 32.dp)
        ) {
            Text(
                text = "View Cart",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontWeight = Companion.Normal
                )
            )
            Icon(
                imageVector = AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun CartItemsList(carts: List<CartDto>) {

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(carts.reversed()) {
            SampleCart(it)
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}