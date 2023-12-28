package com.ladecentro.presentation.ui.search.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.LifecycleEventObserver
import com.ladecentro.presentation.ui.search.SearchViewModel

@Composable
fun SearchLayout(vm: SearchViewModel = hiltViewModel()) {

    Scaffold(
        topBar = {
            TopAppBarSearch(
                value = vm.searchText,
                placeHolder = "Search Here",
                isFocus = vm.isFocusState,
                changeValue = { vm.searchText = "" },
                textValue = { vm.searchText = it }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(padding)
        ) {
            if (vm.searchText.length <= 2) {
                PastSearch()
            } else {
                SearchTabCompose()
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == ON_DESTROY) {
                vm.setPastSearchToPreference()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
}