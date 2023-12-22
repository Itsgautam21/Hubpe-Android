package com.ladecentro.presentation.ui.profile.compose

import android.app.Activity
import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.presentation.common.SimpleTopAppBar
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.ui.profile.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileLayout(vm: ProfileViewModel = hiltViewModel()) {

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val updateState by vm.state
    val context = LocalContext.current as Activity

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
                updateState.error?.let {
                    LaunchedEffect(snackBarHostState) {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(it, "Ok")
                        }
                    }
                }
                updateState.content?.let {
                    context.setResult(ComponentActivity.RESULT_OK)
                    context.finish()
                }
            }
        }
    }
}