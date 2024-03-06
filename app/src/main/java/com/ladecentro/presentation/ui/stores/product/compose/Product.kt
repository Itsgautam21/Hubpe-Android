package com.ladecentro.presentation.ui.stores.product.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ladecentro.presentation.ui.search.compose.SearchProducts
import com.ladecentro.presentation.ui.stores.product.ProductsViewModel

@Composable
fun Products(vm: ProductsViewModel = hiltViewModel()) {

    val productSearch = vm.productSearch.collectAsLazyPagingItems()

//    LazyColumn {
//        items(count = productSearch.itemCount, key = { it }) { index ->
//
//        }
//
//    }

    SearchProducts(productSearch = productSearch) {

    }
}