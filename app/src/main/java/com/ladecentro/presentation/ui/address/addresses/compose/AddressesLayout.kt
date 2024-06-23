package com.ladecentro.presentation.ui.address.addresses.compose

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.ladecentro.common.Intents
import com.ladecentro.data.remote.dto.Location
import com.ladecentro.domain.model.DropdownMenu
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.presentation.common.SimpleDialog
import com.ladecentro.presentation.common.SimpleTopAppBar
import com.ladecentro.presentation.theme.background
import com.ladecentro.presentation.ui.address.addresses.AddressViewModel
import com.ladecentro.presentation.ui.location.maps.MapsActivity
import com.ladecentro.presentation.ui.location.select.compose.SampleSavedAddress
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddressesLayout(vm: AddressViewModel = hiltViewModel()) {

    val context = LocalContext.current as Activity
    var dialogState: Location? by rememberSaveable { mutableStateOf(null) }
    val activityLauncher = rememberLauncherForActivityResult(StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            vm.getUserProfile()
        }
    }
    Scaffold(
        topBar = { SimpleTopAppBar(title = "My Addresses") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    activityLauncher.launch(
                        Intent(
                            context,
                            MapsActivity::class.java
                        ).putExtra(Intents.ADD_ADDRESS.name, Intents.ADD_ADDRESS.name)
                            .putExtra(Intents.CAMERA.name, getCameraPosition(vm.location))
                    )
                },
                shape = RoundedCornerShape(30.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Rounded.Add,
                    contentDescription = "Add Address",
                    tint = Color.White
                )
            }
        }

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            vm.userLocation.content?.let { locations ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(background),
                    contentPadding = PaddingValues(
                        top = 12.dp,
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 100.dp
                    )
                ) {
                    items(locations, key = { key -> key.id }) { location ->
                        val dropdownList = listOf(
                            DropdownMenu("Edit", Rounded.Edit) { loc ->
                                activityLauncher.launch(
                                    Intent(context, MapsActivity::class.java)
                                        .putExtra(
                                            Intents.ADD_ADDRESS.name,
                                            Intents.ADD_ADDRESS.name
                                        )
                                        .putExtra(Intents.UPDATE_ADDRESS.name, loc)
                                        .putExtra(Intents.CAMERA.name, getCameraPosition(loc))
                                )
                            },
                            DropdownMenu("Delete", Rounded.Delete) { loc ->
                                dialogState = loc
                            }
                        )
                        SampleSavedAddress(
                            modifier = Modifier.animateItemPlacement(),
                            location = location, dropDownList = dropdownList) {
                        }
                    }
                }
            }
            if (vm.userLocation.isLoading) {
                ShimmerContent()
            }
        }
        dialogState?.let {
            SimpleDialog(
                dismissRequest = { dialogState = null },
                negativeClick = { dialogState = null },
                body = "Are you sure want to delete this Address?",
                positiveClick = {
                    vm.deleteAddress(it)
                    dialogState = null
                }
            )
        }
    }
}

fun getCameraPosition(loc: Location): CameraPosition {
    val gps: List<Double> = loc.gps.split(",").map { d -> d.toDouble() }
    return CameraPosition.fromLatLngZoom(LatLng(gps[0], gps[1]), 18f)
}

fun getCameraPosition(location: LocationRequest): CameraPosition {
    val gps: List<Double> = location.gps?.split(",")?.map { it.toDouble() } ?: listOf()
    return CameraPosition.fromLatLngZoom(LatLng(gps[1], gps[0]), 18f)
}
