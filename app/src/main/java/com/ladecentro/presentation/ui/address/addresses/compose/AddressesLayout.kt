package com.ladecentro.presentation.ui.address.addresses.compose

import android.content.Intent
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
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.ui.address.add.AddAddressActivity
import com.ladecentro.presentation.ui.location.select.compose.SampleSavedAddress
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent
import com.ladecentro.presentation.ui.order.orders.compose.TopAppBarMyOrders

@Composable
fun AddressesLayout(vm: AddressViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val locations = vm.userLocation.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBarMyOrders(title = "My Addresses")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    context.startActivity(
                        Intent(
                            context,
                            AddAddressActivity::class.java
                        )
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
                        SampleSavedAddress(location)
                    }
                }
            }
            if (locations.isLoading) {
                ShimmerContent()
            }
        }
    }
}