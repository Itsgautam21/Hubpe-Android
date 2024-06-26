package com.ladecentro.presentation.ui.home.compose

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.data.remote.dto.toFavouriteStore
import com.ladecentro.presentation.theme.darkBlue
import com.ladecentro.presentation.ui.home.HomeViewModel
import com.ladecentro.presentation.ui.order.orders.compose.ShimmerContent
import com.ladecentro.presentation.ui.spalsh.OnboardingActivity

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainLayout(vm: HomeViewModel = hiltViewModel()) {

    val context = LocalContext.current as Activity
    val profileState = vm.profileState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = Closed)
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollBehaviourTop = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(),
                drawerContainerColor = Color.White,
                drawerContentColor = Color.Black
            ) {
                DrawerContent(drawerState = drawerState) {
                    vm.userLogout {
                        context.startActivity(Intent(context, OnboardingActivity::class.java))
                        context.finish()
                    }
                }
            }
        }
    ) {
        // Loading state...
        if (profileState.value.isLoading) {
            ShimmerContent()
        }

        // Success state...
        profileState.value.content?.let { profile ->

            Scaffold(modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehaviour.nestedScrollConnection)
                .nestedScroll(scrollBehaviourTop.nestedScrollConnection),
                topBar = {
                    TopAppBarHome(scrollBehaviour, scrollBehaviourTop, drawerState)
                }
            ) { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(color = Color.White)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        HomeBanner()
                        Spacer(modifier = Modifier.height(24.dp))
                        Spotlight()
                        Spacer(modifier = Modifier.height(30.dp))
                        profile.favourites?.let { favourites ->
                            YourFavourite(favourites.map { fav -> fav.toFavouriteStore() })
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        profile.history?.let { histories ->
                            RecentlyViewed(histories.map { his -> his.toFavouriteStore() })
                        }
                        Spacer(modifier = Modifier.height(0.dp))
                        ShopCategory()
                        FooterCompose()
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }

        // Error state...
        profileState.value.error?.let {
            // TODO
        }
    }
    ChangeStatusBar(drawerState)
}

@Composable
fun ChangeStatusBar(drawerState: DrawerState) {

    val localView = LocalView.current
    val window = (localView.context as Activity).window
    if (drawerState.isOpen) {
        SideEffect {
            window.statusBarColor = darkBlue.toArgb()
            WindowCompat.getInsetsController(
                window, localView
            ).isAppearanceLightStatusBars = false
        }
    } else {
        SideEffect {
            window.statusBarColor = Color.White.toArgb()
            WindowCompat.getInsetsController(
                window, localView
            ).isAppearanceLightStatusBars = true
        }
    }
}