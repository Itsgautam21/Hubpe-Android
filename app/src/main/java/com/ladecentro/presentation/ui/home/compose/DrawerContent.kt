package com.ladecentro.presentation.ui.home.compose

import android.content.Intent
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
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state = vm.profileState.collectAsState().value
    var dialog by remember { mutableStateOf(false) }
    val result = rememberLauncherForActivityResult(StartActivityForResult()) {}

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
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
                        DrawerProfileInfo(
                            state.content?.photo ?: "",
                            state.content?.name ?: "Set your Name",
                            state.content?.phone ?: ""
                        ) {
                            result.launch(
                                Intent(context, ProfileActivity::class.java)
                                    .putExtra(Intents.USER_NAME.name, state.content?.name)
                                    .putExtra(Intents.Phone.name, state.content?.phone)
                                    .putExtra(Intents.USER_PHOTO.name, state.content?.photo)
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
                    DrawerMenuItem(title = "My Orders", image = drawable.my_orders) {
                        context.startActivity(Intent(context, MyOrdersActivity::class.java))
                    }
                    DrawerMenuDivider()
                    DrawerMenuItem(title = "Saved Addresses", image = drawable.saved_address) {
                        context.startActivity(Intent(context, AddressesActivity::class.java))
                    }
                    DrawerMenuDivider()
                    DrawerMenuItem(title = "Favourite Store", image = drawable.fav_store) {
                        context.startActivity(
                            Intent(context, FavouriteActivity::class.java)
                                .putExtra(Intents.TYPE_FAV.name, MY_FAVOURITES)
                        )
                    }
                    DrawerMenuDivider()
                    DrawerMenuItem(title = "My Rewards", image = drawable.my_rewards) {

                    }
                    DrawerMenuDivider()
                    DrawerMenuItem(title = "Logout", image = drawable.my_orders) {
                        dialog = true
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
            positiveClick = logout
        )
    }
}

@Composable
fun DrawerMenuItem(title: String, image: Int, onItemClick: () -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(14.dp)
            .bounceClick(onClick = onItemClick)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = title,
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
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun DrawerMenuDivider() {

    HorizontalDivider(
        color = card_border,
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp)
    )
}

@Composable
fun DrawerProfileInfo(image: String, name: String, phone: String, onClick: () -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.bounceClick(onClick = onClick)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            LoadImage(
                image = image,
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
                text = name,
                fontSize = 18.sp,
                fontFamily = fontFamilyHind,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = phone,
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
            modifier = Modifier.size(40.dp),
            tint = Color.White
        )
    }
}