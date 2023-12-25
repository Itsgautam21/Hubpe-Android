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
            TopAppBarSearch(placeHolder = "Search Here", isFocus = true) {
                vm.searchText.value = it.value
                if (vm.searchText.value.length > 2) {
                    vm.searchStoreProduct()
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(padding)
        ) {
            if (vm.searchText.value.length <= 2) {
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
                val searches = vm.pastSearch.value.toMutableList()
                vm.saveSearch?.let {
                    searches.removeIf { text -> text == it }
                    searches.add(it)
                }
                vm.pastSearch.value = searches
                vm.setPastSearchToPreference(searches)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
}