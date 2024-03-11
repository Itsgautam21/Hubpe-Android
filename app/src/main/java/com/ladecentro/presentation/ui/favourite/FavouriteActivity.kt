package com.ladecentro.presentation.ui.favourite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.favourite.compose.FavouriteLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LadecentroTheme {
                FavouriteLayout()
            }
        }
    }
}