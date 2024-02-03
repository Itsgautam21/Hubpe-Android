package com.ladecentro.presentation.ui.cart.details.compose

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.presentation.common.SimpleTopAppBar
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_orange
import com.ladecentro.presentation.theme.primary_orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar() {

    val context = LocalContext.current as Activity

    Surface(shadowElevation = 4.dp) {

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
            title = {
                SimpleTopAppBar(title = "View Cart")
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        context.finish()
                    },
                    modifier = Modifier.padding(start = 0.dp)
                ) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = {
                Spacer(modifier = Modifier.width(24.dp))
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Outlined.Delete,
                        contentDescription = "delete cart",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        )
    }
}

@Composable
fun CartLoading() {

    Surface(color = light_orange, modifier = Modifier.fillMaxWidth(), shadowElevation = 4.dp) {
        Column {
            Text(
                text = "Verifying your cart with the store",
                fontFamily = fontFamilyHind,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )
            LinearProgressIndicator(
                color = primary_orange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            )
        }
    }
}