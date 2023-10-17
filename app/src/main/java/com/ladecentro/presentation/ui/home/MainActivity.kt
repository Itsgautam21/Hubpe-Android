package com.ladecentro.presentation.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.ladecentro.presentation.theme.LadecentroTheme
import com.ladecentro.presentation.theme.darkBlue
import com.ladecentro.presentation.ui.home.compose.DrawerContent
import com.ladecentro.presentation.ui.home.compose.SearchCompose
import com.ladecentro.presentation.ui.home.compose.ShopCategory
import com.ladecentro.presentation.ui.home.compose.Spotlight
import com.ladecentro.presentation.ui.home.compose.TopAppBarHome
import com.ladecentro.presentation.ui.home.compose.YourFavourite
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LadecentroTheme {
                val drawerState = rememberDrawerState(initialValue = Closed)
                Status(drawerState = drawerState, mainActivity = this)
                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier = Modifier.fillMaxWidth(),
                            drawerContainerColor = Color.White,
                            drawerContentColor = Companion.Black
                        ) {
                            DrawerContent(drawerState = drawerState)
                        }
                    }, drawerState = drawerState
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
                        Scaffold(modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehaviour.nestedScrollConnection), topBar = {
                            TopAppBarHome(scrollBehaviour, drawerState)
                        }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(it)
                                    .background(color = Color.White)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Column(modifier = Modifier.padding(vertical = 20.dp)) {
                                    SearchCompose()
                                    YourFavourite()
                                    Spotlight()
                                    ShopCategory()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Status(drawerState: DrawerState, mainActivity: MainActivity) {

    val localView = LocalView.current
    if (drawerState.isOpen) {
        SideEffect {
            mainActivity.window.statusBarColor = darkBlue.toArgb()
            WindowCompat.getInsetsController(
                mainActivity.window, localView
            ).isAppearanceLightStatusBars = false
        }
    } else {
        SideEffect {
            mainActivity.window.statusBarColor = Color.White.toArgb()
            WindowCompat.getInsetsController(
                mainActivity.window, localView
            ).isAppearanceLightStatusBars = true
        }
    }
}