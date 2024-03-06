package com.ladecentro.presentation.ui.stores.product.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadState.Loading
import androidx.paging.LoadState.NotLoading
import androidx.paging.compose.collectAsLazyPagingItems
import com.ladecentro.presentation.theme.background
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent
import com.ladecentro.presentation.ui.search.compose.SearchProducts
import com.ladecentro.presentation.ui.stores.details.compose.SearchStoreCompose
import com.ladecentro.presentation.ui.stores.product.ProductsViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ProductsLayout(vm: ProductsViewModel = hiltViewModel()) {

    val productSearch = vm.productSearch.collectAsLazyPagingItems()

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(background)
        ) {
            LazyColumn {
                item {
                    TopBarProducts(category = vm.category ?: "", storeName = vm.storeId ?: "")
                }
                stickyHeader {
                    SearchStoreCompose("ONDC K Grocery")
                }
                when (productSearch.loadState.refresh) {
                    is Loading -> {
                        item {
                            ShimmerContent()
                        }
                    }
                    is NotLoading -> {
                        items(count = productSearch.itemCount, key ={ d -> d}) { index ->
                            productSearch[index]?.let { product ->

                            }
                        }
                    }
                    is LoadState.Error -> {}
                }

            }

        }
    }
}