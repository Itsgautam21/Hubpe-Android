package com.ladecentro.presentation.ui.location.maps.compose

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.ladecentro.R.drawable
import com.ladecentro.common.LocationResource.Error
import com.ladecentro.common.LocationResource.Loading
import com.ladecentro.common.LocationResource.Success
import com.ladecentro.common.customShimmer
import com.ladecentro.presentation.ui.location.maps.MapsViewModel

@Composable
fun GoogleMaps(cameraPositionState: CameraPositionState, vm: MapsViewModel = hiltViewModel()) {

    val currentLocationState by vm.location.collectAsState()
    val uiSettings = remember {
        MapUiSettings(
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false,
            rotationGesturesEnabled = true
        )
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = false))
    }
    val context = LocalContext.current as Activity
    val locationLauncher = rememberLauncherForActivityResult(StartIntentSenderForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            vm.getUserLocation()
        }
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                currentLocationState.isLoading?.let {
                    if (it) Modifier.customShimmer() else Modifier
                } ?: Modifier
            ),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings
    ) {
        Marker(
            state = MarkerState(cameraPositionState.position.target),
            draggable = true,
            icon = bitmapDescriptor(
                context, drawable.location_pointer
            )
        )
    }
    LaunchedEffect(currentLocationState) {
        when (currentLocationState) {
            is Loading -> {
                Log.d(">>>> Location", "Location Loading")
            }

            is Success -> {
                Log.d(">>>> Location", "Location Success")
            }

            is Error -> {
                locationLauncher.launch(currentLocationState.intent)
            }
        }
    }
}

fun bitmapDescriptor(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, 130, 130)
    val bm = Bitmap.createBitmap(
        130,
        130,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}