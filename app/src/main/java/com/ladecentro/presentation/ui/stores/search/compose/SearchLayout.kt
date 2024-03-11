package com.ladecentro.presentation.ui.stores.search.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.presentation.common.TopAppBarSearch
import com.ladecentro.presentation.ui.stores.search.ProductSearchViewModel

@Composable
fun SearchStoreProduct(vm: ProductSearchViewModel = hiltViewModel()) {

    Scaffold(topBar = {
        TopAppBarSearch(
            value = vm.searchText,
            placeHolder = "Search Here",
            isFocus = true,
            changeValue = { vm.searchText = "" },
            textValue = { vm.searchText = it }
        )
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(Color.White)
                .fillMaxSize()
        ) {
            SearchStoreProducts()
        }
    }
}