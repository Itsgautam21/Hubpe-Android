package com.ladecentro.presentation.ui.address.addresses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.address.addresses.compose.AddressesLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LadecentroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddressesLayout()
                }
            }
        }
    }
}
