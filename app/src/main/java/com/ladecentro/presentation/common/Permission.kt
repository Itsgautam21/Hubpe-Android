package com.ladecentro.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun LocationPermissions(permissionState: PermissionState, onPermissionGranted: () -> Unit) {

    var dialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = permissionState) {
        when {
            permissionState.status.isGranted -> {
                onPermissionGranted()
            }

            permissionState.status.shouldShowRationale -> {
                permissionState.launchPermissionRequest()
            }

            !permissionState.status.isGranted && !permissionState.status.shouldShowRationale -> {
                //dialog = true
            }
        }
    }

    if (dialog) {
        SimpleAlertDialog(
            onDismissRequest = { dialog = false },
            onConfirmation = { dialog = false },
            dialogTitle = "Warning!",
            dialogText = "Permanent location disable",
            icon = null
        )
    }
}