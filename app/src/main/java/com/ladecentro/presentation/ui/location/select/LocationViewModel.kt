package com.ladecentro.presentation.ui.location.select

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.ladecentro.common.LocationResource
import com.ladecentro.common.MyPreference
import com.ladecentro.data.remote.dto.Location
import com.ladecentro.data.remote.dto.mapToLocationRequest
import com.ladecentro.domain.location.LocationTracker
import com.ladecentro.domain.model.PlacesResult
import com.ladecentro.domain.use_case.MapPlacesUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val placesUseCase: MapPlacesUseCase,
    private val myPreference: MyPreference,
    private val locationTracker: LocationTracker
) : ViewModel() {

    val locationAutofill: List<PlacesResult>
        get() = placesUseCase.locationAutofill.toList()

    private val _location: MutableStateFlow<LocationResource> =
        MutableStateFlow(LocationResource.Loading(null))
    val location: StateFlow<LocationResource> get() = _location

    private val _userLocation = MutableStateFlow(UIStates<List<Location>>())
    val userLocation: StateFlow<UIStates<List<Location>>> = _userLocation

    var searchState by mutableStateOf("")

    private val profileData by lazy { myPreference.getProfileFromLocal() }

    init {
        getUserAddresses()
        searchPlaces()
    }

    private fun getUserAddresses() {
        viewModelScope.launch {
            profileData?.let {
                _userLocation.emit(UIStates(content = it.locations))
            }
        }
    }

    private fun searchPlaces() {

        viewModelScope.launch {
            snapshotFlow { searchState }
                .collectLatest {
                    if (it.isNotBlank()) {
                        placesUseCase(it)
                    }
                }
        }
    }

    fun getCameraPosition(
        placesResult: PlacesResult,
        result: (cameraPos: CameraPosition?) -> Unit
    ) {
        viewModelScope.launch {
            placesUseCase.getCoordinates(placesResult) {
                it?.let {
                    result(CameraPosition.fromLatLngZoom(it, 18f))
                }
            }
            result(null)
        }
    }

    fun getUserLocation() {
        viewModelScope.launch {
            delay(50)
            locationTracker.getCurrentLocation().collect {
                when (it) {
                    is LocationResource.Loading -> {
                        _location.emit(LocationResource.Loading(true))
                    }

                    is LocationResource.Success -> {
                        _location.emit(LocationResource.Success(it.location))
                    }

                    is LocationResource.Error -> {
                        _location.emit(LocationResource.Error(it.intent))
                    }
                }
            }
        }
    }

    fun setLocationToLocal(location: Location) {
        myPreference.setLocationToLocal(location.mapToLocationRequest())
    }
}