package com.ladecentro.presentation.ui.get_users.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ladecentro.domain.model.User
import com.ladecentro.presentation.theme.fontFamilyHind

@Composable
fun SampleUser(user: User) {

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .weight(.3f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = user.avatarUrl,
                fontFamily = fontFamilyHind,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                modifier = Modifier
                    .weight(.7f)
                    .padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun UsersList(list: List<User>) {
    LazyColumn(modifier = Modifier.fillMaxSize(), content = {
        items(list) {
            SampleUser(it)
        }
    })
}