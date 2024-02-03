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
import com.ladecentro.presentation.common.SimpleTopAppBar
import com.ladecentro.presentation.theme.card_background

@Composable
fun CartLayout() {

    Scaffold(
        topBar = { SimpleTopAppBar(title = "View Cart") }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = card_background
        ) {
            Column {
                //CartLoading()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    item {
                        CartDetails()
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