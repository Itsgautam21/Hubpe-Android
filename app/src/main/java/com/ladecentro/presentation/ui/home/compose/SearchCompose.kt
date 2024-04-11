package com.ladecentro.presentation.ui.home.compose

import android.content.Intent
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.common.Constants.MY_FAVOURITES
import com.ladecentro.common.Constants.RECENTLY_VIEWED
import com.ladecentro.common.Intents
import com.ladecentro.common.bounceClick
import com.ladecentro.domain.model.FavouriteStore
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.darkBlue
import com.ladecentro.presentation.theme.dark_gray
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.secondary
import com.ladecentro.presentation.ui.favourite.FavouriteActivity
import com.ladecentro.presentation.ui.stores.details.StoreActivity
import com.ladecentro.presentation.ui.stores.stores.StoresActivity

@Composable
fun YourFavourite(favourites: List<FavouriteStore>, histories: List<FavouriteStore>) {

    val context = LocalContext.current
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(0.dp))
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
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.bounceClick {
                    context.startActivity(
                        Intent(context, FavouriteActivity::class.java).putExtra(
                            Intents.TYPE_FAV.name, MY_FAVOURITES
                        )
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            for (index in 0..3) {
                Surface(
                    color = Color.White,
                    border = BorderStroke(1.dp, card_border),
                    shape = MaterialTheme.shapes.medium
                ) {
                    favourites.getOrNull(index)?.let { fav ->
                        LoadImage(
                            image = fav.image,
                            modifier = Modifier
                                .weight(1f)
                                .size(72.dp)
                                .bounceClick {
                                    context.startActivity(
                                        Intent(context, StoreActivity::class.java)
                                            .putExtra(Intents.STORE_ID.name, fav.id)
                                    )
                                }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .height(50.dp)
                .bounceClick {
                    context.startActivity(
                        Intent(
                            context, FavouriteActivity::class.java
                        ).putExtra(Intents.TYPE_FAV.name, RECENTLY_VIEWED)
                    )
                },
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(containerColor = card_background),
            border = BorderStroke(1.dp, card_border)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                val size = 30.dp
                Box(modifier = Modifier) {
                    for (index in 0..3) {
                        val history = histories.getOrNull(index)
                        history?.let { his ->
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                color = Color.White,
                                shadowElevation = 8.dp,
                                tonalElevation = 8.dp,
                                modifier = Modifier.padding(start = ((size / 2) * index + 8.dp))
                            ) {
                                LoadImage(
                                    image = his.image,
                                    contentDescription = "history store image",
                                    contentScale = ContentScale.Inside,
                                    modifier = Modifier.height(30.dp).width(size),
                                )
                            }
                        }
                    }
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = ""
                )
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

    val context = LocalContext.current
    Column {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "WHAT'S ON YOUR MIND?",
            fontSize = 17.sp,
            color = dark_gray,
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamilyHind,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyHorizontalGrid(
            rows = Fixed(2),
            modifier = Modifier.height(290.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(list) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = it.image),
                        contentDescription = "category",
                        modifier = Modifier
                            .height(90.dp)
                            .width(90.dp)
                            .bounceClick {
                                context.startActivity(
                                    Intent(context, StoresActivity::class.java)
                                        .putExtra(Intents.CATEGORY_NAME.name, it.value)
                                )
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it.name,
                        fontSize = 14.sp,
                        fontFamily = fontFamilyHind,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

val list = listOf(
    Category(drawable.category4, "turbo", ""),
    Category(drawable.category7, "Fashion", "Fashion"),
    Category(drawable.category5, "Food", "Food and Beverage"),
    Category(drawable.category2, "Electronics", "Electronics"),
    Category(drawable.category6, "Grocery", "Grocery"),
    Category(drawable.category3, "Home & Decor", "Home & Decor"),
    Category(drawable.category1, "Beauty", "Beauty"),
    Category(drawable.category4, "Pharma", "Pharma"),
)

data class Category(val image: Int, val name: String, val value: String)