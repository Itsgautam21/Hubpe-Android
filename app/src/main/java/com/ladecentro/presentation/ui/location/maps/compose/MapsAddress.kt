package com.ladecentro.presentation.ui.location.maps.compose

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.maps.android.compose.CameraPositionState
import com.ladecentro.common.Intents.ADDRESS
import com.ladecentro.common.customShimmer
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.address.add.AddAddressActivity
import com.ladecentro.presentation.ui.location.maps.MapsViewModel

@Composable
fun MapsAddress(cameraPositionState: CameraPositionState, vm: MapsViewModel = hiltViewModel()) {

    val context = LocalContext.current as Activity
    val address by vm.markerAddressDetail.collectAsState()
    val activityLauncher = rememberLauncherForActivityResult(StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            context.setResult(Activity.RESULT_OK)
            context.finish()
        }
    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(16.dp)
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
            AnimatedContent(
                targetState = address.content?.featureName,
                transitionSpec = {
                    scaleIn(animationSpec = tween(durationMillis = 200)) togetherWith ExitTransition.None
                },
                contentAlignment = Alignment.Center,
                label = "short address"
            ) {
                Text(
                    text = it ?: "",
                    fontSize = 18.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedContent(
                targetState = address.content?.getAddressLine(0),
                transitionSpec = {
                    scaleIn(animationSpec = tween(durationMillis = 200)) togetherWith ExitTransition.None
                },
                contentAlignment = Alignment.Center,
                label = "long address"
            ) {
                Text(
                    text = it ?: "",
                    fontSize = 13.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    vm.isAddAddress?.let {
                        address.content?.let {
                            activityLauncher.launch(
                                Intent(context, AddAddressActivity::class.java)
                                    .putExtra(ADDRESS.name, it)
                            )
                        }
                        return@Button
                    }
                    vm.setLocationToLocal()
                    context.setResult(ComponentActivity.RESULT_OK)
                    context.finish()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary_orange
                ),
                enabled = !address.isLoading && !cameraPositionState.isMoving,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .then(
                        if (cameraPositionState.isMoving && !address.isLoading)
                            Modifier.customShimmer() else Modifier
                    )
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