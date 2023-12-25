package com.ladecentro.presentation.ui.location.select.compose

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.ladecentro.common.Intents.CAMERA
import com.ladecentro.common.LocationResource.Error
import com.ladecentro.common.LocationResource.Loading
import com.ladecentro.common.LocationResource.Success
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.ui.location.maps.MapsActivity
import com.ladecentro.presentation.ui.location.select.LocationActivity
import com.ladecentro.presentation.ui.location.select.LocationViewModel
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent

@Composable
fun LocationLayout(vm: LocationViewModel = hiltViewModel()) {

    val context = LocalContext.current as Activity
    val state by vm.location.collectAsState()
    val launcher = rememberLauncherForActivityResult(StartIntentSenderForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            vm.getUserLocation()
        }
    }
    val activityLauncher = rememberLauncherForActivityResult(StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            context.setResult(Activity.RESULT_OK)
            context.finish()
        }
    }
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

    state.isLoading?.let {
        if (it) {
            ShimmerContent()
        }
    }

    LaunchedEffect(key1 = state) {
        when (state) {
            is Loading -> {
                Log.d(">>>> Location", "Location Loading")
            }

            is Success -> {
                val cameraPos = CameraPosition.fromLatLngZoom(
                    LatLng(
                        state.location!!.latitude,
                        state.location!!.longitude
                    ), 18f
                )
                activityLauncher.launch(
                    Intent(context, MapsActivity::class.java).putExtra(
                        CAMERA.name,
                        cameraPos
                    )
                )
            }

            is Error -> {
                launcher.launch(state.intent)
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

