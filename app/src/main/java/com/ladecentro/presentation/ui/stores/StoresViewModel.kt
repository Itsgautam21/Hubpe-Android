package com.ladecentro.presentation.ui.stores

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ladecentro.common.PreferenceUtils
import com.ladecentro.domain.model.LocationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoresViewModel @Inject constructor(private val preferenceUtils: PreferenceUtils) :
    ViewModel() {

    private var _locationState by mutableStateOf(preferenceUtils.getLocationFromLocal())
    val locationState: LocationRequest get() = _locationState

    fun getLocationFromLocal() {
        _locationState = preferenceUtils.getLocationFromLocal()
    }
}