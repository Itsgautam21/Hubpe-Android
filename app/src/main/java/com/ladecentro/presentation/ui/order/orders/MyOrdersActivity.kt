package com.ladecentro.presentation.ui.order.orders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.order.orders.compose.MyOrders
import com.ladecentro.presentation.ui.order.orders.compose.TopAppBarMyOrders
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrdersActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LadecentroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.White
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBarMyOrders("My Orders")
                        },
                        contentColor = Color.White
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                                .background(Companion.White)
                        ) {
                            MyOrders()
                        }
                    }
                }
            }
        }
    }
}
