package com.ladecentro.presentation.ui.location.select.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.primary_orange

@Composable
fun LocationOptionPinCode() {

    Card(
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = drawable.location),
                contentDescription = "current location",
                modifier = Modifier.size(16.dp),
                tint = primary_orange,

                )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Enter Pincode",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamilyHind,
                color = primary_orange
            )
        }
    }
}

@Composable
fun LocationOptionCurrent() {

    Card(
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = drawable.current_location),
                contentDescription = "current location",
                modifier = Modifier.size(19.dp),
                tint = primary_orange,

                )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Use current location",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamilyHind,
                color = primary_orange
            )
        }
    }
}

@Composable
fun LocationOption() {
    LazyColumn(contentPadding = PaddingValues(12.dp)) {

        item {
            Spacer(modifier = Modifier.height(4.dp))
            LocationOptionPinCode()
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            LocationOptionCurrent()
        }
        item {
            Text(
                text = "Saved Address",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = fontFamilyHind,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        items(10) {
            SampleSavedAddress()
        }
    }
}