package com.ladecentro.presentation.ui.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.search.compose.SearchLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {

    private val mViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LadecentroTheme {
                SearchLayout()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}