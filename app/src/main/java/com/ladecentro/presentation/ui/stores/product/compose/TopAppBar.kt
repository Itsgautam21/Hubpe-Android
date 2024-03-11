package com.ladecentro.presentation.ui.stores.product.compose

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.presentation.common.SearchCompose
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.light_orange
import com.ladecentro.presentation.ui.stores.product.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarStoreProducts(
    scrollBehaviourTop: TopAppBarScrollBehavior,
    scrollBehaviour: TopAppBarScrollBehavior,
    vm: ProductsViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity
    Surface(color = light_orange) {
        Column {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = light_orange,
                    scrolledContainerColor = light_orange
                ),
                title = {
                    TopBarProducts(category = vm.category, storeName = vm.storeName)
                },
                scrollBehavior = scrollBehaviourTop,
                actions = {
                },
                navigationIcon = {
                    IconButton(
                        onClick = { context.finish() },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Image(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                })
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = light_orange,
                    scrolledContainerColor = light_orange
                ),
                title = {
                    Box(
                        modifier = Modifier
                            .background(light_orange)
                            .padding(start = 0.dp, end = 2.dp, top = 8.dp, bottom = 8.dp)
                    ) {
                        SearchCompose(
                            title = "Search In ${vm.category}",
                            elevation = 0.dp,
                            height = 48.dp,
                            borderStroke = BorderStroke(1.dp, card_border)
                        ) {}
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        }
    }
}