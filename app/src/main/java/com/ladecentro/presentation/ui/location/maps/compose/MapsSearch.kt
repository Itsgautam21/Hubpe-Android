package com.ladecentro.presentation.ui.location.maps.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.R.drawable
import com.ladecentro.presentation.ui.location.maps.MapsViewModel
import com.ladecentro.presentation.ui.search.compose.SearchMainCompose

@Composable
fun MapsSearch(vm: MapsViewModel = hiltViewModel()) {

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(top = 14.dp, start = 14.dp)) {
            SearchMainCompose(
                value = vm.searchText,
                placeHolder = "Search Location",
                isFocus = false,
                changeValue = { vm.searchText = "" }
            ) { vm.searchText = it }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            if (vm.searchText.isNotBlank()) {
                PlacesMapCompose()
            }
            FloatingActionButton(
                onClick = { vm.getUserLocation() },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .size(52.dp),
                containerColor = Color.White,
                contentColor = Color.Black,
                shape = RoundedCornerShape(30.dp),
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = drawable.current_location),
                    contentDescription = "current location",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}