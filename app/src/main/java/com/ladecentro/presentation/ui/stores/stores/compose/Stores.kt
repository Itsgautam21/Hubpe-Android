package com.ladecentro.presentation.ui.stores.stores.compose

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.common.Intents
import com.ladecentro.common.bounceClick
import com.ladecentro.data.remote.dto.Store
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.ui.stores.details.StoreActivity

@Composable
fun SampleStores(store: Store, fav: Boolean, onFavIconClick: () -> Unit) {

    val context = LocalContext.current

    Surface(
        color = Color.White,
        shadowElevation = 0.dp,
        modifier = Modifier.bounceClick {
            context.startActivity(
                Intent(context, StoreActivity::class.java)
                    .putExtra(Intents.STORE_ID.name, store.id)
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    border = BorderStroke(1.dp, card_border),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White
                ) {
                    LoadImage(
                        image = store.descriptor.images.getOrNull(0),
                        modifier = Modifier.size(72.dp)
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = store.descriptor.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = fontFamilyHind,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    Text(
                        text = store.locations[0].descriptor.shortDesc,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        fontFamily = fontFamilyHind,
                        color = light_gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(Color.White)
                        ) {
                            Icon(
                                imageVector = Rounded.Star,
                                contentDescription = "rating",
                                modifier = Modifier
                                    .size(14.dp)
                                    .offset(y = (-1).dp),
                                tint = light_gray
                            )
                            Text(
                                text = store.rating ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                fontFamily = fontFamilyHind,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                )
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(Color.White)
                        ) {
                            Icon(
                                imageVector = Rounded.DateRange,
                                contentDescription = "rating",
                                modifier = Modifier
                                    .size(12.dp)
                                    .offset(y = (-1).dp),
                                tint = light_gray
                            )
                            Text(
                                text = store.fulfillments[0].deliveryTime,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                fontFamily = fontFamilyHind,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                )
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(Color.White)
                        ) {
                            Icon(
                                imageVector = Rounded.LocationOn,
                                contentDescription = "rating",
                                modifier = Modifier
                                    .size(14.dp)
                                    .offset(y = (-1).dp),
                                tint = light_gray
                            )
                            Text(
                                text = "4.2 km",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                fontFamily = fontFamilyHind,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                )
                            )
                        }
                    }
                }
                Icon(
                    imageVector = if (fav) Rounded.Favorite else Rounded.FavoriteBorder,
                    contentDescription = "Heart",
                    modifier = Modifier.size(20.dp).clickable { onFavIconClick() },
                    tint = if (fav) Companion.Red else Companion.Gray,
                )
            }
            if (!store.promoProducts.isNullOrEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    store.promoProducts.forEach { image ->
                        Surface(
                            border = BorderStroke(1.dp, card_border),
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        ) {
                            LoadImage(
                                image = image,
                                modifier = Modifier.height(64.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StoresBanner() {

    LazyRow(
        modifier = Modifier.padding(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
    ) {

        items(5) {
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Companion.Black
                )
            ) {
                Image(
                    painter = painterResource(id = drawable.food_banner),
                    contentDescription = "store logo",
                    modifier = Modifier
                        .height(120.dp)
                        .width(220.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}