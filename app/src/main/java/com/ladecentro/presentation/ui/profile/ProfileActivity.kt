package com.ladecentro.presentation.ui.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ladecentro.presentation.common.SimpleTopAppBar
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.ui.profile.compose.MainProfileUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : ComponentActivity() {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LadecentroTheme {

                val snackBarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Scaffold(
                        topBar = { SimpleTopAppBar("Profile Settings") },
                        snackbarHost = { SnackbarHost(snackBarHostState) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(card_border)
                                .padding(it)
                                .verticalScroll(rememberScrollState())
                        ) {
                            MainProfileUI()
                            viewModel.state.value.error?.let {
                                LaunchedEffect(snackBarHostState) {
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(it, "Ok")
                                    }
                                }
                            }
                            viewModel.state.value.content?.let {
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }
}