package com.ladecentro.presentation.ui.cart.carts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.cart.carts.compose.CartsLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LadecentroTheme {
                CartsLayout()
            }
        }
    }
}