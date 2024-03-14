package com.ladecentro.presentation.ui.authentication.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.authentication.login.compose.Login
import com.ladecentro.presentation.ui.authentication.login.compose.TopAppBarLogin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LadecentroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val snackBarHostState = remember { SnackbarHostState() }
                    Scaffold(
                        topBar = { TopAppBarLogin() },
                        snackbarHost = { SnackbarHost(snackBarHostState) }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize()
                                .background(Color.White)
                            //.verticalScroll(rememberScrollState())
                        ) {
                            Login(viewModel)
                        }
                    }
                }
            }
        }
    }
}