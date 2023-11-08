package com.ladecentro.presentation.ui.authentication.login.compose

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarLogin() {

    val context = LocalContext.current as Activity
    Surface(shadowElevation = 0.dp) {

        Column {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
                title = {
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            context.finish()
                        }
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
}