package com.ladecentro.presentation.ui.cart.details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.cart.details.compose.CartLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LadecentroTheme {
                CartLayout()
            }
        }
    }
}