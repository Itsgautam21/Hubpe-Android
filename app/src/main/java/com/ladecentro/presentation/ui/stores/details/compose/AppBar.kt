package com.ladecentro.presentation.ui.stores.details.compose

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.data.remote.dto.Store
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.common.SearchCompose
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.fontFamilyHindBold
import com.ladecentro.presentation.theme.light_orange
import com.ladecentro.presentation.theme.light_text

@Composable
fun StoreTopCompose(store: Store) {

    Surface(color = light_orange) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TopBarStore(
                image = store.descriptor.images.getOrNull(0),
                storeName = store.descriptor.name
            )
            Surface(
                color = Companion.White,
                shadowElevation = 0.dp,
                shape = MaterialTheme.shapes.large,
                border = BorderStroke(1.dp, card_border)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Max)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    SampleStoreInfo(imageId = drawable.star, text = "4.3", value = "50+ rating")
                    VerticalDivider(
                        color = card_border,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    SampleStoreInfo(
                        imageId = drawable.timeline,
                        text = "35 min",
                        value = "Delivery time"
                    )
                    VerticalDivider(
                        color = card_border,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    SampleStoreInfo(imageId = drawable.pin_distance, text = "2.0km", value = "Away")
                }
            }
        }
    }
}

@Composable
fun TopBarStore(image: String?, storeName: String) {

    val context = LocalContext.current as Activity

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = { context.finish() },
            colors = IconButtonDefaults.iconButtonColors(containerColor = Companion.White)
        ) {
            Image(
                imageVector = Rounded.Close,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                color = Companion.White,
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, card_border)
            ) {
                LoadImage(
                    image = image,
                    contentDescription = "store image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .border(BorderStroke(1.dp, card_border), MaterialTheme.shapes.small)
                        .size(50.dp)

                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = storeName,
                fontWeight = FontWeight.W800,
                fontSize = 16.sp,
                fontFamily = fontFamilyHindBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(
            onClick = { },
            colors = IconButtonDefaults.iconButtonColors(containerColor = Companion.White)
        ) {
            Image(
                painter = painterResource(id = drawable.icons8_heart),
                contentDescription = "Heart",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SampleStoreInfo(imageId: Int, text: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Image(
            painterResource(id = imageId),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            fontFamily = fontFamilyHind,
            color = MaterialTheme.colorScheme.primary,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            fontFamily = fontFamilyHind,
            color = light_text,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
    }
}

@Composable
fun SearchStoreCompose(storeName: String) {

    Box(
        modifier = Modifier
            .background(light_orange)
            .padding(start = 16.dp, end = 2.dp, top = 8.dp, bottom = 8.dp)
    ) {
        SearchCompose(
            title = "Search In $storeName",
            elevation = 0.dp,
            height = 48.dp,
            borderStroke = BorderStroke(1.dp, card_border)
        ) {

        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun BannerPager(store: Store) {

    store.homeImage?.let { images ->
        val pagerState = rememberPagerState(pageCount = { images.size })
        Column {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(12.dp),
                pageSpacing = 16.dp
            ) {
                Surface(shape = MaterialTheme.shapes.medium) {
                    LoadImage(
                        image = images[it],
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(6.dp)
                    )
                }
            }
        }
    }
}