package com.ladecentro.presentation.ui.stores.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.presentation.theme.fontFamilyFredoka
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray

@Composable
fun SampleStores() {

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Companion.Black
        ),
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Companion.Black
                    )
                ) {
                    Image(
                        painter = painterResource(id = drawable.store_logo),
                        contentDescription = "store logo",
                        modifier = Modifier
                            .height(72.dp)
                            .width(72.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Domino's Pizza",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = fontFamilyFredoka,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = "Ruby, Kolkata",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontFamily = fontFamilyHind,
                        color = light_gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(Companion.White)
                        ) {
                            Icon(
                                imageVector = Rounded.Star,
                                contentDescription = "rating",
                                modifier = Modifier
                                    .height(14.dp)
                                    .width(14.dp),
                                tint = light_gray
                            )
                            Text(
                                text = "3.7",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                fontFamily = fontFamilyHind,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 2.dp),
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(Companion.White)
                        ) {
                            Icon(
                                imageVector = Rounded.DateRange,
                                contentDescription = "rating",
                                modifier = Modifier
                                    .height(12.dp)
                                    .width(12.dp),
                                tint = light_gray
                            )
                            Text(
                                text = "20 min",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                fontFamily = fontFamilyHind,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(Companion.White)
                        ) {
                            Icon(
                                imageVector = Rounded.LocationOn,
                                contentDescription = "rating",
                                modifier = Modifier
                                    .height(14.dp)
                                    .width(14.dp),
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
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
                Icon(
                    painter = painterResource(id = drawable.icons8_heart),
                    contentDescription = "forward arrow",
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 1..4) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.cardElevation(0.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                            contentColor = Companion.Black
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painterResource(id = drawable.category5),
                            contentDescription = "store logo",
                            modifier = Modifier
                                .height(72.dp),
                            contentScale = ContentScale.Crop
                        )
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
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
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