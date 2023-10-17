package com.ladecentro.presentation.model

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigation(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

val bottomItemList = listOf(
    BottomNavigation(
        title = "Home",
        selectedIcon = Filled.Home,
        unselectedIcon = Outlined.Home,
        hasNews = false,
    ),
    BottomNavigation(
        title = "Chats",
        selectedIcon = Filled.List,
        unselectedIcon = Outlined.List,
        hasNews = false,
    ), BottomNavigation(
        title = "Settings",
        selectedIcon = Filled.Settings,
        unselectedIcon = Outlined.Settings,
        hasNews = false,
    ),
    BottomNavigation(
        title = "Settings",
        selectedIcon = Filled.Share,
        unselectedIcon = Outlined.Share,
        hasNews = false,
    )
)

