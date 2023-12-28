package com.ladecentro.presentation.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.home.compose.MainLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            LadecentroTheme {
                MainLayout()
                BackHandler {
                   finish()
                }
            }
        }
    }
}