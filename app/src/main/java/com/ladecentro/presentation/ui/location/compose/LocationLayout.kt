package com.ladecentro.presentation.ui.location.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R
import com.ladecentro.presentation.theme.card_border
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.primary_orange

@Composable
fun LocationLayout() {

    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp)) {

        items(locationOptions) {
            SampleLocationOption(it)
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
    }
}

@Composable
fun SampleLocationOption(locationOption: LocationOption) {
    Row(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = locationOption.painterRes),
            contentDescription = "current location",
            modifier = Modifier.size(20.dp),
            tint = primary_orange,

        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = locationOption.text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamilyHind,
            color = primary_orange
        )
    }
    HorizontalDivider(
        thickness = 1.dp,
        color = card_border
    )
}

data class LocationOption(val text: String, val painterRes: Int)

val locationOptions = listOf(
    LocationOption("Enter Pincode", R.drawable.location),
    LocationOption("Use my current location", R.drawable.current_location)
)