package com.ladecentro.presentation.ui.profile.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.primary_orange
import com.ladecentro.presentation.ui.profile.ProfileViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainProfileUI(vm: ProfileViewModel = hiltViewModel()) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Companion.Black
        )
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {

            Box(modifier = Modifier.height(98.dp), contentAlignment = Alignment.BottomCenter) {
                Surface(
                    modifier = Modifier.padding(bottom = 14.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = card_background,
                    contentColor = Companion.Green,
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.store_logo),
                        contentDescription = "profile image",
                        modifier = Modifier.size(84.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Icon(
                    imageVector = Rounded.AddCircle,
                    contentDescription = "Add Photo",
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(28.dp)
                        .background(Companion.White)

                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = vm.userName ?: "",
                onValueChange = {
                    if (it.length <= 50) {
                        vm.userName = it
                    }
                },
                label = {
                    Text(
                        text = "Full Name",
                        fontFamily = fontFamilyHind
                    )
                },
                textStyle = TextStyle(
                    fontSize = 15.sp
                ),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = vm.phoneNumber ?: "",
                onValueChange = {},
                label = {
                    Text(
                        text = "Mobile Number",
                        fontFamily = fontFamilyHind,
                    )
                },
                textStyle = TextStyle(
                    fontSize = 15.sp
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                enabled = false
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { vm.updateUser() },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primary_orange),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !vm.state.value.isLoading
            ) {
                Text(
                    text = "Save",
                    fontFamily = fontFamilyHind,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

    }
}