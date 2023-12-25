package com.ladecentro.presentation.ui.address.addresses.compose

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.ladecentro.common.Intents
import com.ladecentro.presentation.common.SimpleTopAppBar
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.ui.address.add.AddAddressActivity
import com.ladecentro.presentation.ui.address.addresses.AddressViewModel
import com.ladecentro.presentation.ui.location.maps.MapsActivity
import com.ladecentro.presentation.ui.location.select.compose.SampleSavedAddress
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent

@Composable
fun AddressesLayout(vm: AddressViewModel = hiltViewModel()) {

    val context = LocalContext.current as Activity
    val locations = vm.userLocation.collectAsState().value
    val activityLauncher = rememberLauncherForActivityResult(StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            vm.getUserProfile()
        }
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "My Addresses")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val gps: List<Double> =
                        vm.location.gps?.split(",")?.map { it.toDouble() } ?: listOf()
                    val cameraPos = CameraPosition.fromLatLngZoom(LatLng(gps[0], gps[1]), 18f)
                    activityLauncher.launch(
                        Intent(
                            context,
                            MapsActivity::class.java
                        ).putExtra(Intents.ADD_ADDRESS.name, Intents.ADD_ADDRESS.name)
                            .putExtra(Intents.CAMERA.name, cameraPos)
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
            locations.content?.let { locations ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(card_background),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    items(locations) { location ->
                        SampleSavedAddress(location) {

                        }
                    }
                }
            }
            if (locations.isLoading) {
                ShimmerContent()
            }
        }
    }
}