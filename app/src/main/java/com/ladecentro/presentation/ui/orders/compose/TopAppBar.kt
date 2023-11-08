package com.ladecentro.presentation.ui.orders.compose

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.presentation.theme.doppio_one

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarMyOrders(title: String) {

    val context = LocalContext.current as Activity

    Surface(shadowElevation = 4.dp) {

        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
            title = {
                Text(
                    text = title,
                    fontFamily = doppio_one, fontSize = 18.sp
                )
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
            }
        )
    }
}