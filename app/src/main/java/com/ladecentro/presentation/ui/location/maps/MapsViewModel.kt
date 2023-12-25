package com.ladecentro.presentation.ui.location.maps

import android.location.Address
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.common.SharedPreference
import com.ladecentro.data.remote.dto.Location
import com.ladecentro.domain.model.City
import com.ladecentro.domain.model.Country
import com.ladecentro.domain.model.Descriptor
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.use_case.MapsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val mapsUseCase: MapsUseCase,
    private val myPreference: MyPreference,
    private val gson: Gson,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _markerAddressDetail = MutableStateFlow(Address(Locale.US))
    val markerAddressDetail: StateFlow<Address> get() = _markerAddressDetail

    private val _cameraPosition: MutableStateFlow<CameraPosition> = MutableStateFlow(
        savedStateHandle[Intents.CAMERA.name] ?: CameraPosition.fromLatLngZoom(
            LatLng(22.5726, 88.3639), 18f
        )
    )
    val cameraPosition get() = _cameraPosition

    val isAddAddress: String? = savedStateHandle[Intents.ADD_ADDRESS.name]

    fun getMarkerAddressDetails(latLong: LatLng) {
        viewModelScope.launch {
            mapsUseCase.invoke(latLong)
            mapsUseCase.addressFlow.collect {
                if (it.data != null) {
                    _markerAddressDetail.emit(it.data)
                }
            }
        }
    }

    fun setLocationToLocal() {

        val request = LocationRequest(
            descriptor = Descriptor(name = markerAddressDetail.value.featureName,
                longDesc = markerAddressDetail.value.getAddressLine(0)
            ),
            gps = "${markerAddressDetail.value.latitude},${markerAddressDetail.value.longitude}",
            city = City(name = markerAddressDetail.value.locality),
            country = Country(
                name = markerAddressDetail.value.countryName,
                code = markerAddressDetail.value.countryCode
            )
        )
        myPreference.setStoredTag(SharedPreference.LOCATION.name, gson.toJson(request))
    }
}