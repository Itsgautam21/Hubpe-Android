package com.ladecentro.presentation.ui.stores.product

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.theme.light_orange
import com.ladecentro.presentation.ui.stores.product.compose.ProductsLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LadecentroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChangeStatusBarColor()
                    ProductsLayout()
                }
            }
        }
    }

    @Composable
    fun ChangeStatusBarColor() {

        val localView = LocalView.current
        val window = (localView.context as Activity).window

        SideEffect {
            window.statusBarColor = light_orange.toArgb()
            WindowCompat.getInsetsController(
                window, localView
            ).isAppearanceLightStatusBars = true
        }
    }
}

