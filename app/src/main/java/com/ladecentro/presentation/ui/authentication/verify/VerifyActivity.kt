package com.ladecentro.presentation.ui.authentication.verify

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.authentication.login.compose.TopAppBarLogin
import com.ladecentro.presentation.ui.authentication.verify.compose.VerifyCompose
import com.ladecentro.presentation.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class VerifyActivity : ComponentActivity() {

    private val viewModel by viewModels<VerifyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            LadecentroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val snackBarHostState = remember { SnackbarHostState() }
                    val coroutineScope = rememberCoroutineScope()

                    Scaffold(topBar = { TopAppBarLogin() },
                        snackbarHost = { SnackbarHost(snackBarHostState) }) {
                        Column(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize()
                                .background(Color.White)
                        ) {
                            VerifyCompose(viewModel)

                            viewModel.state.value.error?.let {
                                LaunchedEffect(snackBarHostState) {
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(it, "Ok")
                                    }
                                }
                            }
                            viewModel.state.value.content?.let {
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
    }
}