package com.ladecentro.presentation.ui.home.compose

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R.drawable
import com.ladecentro.presentation.theme.fontFamilyFredoka
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.home.HomeViewModel
import com.ladecentro.presentation.ui.location.select.LocationActivity

@Composable
fun LocationBottomSheet(vm: HomeViewModel = hiltViewModel(), onEnableLocation: () -> Unit) {

    val context = LocalContext.current
    val result = rememberLauncherForActivityResult(StartActivityForResult()) {
        if (it.resultCode == ComponentActivity.RESULT_OK) {
            vm.getLocationFromLocal()
            vm.openBottomSheet = false
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = drawable.enable_location),
            contentDescription = "location off logo",
            modifier = Modifier
                .height(100.dp)
                .width(250.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Location Permission is Off",
            fontSize = 18.sp,
            fontFamily = fontFamilyFredoka,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "We need your location to find the nearest store & provide you a seamless delivery experience",
            fontSize = 12.sp,
            fontFamily = fontFamilyHind,
            fontWeight = FontWeight.Normal,
            color = light_gray,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onEnableLocation.invoke() },
            colors = ButtonDefaults.buttonColors(
                containerColor = primary_orange
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Enable Location",
                fontSize = 15.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Enter Location Manually",
            fontSize = 15.sp,
            fontFamily = fontFamilyHind,
            fontWeight = FontWeight.Medium,
            color = primary_orange,
            modifier = Modifier.clickable {
                result.launch(Intent(context, LocationActivity::class.java))
            }
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}