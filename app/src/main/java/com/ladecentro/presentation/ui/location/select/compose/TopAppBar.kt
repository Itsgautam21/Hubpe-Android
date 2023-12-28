package com.ladecentro.presentation.ui.location.select.compose

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ladecentro.presentation.theme.card_background
import com.ladecentro.presentation.ui.location.select.LocationViewModel
import com.ladecentro.presentation.ui.search.compose.TopAppBarSearch

@Composable
fun TopAppBarLocation(vm: LocationViewModel = hiltViewModel()) {

    TopAppBarSearch(
        value = vm.searchState,
        placeHolder = "Search Location",
        isFocus = false,
        color = card_background,
        changeValue = { vm.searchState = "" },
        textValue = { vm.searchState = it }
    )
}