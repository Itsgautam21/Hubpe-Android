package com.ladecentro.presentation.ui.location.select.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.ui.location.select.LocationActivity
import com.ladecentro.presentation.ui.location.select.LocationViewModel

@Composable
fun LocationLayout(vm: LocationViewModel = hiltViewModel()) {

    val context = LocalContext.current
    Scaffold(
        topBar = { TopAppBarLocation() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(card_background)
        ) {
            AnimatedVisibility(
                visible = vm.searchState.value.isNotEmpty(),
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(1000))
            ) {
                PlacesUI()
            }
            AnimatedVisibility(
                visible = vm.searchState.value.isEmpty(),
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(1000))
            ) {
                LocationOption()
            }
        }
    }

    val localView = LocalView.current
    SideEffect {
        val activity = context as LocationActivity
        activity.window.statusBarColor = card_background.toArgb()
        WindowCompat.getInsetsController(activity.window, localView)
            .isAppearanceLightStatusBars = true
    }
}

