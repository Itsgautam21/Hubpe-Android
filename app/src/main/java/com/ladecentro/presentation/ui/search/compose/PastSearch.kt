package com.ladecentro.presentation.ui.search.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R.drawable
import com.ladecentro.common.bounceClick
import com.ladecentro.presentation.theme.dark_gray
import com.ladecentro.presentation.theme.doppio_one
import com.ladecentro.presentation.theme.fontFamilyHind
import com.ladecentro.presentation.theme.light_text
import com.ladecentro.presentation.ui.search.SearchViewModel

@Composable
private fun SamplePastSearch(pastSearch: String, vm: SearchViewModel = hiltViewModel()) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = drawable.history),
            contentDescription = "History",
            modifier = Modifier.size(20.dp),
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
                .bounceClick {
                    vm.isFocusState = false
                    vm.searchText = pastSearch
                }
        )
        IconButton(
            onClick = { vm.removePastSearch(pastSearch) },
            modifier = Modifier.size(20.dp)
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
fun PastSearch(vm: SearchViewModel = hiltViewModel()) {

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Past Searches",
                fontSize = 18.sp,
                fontFamily = fontFamilyHind,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
        items(items = vm.pastSearch.value.reversed(), key = { it }) {
            SamplePastSearch(it)
        }
    }
}
