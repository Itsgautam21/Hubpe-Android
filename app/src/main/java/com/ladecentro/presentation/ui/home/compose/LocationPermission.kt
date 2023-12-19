package com.ladecentro.presentation.ui.home.compose

import android.Manifest.permission
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.ladecentro.common.hasLocationPermission
import com.ladecentro.common.isGPSEnable
import com.ladecentro.presentation.common.SimpleAlertDialog
import com.ladecentro.presentation.ui.home.HomeViewModel

@Composable
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
fun LocationPermission(vm: HomeViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val state = vm.location.collectAsState()
    var dialog by remember { mutableStateOf(false) }
    val permissionState = rememberPermissionState(permission = permission.ACCESS_FINE_LOCATION)
    val sheetState = rememberModalBottomSheetState()
    val openBottomSheet = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(contract = StartIntentSenderForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            vm.getUserLocation(null)
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
                    openBottomSheet.value = true
                } else {
                    vm.getUserLocation(launcher)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    if (state.value != null) {
        Log.d(">>>> Location", state.value?.latitude.toString())
    }
    if (openBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                openBottomSheet.value = false
            },
            sheetState = sheetState,

            containerColor = Color.White
        ) {
            LocationBottomSheet(
                onEnableLocation = {
                    if (!context.hasLocationPermission()) {
                        permissionState.launchPermissionRequest()
                    } else {
                        vm.getUserLocation(launcher)
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
        permissionState.status.isGranted -> {
        }

        permissionState.status.shouldShowRationale -> {
        }

        !permissionState.status.isGranted && !permissionState.status.shouldShowRationale -> {
            dialog = true
        }
    }
}