package com.ladecentro.presentation.ui.cart.compose

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.presentation.theme.doppio_one
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.primary_orange


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarCart() {

    val context = LocalContext.current as Activity

    Surface(shadowElevation = 4.dp) {

        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
            title = {
                Text(
                    text = "All Carts",
                    fontFamily = doppio_one, fontSize = 18.sp
                )
            },
            actions = {
                Text(
                    text = "Empty",
                    fontFamily = fontFamilyHind,
                    fontSize = 16.sp,
                    color = primary_orange,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable {

                        }
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        context.finish()
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    }
}