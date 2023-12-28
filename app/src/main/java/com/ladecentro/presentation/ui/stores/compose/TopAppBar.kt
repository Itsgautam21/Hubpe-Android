package com.ladecentro.presentation.ui.stores.compose

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R.drawable
import com.ladecentro.presentation.ui.cart.CartActivity
import com.ladecentro.presentation.ui.home.compose.SearchCompose
import com.ladecentro.presentation.ui.home.compose.SelectedLocationAppBarTitle
import com.ladecentro.presentation.ui.stores.StoresViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarStores(
    scrollBehaviour: TopAppBarScrollBehavior,
    scrollBehaviourTop: TopAppBarScrollBehavior,
    vm: StoresViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity

    Surface(shadowElevation = 0.dp) {
        Column {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                title = {
                    vm.locationState.let {
                        SelectedLocationAppBarTitle(
                            title = it.descriptor?.name ?: "",
                            description = it.descriptor?.longDesc ?: ""
                        ) {
                            vm.getLocationFromLocal()
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            context.finish()
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = drawable.back_arrow),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                CartActivity::class.java
                            )
                        )
                    }, modifier = Modifier.padding(end = 8.dp)) {
                        Image(
                            painter = painterResource(id = drawable.bag),
                            contentDescription = "Bag",
                        )
                    }
                },
                scrollBehavior = scrollBehaviourTop
            )
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White
                ),
                title = {
                    Column {
                        Spacer(modifier = Modifier.height(20.dp))
                        SearchCompose()
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        }
    }
}