package com.ladecentro.presentation.ui.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.search.compose.SearchLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LadecentroTheme {
                SearchLayout()
            }
        }
    }
}