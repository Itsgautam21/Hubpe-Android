package com.ladecentro.presentation.ui.home.compose

import android.Manifest.permission
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.ladecentro.common.LocationResource
import com.ladecentro.common.hasLocationPermission
import com.ladecentro.common.isGPSEnable
import com.ladecentro.presentation.common.SimpleAlertDialog
import com.ladecentro.presentation.ui.home.HomeViewModel

@Composable
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
fun LocationPermission(vm: HomeViewModel = hiltViewModel()) {

    val context = LocalContext.current as Activity
    val state by vm.location.collectAsState()
    var dialog by remember { mutableStateOf(false) }
    val permissionState = rememberPermissionState(permission = permission.ACCESS_FINE_LOCATION)
    val sheetState = rememberModalBottomSheetState()
    val launcher = rememberLauncherForActivityResult(contract = StartIntentSenderForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            vm.getUserLocation()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == ON_START) {
                permissionState.launchPermissionRequest()
            }
            if (event == ON_CREATE) {
                if (!context.isGPSEnable()) {
                    vm.openBottomSheet = true
                } else {
                    vm.getUserLocation()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    LaunchedEffect(key1 = state) {
        when (state) {
            is LocationResource.Loading -> {
                Log.d(">>>> Location", "Location Loading")
            }

            is LocationResource.Success -> {
                Toast.makeText(context, state.location?.latitude.toString(), Toast.LENGTH_LONG)
                    .show()
                Log.d(">>>> Location", state.location?.latitude.toString())
            }

            is LocationResource.Error -> {
                launcher.launch(state.intent)
            }
        }
    }

    if (vm.openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                vm.openBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            LocationBottomSheet(
                onEnableLocation = {
                    if (!context.hasLocationPermission()) {
                        permissionState.launchPermissionRequest()
                    } else {
                        vm.getUserLocation()
                    }
                }
            )
        }
    }
    if (dialog) {
        SimpleAlertDialog(
            onDismissRequest = { dialog = false },
            onConfirmation = { /*TODO*/ },
            dialogTitle = "Warning!",
            dialogText = "Permanent location disable",
            icon = null
        )
    }

    when {
        permissionState.status.isGranted -> {}

        permissionState.status.shouldShowRationale -> {}

        !permissionState.status.isGranted && !permissionState.status.shouldShowRationale -> {
            dialog = true
        }
    }
}