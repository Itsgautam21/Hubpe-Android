package com.ladecentro.presentation.ui.location.maps.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.rememberCameraPositionState
import com.ladecentro.presentation.ui.location.maps.MapsViewModel

@Composable
fun MapsLayout(vm: MapsViewModel = hiltViewModel()) {

    val cameraState by vm.cameraPosition.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        position = cameraState
    }
    LaunchedEffect(cameraState) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newCameraPosition(cameraState), durationMs = 1000
        )
    }
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            vm.getMarkerAddressDetails(cameraPositionState.position.target)
        }
    }
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            GoogleMaps(cameraPositionState)
            MapsSearch()
        }
        MapsAddress(cameraPositionState)
    }
}