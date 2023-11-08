package com.ladecentro.presentation.ui.stores.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyAverSans
import com.ladecentro.presentation.theme.fontFamilyHind

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun StoreComposeUI() {

    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        //modifier = Modifier.background(Color(0xFFF0F3F8))
    ) {

        item {
            StoresBanner()
        }
        item {
            Text(
                text = "Spotlight",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = fontFamilyAverSans,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        items(5) {
            SampleStores()
        }
        item {
            ShopByCategory()
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Box(contentAlignment = Alignment.Center) {
                Divider(
                    thickness = 1.dp,
                    color = card_border,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding()
                ) {
                    Text(
                        text = "Explore More",
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        fontFamily = fontFamilyHind,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }
        }
        stickyHeader {
            FilterSortStores()
        }
        items(10) {
            SampleStores()
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}