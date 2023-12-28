package com.ladecentro.presentation.ui.search.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent
import com.ladecentro.presentation.ui.search.SearchViewModel

@Composable
fun SearchTabCompose(vm: SearchViewModel = hiltViewModel()) {

    var tabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Products", "Stores")
    val searchState by vm.searchState.collectAsState()

    PrimaryTabRow(
        selectedTabIndex = tabIndex,
        modifier = Modifier.background(Color.Gray),
        contentColor = Color.Black,
        containerColor = Color.White,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(it[tabIndex]),
                width = 130.dp,
                color = primary_orange,
                shape = RoundedCornerShape(
                    topStart = 3.dp,
                    topEnd = 3.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp,
                ),
            )
        },
        divider = {
            HorizontalDivider(thickness = 1.dp, color = card_border)
        }) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = tabIndex == index,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = light_text,
                onClick = { tabIndex = index },
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
    if (searchState.isLoading) {
        ShimmerContent()
    }
    AnimatedVisibility(visible = tabIndex == 0) {
        searchState.content?.let {
            SearchProducts(it.products)
        }
    }
    AnimatedVisibility(visible = tabIndex == 1) {
        searchState.content?.let {
            SearchStores(it.stores)
        }
    }
}