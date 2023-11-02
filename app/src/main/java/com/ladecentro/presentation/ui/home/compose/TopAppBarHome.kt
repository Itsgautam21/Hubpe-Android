package com.ladecentro.presentation.ui.home.compose

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.presentation.theme.darkBlue
import com.ladecentro.presentation.theme.doppio_one
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.ui.cart.CartActivity
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarHome(
    scrollBehaviour: TopAppBarScrollBehavior,
    scrollBehaviourTop: TopAppBarScrollBehavior,
    drawerState: DrawerState
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
                    Column(modifier = Modifier.padding(horizontal = 4.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Home",
                                fontFamily = doppio_one,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = darkBlue
                            )
                            Spacer(modifier = Modifier.width(6.dp))
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
                            text = "DN Block, Ring Road, Sector V...",
                            fontFamily = fontFamilyHind,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Thin,
                            color = light_gray
                        )
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
                        Spacer(modifier = Modifier.height(20.dp))
                        SearchCompose()
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        }
    }
}