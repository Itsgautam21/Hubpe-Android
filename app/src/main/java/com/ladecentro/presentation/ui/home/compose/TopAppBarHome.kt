package com.ladecentro.presentation.ui.home.compose

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R.drawable
import com.ladecentro.common.bounceClick
import com.ladecentro.presentation.common.SearchCompose
import com.ladecentro.presentation.theme.darkBlue
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.poppins
import com.ladecentro.presentation.ui.cart.carts.CartActivity
import com.ladecentro.presentation.ui.home.HomeViewModel
import com.ladecentro.presentation.ui.location.select.LocationActivity
import com.ladecentro.presentation.ui.search.SearchActivity
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarHome(
    scrollBehaviour: TopAppBarScrollBehavior,
    scrollBehaviourTop: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    vm: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Surface(shadowElevation = 0.dp, color = Color.White) {
        Column {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Companion.White,
                    scrolledContainerColor = Companion.White
                ),
                title = {
                    vm.locationAddress?.let {
                        SelectedLocationAppBarTitle(
                            title = it.descriptor?.name ?: "",
                            description = it.descriptor?.longDesc ?: ""
                        ) {
                            vm.getLocationFromLocal()
                        }
                    }

                },
                scrollBehavior = scrollBehaviourTop,
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
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }, modifier = Modifier.padding(start = 8.dp)) {
                        Image(
                            painter = painterResource(id = drawable.menu),
                            contentDescription = "Menu"
                        )
                    }
                })
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Companion.White,
                    scrolledContainerColor = Companion.White
                ),
                title = {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        SearchCompose(title = "What do you want today?") {
                            context.startActivity(Intent(context, SearchActivity::class.java))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        }
    }
}

@Composable
fun SelectedLocationAppBarTitle(title: String, description: String, onLocationSelect: () -> Unit) {

    val context = LocalContext.current
    val result = rememberLauncherForActivityResult(StartActivityForResult()) {
        if (it.resultCode == ComponentActivity.RESULT_OK) {
            onLocationSelect()
        }
    }

    Column(modifier = Modifier
        .padding(horizontal = 4.dp)
        .bounceClick {
            result.launch(
                Intent(
                    context,
                    LocationActivity::class.java
                )
            )
        }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                text = title,
                fontFamily = poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = darkBlue,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = drawable.down_arrow),
                contentDescription = null,
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp)
                    .padding(top = 2.dp),
                tint = darkBlue
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = description,
            fontFamily = fontFamilyHind,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = light_gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(0.9f),
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}