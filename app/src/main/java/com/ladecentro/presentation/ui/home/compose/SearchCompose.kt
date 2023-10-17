package com.ladecentro.presentation.ui.home.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.darkBlue
import com.ladecentro.presentation.theme.dark_gray
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.secondary

@Composable
@Preview
fun SearchCompose() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp).padding(horizontal = 16.dp), colors = CardDefaults.cardColors(
            contentColor = Color.Black, containerColor = Color.White
        ), elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            Text(
                text = "What do you want today?",
                fontSize = 15.sp,
                modifier = Modifier.weight(1f),
                color = light_text,
                fontFamily = fontFamilyHind
            )
            Icon(
                painter = painterResource(id = drawable.search),
                contentDescription = "search",
                tint = darkBlue
            )
        }
    }
}

@Preview
@Composable
fun YourFavourite() {

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Your Favourite",
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                color = darkBlue,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "View All",
                fontSize = 12.sp,
                color = secondary,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .weight(.1f)
                    .clip(RoundedCornerShape(16.dp))
            ) {

                Image(
                    painter = painterResource(id = drawable.category5),
                    contentDescription = "",
                    modifier = Modifier.height(90.dp)

                )
            }
            Box(
                modifier = Modifier
                    .weight(.1f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = drawable.category2),
                    contentDescription = "",
                    modifier = Modifier
                        .height(90.dp)
                        .width(90.dp)

                )
            }
            Box(
                modifier = Modifier
                    .weight(.1f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = drawable.category3),
                    contentDescription = "",
                    modifier = Modifier.height(90.dp)

                )
            }

            Box(
                modifier = Modifier
                    .weight(.1f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = drawable.category4),
                    contentDescription = "",
                    modifier = Modifier.height(90.dp)

                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .height(50.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(containerColor = card_background),
            border = BorderStroke(1.dp, card_border)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                Text(
                    text = "Recently Viewed",
                    color = darkBlue,
                    fontSize = 16.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Icon(imageVector = Outlined.KeyboardArrowRight, contentDescription = "")

            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(0.dp),
            border = BorderStroke(width = 1.dp, color = card_border)
        ) {
            Image(
                painter = painterResource(id = drawable.banner),
                contentScale = ContentScale.Crop,
                contentDescription = "banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
            )

        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "In the spotlight",
            fontSize = 18.sp,
            color = darkBlue,
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamilyHind
        )
        Spacer(modifier = Modifier.height(14.dp))
    }
}

@Preview
@Composable
fun Spotlight() {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(3.dp),
                shape = RoundedCornerShape(15.dp),
            ) {

                Card(
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = drawable.spotlight1),
                        contentDescription = "spotlight",
                        modifier = Modifier
                            .height(190.dp)
                            .width(240.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Juice Center",
                        fontSize = 15.sp,
                        color = darkBlue,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamilyHind
                    )
                    Text(
                        text = "Zero Sugar - Guilt Free Juice",
                        fontSize = 13.sp,
                        color = light_gray,
                        fontWeight = FontWeight.Normal,
                        fontFamily = fontFamilyHind,
                    )
                }
            }
        }
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(3.dp),
                shape = RoundedCornerShape(15.dp),
            ) {

                Card(
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = drawable.spotlight2),
                        contentDescription = "spotlight",
                        modifier = Modifier
                            .height(190.dp)
                            .width(240.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Natural Ice Cream",
                        fontSize = 15.sp,
                        color = darkBlue,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamilyHind
                    )
                    Text(
                        text = "Zero Sugar - Guilt Free Ice Cream",
                        fontSize = 13.sp,
                        color = light_gray,
                        fontWeight = FontWeight.Normal,
                        fontFamily = fontFamilyHind,
                    )
                }
            }
        }
    }
}

@Composable
fun ShopCategory() {

    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "WHAT'S ON YOUR MIND?",
            fontSize = 17.sp,
            color = dark_gray,
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamilyHind,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyHorizontalGrid(
            rows = Fixed(2),
            modifier = Modifier.height(200.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            items(list) {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "category",
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

val list = listOf(
    drawable.category1,
    drawable.category2,
    drawable.category3,
    drawable.category4,
    drawable.category4,
    drawable.category5,
    drawable.category6,
    drawable.category7
)