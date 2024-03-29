package com.ladecentro.presentation.ui.location.select.compose

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R
import com.ladecentro.common.Intents
import com.ladecentro.domain.model.PlacesResult
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.ui.location.maps.MapsActivity
import com.ladecentro.presentation.ui.location.select.LocationViewModel

@Composable
fun SamplePlaces(placesResult: PlacesResult, vm: LocationViewModel = hiltViewModel()) {

    val context = LocalContext.current as Activity
    val activityLauncher = rememberLauncherForActivityResult(StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            context.setResult(Activity.RESULT_OK)
            context.finish()
        }
    }

    Card(
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            vm.getCameraPosition(placesResult) { cam ->
                cam?.let { notNullCamPos ->
                    activityLauncher.launch(
                        Intent(
                            context,
                            MapsActivity::class.java
                        ).putExtra(Intents.CAMERA.name, notNullCamPos)
                    )
                }
            }
        }
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Column(modifier = Modifier.padding(0.dp)) {

                Icon(
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = "location",
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))

            }
            Column {
                Text(
                    text = placesResult.primary,
                    fontSize = 15.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = placesResult.secondary,
                    fontSize = 12.sp,
                    fontFamily = fontFamilyHind,
                    fontWeight = FontWeight.SemiBold,
                    color = light_gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )
            }
        }
        HorizontalDivider(color = card_border, thickness = 1.dp)
    }
}

@Composable
fun PlacesUI(vm: LocationViewModel = hiltViewModel()) {

    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(card_background)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            LocationOptionCurrent()
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(vm.locationAutofill) {
            AnimatedVisibility(visible = vm.locationAutofill.isNotEmpty()) {
                SamplePlaces(placesResult = it)
            }
        }
    }
}