package com.ladecentro.presentation.ui.cart.details.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.presentation.common.SimpleTopAppBar
import com.ladecentro.presentation.theme.background
import com.ladecentro.presentation.ui.cart.details.CartDetailViewModel

@Composable
fun CartLayout(vm: CartDetailViewModel = hiltViewModel()) {

    Scaffold(
        topBar = { SimpleTopAppBar(title = "View Cart") }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = background
        ) {
            Column {
                if (vm.userCart.isLoading) {
                    CartLoading()
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    item {
                        CartDetails()
                    }
                    item {
                        CartInfo()
                    }
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
                CartFooter()
                CartAddresses()
                CartDeliveryOptions()
            }
        }
    }
}