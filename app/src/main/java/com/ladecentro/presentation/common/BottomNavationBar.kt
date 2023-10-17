package com.ladecentro.presentation.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.unit.dp
import com.ladecentro.presentation.model.bottomItemList

@Composable
fun BottomNavigationBar() {

    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    Card(elevation = CardDefaults.cardElevation(24.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        NavigationBar(
            containerColor = Color.White, modifier = Modifier
                .height(64.dp)
                .clip(
                    RoundedCornerShape(16.dp)
                )
        ) {
            bottomItemList.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItem == index,
                    onClick = { selectedItem = index },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedItem) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Companion.White
                    )
                )
            }
        }
    }
}