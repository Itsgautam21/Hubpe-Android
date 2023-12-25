package com.ladecentro.presentation.ui.location.maps.compose

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ladecentro.R
import com.ladecentro.common.Intents
import com.ladecentro.presentation.common.SimpleTopAppBar
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.address.add.AddAddressActivity
import com.ladecentro.presentation.ui.location.maps.MapsViewModel

@Composable
fun MapsLayout(vm: MapsViewModel = hiltViewModel()) {

    val cameraPositionState = rememberCameraPositionState {
        position = vm.cameraPosition.value
    }
    val uiSettings = remember {
        MapUiSettings(myLocationButtonEnabled = false, zoomControlsEnabled = false)
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = false))
    }
    val address by vm.markerAddressDetail.collectAsState()
    val context = LocalContext.current as Activity

    val activityLauncher = rememberLauncherForActivityResult(StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            context.setResult(Activity.RESULT_OK)
            context.finish()
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            vm.getMarkerAddressDetails(cameraPositionState.position.target)
        }
    }

    Scaffold(
        topBar = { SimpleTopAppBar(title = "Choose Location") }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(it)
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomEnd) {
                GoogleMap(
                    modifier = Modifier.fillMaxWidth(),
                    cameraPositionState = cameraPositionState,
                    properties = properties,
                    uiSettings = uiSettings
                ) {
                    Marker(
                        state = MarkerState(cameraPositionState.position.target),
                        draggable = true
                    )
                }
                FloatingActionButton(
                    onClick = { },
                    modifier = Modifier.padding(16.dp),
                    containerColor = Color.White,
                    contentColor = Companion.Black,
                    shape = RoundedCornerShape(30.dp),
                    elevation = FloatingActionButtonDefaults.elevation(3.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.current_location),
                        contentDescription = "current location",
                        tint = primary_orange
                    )
                }
            }
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "SELECT DELIVERY LOCATION",
                        fontSize = 13.sp,
                        fontFamily = fontFamilyHind,
                        fontWeight = FontWeight.Normal,
                        color = light_gray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = address.featureName ?: "",
                        fontSize = 18.sp,
                        fontFamily = fontFamilyHind,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = address.getAddressLine(0) ?: "",
                        fontSize = 13.sp,
                        fontFamily = fontFamilyHind,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 20.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            vm.isAddAddress?.let {
                                activityLauncher.launch(
                                    Intent(context, AddAddressActivity::class.java)
                                        .putExtra(Intents.ADDRESS.name, address)
                                )
                                return@Button
                            }
                            vm.setLocationToLocal()
                            context.setResult(ComponentActivity.RESULT_OK)
                            context.finish()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary_orange
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Confirm Location",
                            fontSize = 15.sp,
                            fontFamily = fontFamilyHind,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}