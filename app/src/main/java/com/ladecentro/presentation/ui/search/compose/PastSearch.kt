package com.ladecentro.presentation.ui.search.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ladecentro.R.drawable
import com.ladecentro.presentation.theme.dark_gray
import com.ladecentro.presentation.theme.doppio_one
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_text

@Composable
private fun SamplePastSearch(pastSearch: String) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = drawable.history),
            contentDescription = "History",
            modifier = Modifier
                .height(20.dp)
                .width(20.dp),
            tint = light_text
        )
        Text(
            text = pastSearch,
            fontSize = 13.sp,
            fontFamily = doppio_one,
            color = dark_gray,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        )
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .height(20.dp)
                .width(20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "History",

                tint = light_text
            )
        }
    }
}

@Composable
fun PastSearch() {

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Past Searches",
            fontSize = 18.sp,
            fontFamily = fontFamilyHind,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pastSearches.size) {
                SamplePastSearch(pastSearch = pastSearches[it])
            }
        }
    }
}

private val pastSearches = listOf(
    "Snacks",
    "Vegetable Oil",
    "Formal Shirts",
    "Snacks",
    "Vegetable Oil",
    "Formal Shirts",
    "Snacks",
    "Vegetable Oil",
    "Formal Shirts"
)
