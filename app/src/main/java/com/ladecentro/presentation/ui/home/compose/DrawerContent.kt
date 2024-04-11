package com.ladecentro.presentation.ui.home.compose

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R.drawable
import com.ladecentro.common.Constants.MY_FAVOURITES
import com.ladecentro.common.Intents
import com.ladecentro.common.bounceClick
import com.ladecentro.presentation.common.LoadImage
import com.ladecentro.presentation.common.SimpleDialog
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.ui.address.addresses.AddressesActivity
import com.ladecentro.presentation.ui.favourite.FavouriteActivity
import com.ladecentro.presentation.ui.home.HomeViewModel
import com.ladecentro.presentation.ui.order.orders.MyOrdersActivity
import com.ladecentro.presentation.ui.profile.ProfileActivity
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    vm: HomeViewModel = hiltViewModel(),
    drawerState: DrawerState,
    logout: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = vm.profileState.collectAsState().value
    var dialog by remember { mutableStateOf(false) }
    val result = rememberLauncherForActivityResult(StartActivityForResult()) {
        if (it.resultCode == ComponentActivity.RESULT_OK) {
            //vm.setUserProfileFromPreference()
        }
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .background(color = MaterialTheme.colorScheme.primary)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)

                    ) {
                        Icon(
                            imageVector = Filled.ArrowBack,
                            contentDescription = "arrow_back",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.bounceClick {
                                result.launch(
                                    Intent(
                                        context,
                                        ProfileActivity::class.java
                                    ).putExtra(Intents.USER_NAME.name, state.content?.name)
                                        .putExtra(Intents.Phone.name, state.content?.phone)
                                        .putExtra(Intents.USER_PHOTO.name, state.content?.photo)
                                )
                            }) {
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(0.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                LoadImage(
                                    image = state.content?.photo ?: "",
                                    modifier = Modifier.size(50.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = state.content?.name ?: "",
                                    fontSize = 18.sp,
                                    fontFamily = fontFamilyHind,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = state.content?.phone ?: "",
                                    fontSize = 12.sp,
                                    fontFamily = fontFamilyHind,
                                    color = Color.White,
                                    fontWeight = FontWeight.Normal,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                            }
                            Icon(
                                painter = painterResource(id = drawable.forward_arrow),
                                contentDescription = "arrow",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 120.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(14.dp)
                            .bounceClick {
                                context.startActivity(Intent(context, MyOrdersActivity::class.java))
                            }
                    ) {
                        Image(
                            painter = painterResource(id = drawable.my_orders),
                            contentDescription = "",
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                        Text(
                            text = "My Orders",
                            fontSize = 15.sp,
                            fontFamily = fontFamilyHind,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp)
                        )
                        Icon(
                            painter = painterResource(id = drawable.forward_arrow),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp)
                        )
                    }
                    HorizontalDivider(
                        color = card_border,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 50.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(14.dp)
                            .bounceClick {
                                context.startActivity(
                                    Intent(
                                        context,
                                        AddressesActivity::class.java
                                    )
                                )
                            }
                    ) {
                        Image(
                            painter = painterResource(id = drawable.saved_address),
                            contentDescription = "",
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                        Text(
                            text = "Saved Addresses",
                            fontSize = 15.sp,
                            fontFamily = fontFamilyHind,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp)
                        )
                        Icon(
                            painter = painterResource(id = drawable.forward_arrow),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp)
                        )
                    }
                    HorizontalDivider(
                        color = card_border,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 50.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(14.dp)
                            .bounceClick {
                                context.startActivity(
                                    Intent(
                                        context, FavouriteActivity::class.java
                                    ).putExtra(Intents.TYPE_FAV.name, MY_FAVOURITES)
                                )
                            }
                    ) {
                        Image(
                            painter = painterResource(id = drawable.fav_store),
                            contentDescription = "",
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                        Text(
                            text = "Favourite Store",
                            fontSize = 15.sp,
                            fontFamily = fontFamilyHind,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp)
                        )
                        Icon(
                            painter = painterResource(id = drawable.forward_arrow),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp)
                        )
                    }
                    HorizontalDivider(
                        color = card_border,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 50.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(14.dp)
                    ) {
                        Image(
                            painter = painterResource(id = drawable.my_rewards),
                            contentDescription = "",
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                        Text(
                            text = "My Rewards",
                            fontSize = 15.sp,
                            fontFamily = fontFamilyHind,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp)
                        )
                        Icon(
                            painter = painterResource(id = drawable.forward_arrow),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp)
                        )
                    }
                    HorizontalDivider(
                        color = card_border,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 50.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(14.dp)
                            .clickable { dialog = true }
                    ) {
                        Image(
                            painter = painterResource(id = drawable.my_orders),
                            contentDescription = "",
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                        Text(
                            text = "Logout",
                            fontSize = 15.sp,
                            fontFamily = fontFamilyHind,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp)
                        )
                        Icon(
                            painter = painterResource(id = drawable.forward_arrow),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp)
                        )
                    }
                }
            }
        }
    }
    if (dialog) {
        SimpleDialog(
            dismissRequest = { dialog = false },
            negativeClick = { dialog = false },
            body = "Are you sure want to logout?",
            positiveClick = { logout() }
        )
    }
}