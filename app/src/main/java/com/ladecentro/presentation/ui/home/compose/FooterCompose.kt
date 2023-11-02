package com.ladecentro.presentation.ui.home.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R
import com.ladecentro.presentation.theme.darkBlue
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_gray
import com.ladecentro.presentation.theme.primary_orange

@Composable
@Preview(showBackground = true)
fun FooterCompose() {

    Column {
        Spacer(modifier = Modifier.height(40.dp))
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {

            Text(
                text = "Hubse is free for everyone",
                fontSize = 18.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Effortlessly set up shop on a zero commission platform that gives you complete control.",
                fontSize = 12.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.SemiBold,
                color = light_gray,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 0.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(personList.size) {
                Image(
                    painter = painterResource(id = personList[it]),
                    contentDescription = "person",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(240.dp)
                        .width(150.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .height(54.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = primary_orange)
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Start Selling on Hubse",
                fontSize = 16.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 44.dp)
                .height(32.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .background(color = darkBlue)
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "0% Commission",
                fontSize = 13.sp,
                fontFamily = fontFamilyHind,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }
    }
}

val personList = listOf(R.drawable.person2, R.drawable.person3, R.drawable.person1)