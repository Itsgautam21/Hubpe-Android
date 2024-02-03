package com.ladecentro.presentation.common

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.ladecentro.presentation.theme.Typography
import com.ladecentro.presentation.theme.doppio_one

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SimpleTopAppBar(title: String) {

    val context = LocalContext.current as Activity

    Surface(shadowElevation = 4.dp) {

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
            title = {
                Text(
                    text = title,
                    style = Typography.titleLarge.copy(
                        fontWeight = FontWeight.Medium,
                        fontFamily = doppio_one
                    ),
                    fontSize = 18.sp
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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarSearch(
    value: String = "",
    placeHolder: String,
    isFocus: Boolean,
    color: Color = Color.White,
    searchAction: () -> Unit = {},
    changeValue: () -> Unit = {},
    textValue: (value: String) -> Unit,
) {

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = color),
        title = {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                SearchMainCompose(value, placeHolder, isFocus, searchAction, changeValue, textValue)
            }
        }
    )
}