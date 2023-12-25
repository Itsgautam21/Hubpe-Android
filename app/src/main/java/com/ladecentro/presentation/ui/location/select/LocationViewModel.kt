package com.ladecentro.presentation.ui.location.select

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.gson.Gson
import com.ladecentro.common.LocationResource
import com.ladecentro.common.MyPreference
import com.ladecentro.common.SharedPreference
import com.ladecentro.data.remote.dto.Location
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.data.remote.dto.mapToLocationRequest
import com.ladecentro.domain.location.LocationTracker
import com.ladecentro.domain.model.PlacesResult
import com.ladecentro.domain.use_case.MapPlacesUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val placesUseCase: MapPlacesUseCase,
    private val myPreference: MyPreference,
    private val locationTracker: LocationTracker,
    private val gson: Gson
) : ViewModel() {

    val locationAutofill: List<PlacesResult>
        get() = placesUseCase.locationAutofill.toList()

    private val _location: MutableStateFlow<LocationResource> =
        MutableStateFlow(LocationResource.Loading(null))
    val location: StateFlow<LocationResource> get() = _location

    private val _userLocation = MutableStateFlow(UIStates<List<Location>>())
    val userLocation: StateFlow<UIStates<List<Location>>> = _userLocation

    private val _searchState = mutableStateOf("")
    val searchState: State<String> get() = _searchState

    private var job: Job? = null

    init {
        getUserAddresses()
    }

    private fun getUserAddresses() {
        viewModelScope.launch {
            getProfileFromLocal()?.let {
                _userLocation.emit(UIStates(content = it.locations))
            }
        }
    }

    fun searchPlaces(query: String) {
        _searchState.value = query
        job?.cancel()
        job = viewModelScope.launch {
            placesUseCase(query)
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

    private fun getProfileFromLocal(): ProfileDto? {
        val profileJson = myPreference.getStoresTag(SharedPreference.PROFILE.name)
        if (profileJson.isNullOrBlank()) {
            return null
        }
        return gson.fromJson(profileJson, ProfileDto::class.java)
    }

    fun setLocationToLocal(location: Location) {
        myPreference.setStoredTag(
            SharedPreference.LOCATION.name,
            gson.toJson(location.mapToLocationRequest())
        )
    }
}