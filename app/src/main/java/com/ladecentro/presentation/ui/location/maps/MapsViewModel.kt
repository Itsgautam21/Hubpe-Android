package com.ladecentro.presentation.ui.location.maps

import android.location.Address
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.ladecentro.common.Intents
import com.ladecentro.common.LocationResource
import com.ladecentro.common.MyPreference
import com.ladecentro.common.Resource
import com.ladecentro.common.SharedPreference
import com.ladecentro.domain.location.LocationTracker
import com.ladecentro.domain.model.City
import com.ladecentro.domain.model.Country
import com.ladecentro.domain.model.Descriptor
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.model.PlacesResult
import com.ladecentro.domain.use_case.MapPlacesUseCase
import com.ladecentro.domain.use_case.MapsUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val mapsUseCase: MapsUseCase,
    private val myPreference: MyPreference,
    private val placesUseCase: MapPlacesUseCase,
    private val locationTracker: LocationTracker,
    private val gson: Gson,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _markerAddressDetail = MutableStateFlow(UIStates<Address>())
    val markerAddressDetail: StateFlow<UIStates<Address>> get() = _markerAddressDetail

    private val _location: MutableStateFlow<LocationResource> =
        MutableStateFlow(LocationResource.Loading(null))
    val location: StateFlow<LocationResource> get() = _location

    val locationAutofill: List<PlacesResult>
        get() = placesUseCase.locationAutofill.toList()

    private val _cameraPosition: MutableStateFlow<CameraPosition> = MutableStateFlow(
        savedStateHandle[Intents.CAMERA.name] ?: CameraPosition.fromLatLngZoom(
            LatLng(22.5726, 88.3639), 18f
        )
    )
    val cameraPosition: StateFlow<CameraPosition> get() = _cameraPosition
    val isAddAddress: String? = savedStateHandle[Intents.ADD_ADDRESS.name]
    var searchText by mutableStateOf("")

    init {
        searchPlaces()
    }

    fun getMarkerAddressDetails(latLong: LatLng) {
        _cameraPosition.value = CameraPosition.fromLatLngZoom(latLong, 18f)
        viewModelScope.launch {
            mapsUseCase(latLong)
            mapsUseCase.addressFlow.collect {
                when (it) {
                    is Resource.Loading -> {
                        _markerAddressDetail.emit(UIStates(isLoading = true))
                    }

                    is Resource.Success -> {
                        _markerAddressDetail.emit(UIStates(content = it.data))
                    }

                    is Resource.Error -> {
                        _markerAddressDetail.emit(UIStates(error = it.message))
                    }
                }
            }
        }
    }

    fun searchPlaces() {
        viewModelScope.launch {
            snapshotFlow { searchText }
                .collectLatest {
                    if (it.isNotBlank()) {
                        placesUseCase(it)
                    }
                }
        }
    }

    fun getCameraPosition(placesResult: PlacesResult) {
        viewModelScope.launch {
            placesUseCase.getCoordinates(placesResult) {
                it?.let {
                    _cameraPosition.value = CameraPosition.fromLatLngZoom(it, 18f)
                }
            }
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
                        _cameraPosition.value = CameraPosition.fromLatLngZoom(
                            LatLng(
                                it.location!!.latitude,
                                it.location.longitude
                            ), 18f
                        )
                    }

                    is LocationResource.Error -> {
                        _location.emit(LocationResource.Error(it.intent))
                    }
                }
            }
        }
    }

    fun setLocationToLocal() {

        markerAddressDetail.value.content?.let {
            val request = LocationRequest(
                descriptor = Descriptor(
                    name = it.featureName,
                    longDesc = it.getAddressLine(0)
                ),
                gps = "${it.latitude},${it.longitude}",
                city = City(name = it.locality),
                country = Country(
                    name = it.countryName,
                    code = it.countryCode
                )
            )
            myPreference.setStoredTag(SharedPreference.LOCATION.name, gson.toJson(request))
        }
    }
}