package com.ladecentro.presentation.ui.stores.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.stores.search.compose.SearchStoreProduct
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductSearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LadecentroTheme {
                SearchStoreProduct()
            }
        }
    }
}
