package com.ladecentro.presentation.ui.stores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.ui.stores.compose.StoreComposeUI
import com.ladecentro.presentation.ui.stores.compose.TopAppBarStores

@OptIn(ExperimentalMaterial3Api::class)
class StoresActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LadecentroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.White
                ) {
                    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
                    val scrollBehaviourTop = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehaviour.nestedScrollConnection)
                            .nestedScroll(scrollBehaviourTop.nestedScrollConnection),
                        topBar = {
                            TopAppBarStores(scrollBehaviour, scrollBehaviourTop)
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize()
                                .background(Color.White)
                        ) {
                            StoreComposeUI()
                        }
                    }
                }
            }
        }
    }
}