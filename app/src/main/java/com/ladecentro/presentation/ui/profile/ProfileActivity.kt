package com.ladecentro.presentation.ui.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.tooling.preview.Preview
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.ui.orders.compose.TopAppBarMyOrders
import com.ladecentro.presentation.ui.profile.compose.MainProfileUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class ProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LadecentroTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBarMyOrders("Profile Settings")
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(card_border)
                                .padding(it)
                                .verticalScroll(rememberScrollState())
                        ) {
                            MainProfileUI()
                        }
                    }
                }
            }
        }
    }
}