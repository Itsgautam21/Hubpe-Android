package com.ladecentro.presentation.ui.cart.details.compose

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.common.Intents
import com.ladecentro.common.bounceClick
import com.ladecentro.data.remote.dto.mapToLocationRequest
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.address.addresses.compose.getCameraPosition
import com.ladecentro.presentation.ui.cart.details.CartDetailViewModel
import com.ladecentro.presentation.ui.location.maps.MapsActivity
import com.ladecentro.presentation.ui.location.select.compose.SampleSavedAddress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartAddresses(vm: CartDetailViewModel = hiltViewModel()) {

    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }
    val activityLauncher = rememberLauncherForActivityResult(StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            vm.getAddressFromLocal()
        }
    }

    if (vm.openSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                vm.openSheet = false
            },
            sheetState = sheetState,
            containerColor = card_background,
            contentColor = Color.Black
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                item {
                    CartAddAddressCompose(loading) {
                        loading = true
                        val camera = getCameraPosition(vm.userLocation)
                        loading = false
                        activityLauncher.launch(
                            Intent(context, MapsActivity::class.java)
                                .putExtra(Intents.ADD_ADDRESS.name, Intents.ADD_ADDRESS.name)
                                .putExtra(Intents.CAMERA.name, camera)
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                items(vm.userAddress.reversed()) {
                    SampleSavedAddress(location = it) { loc ->
                        vm.userLocation = loc.mapToLocationRequest()
                        vm.openSheet = false
                    }
                }
            }
        }
    }
}

@Composable
fun CartAddAddressCompose(loading: Boolean, onClick: () -> Unit) {

    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "current location",
                tint = primary_orange
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Add New Address",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamilyHind,
                color = primary_orange,
                modifier = Modifier.weight(1f)
            )
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = primary_orange
                )
            }
        }
    }
}