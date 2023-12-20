package com.ladecentro.presentation.ui.location.maps

import android.location.Address
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _markerAddressDetail = MutableStateFlow(Address(Locale.US))
    val markerAddressDetail: StateFlow<Address> get() = _markerAddressDetail

    private val _cameraPosition: MutableStateFlow<CameraPosition> = MutableStateFlow(
        savedStateHandle["camera"] ?: CameraPosition.fromLatLngZoom(
            LatLng(22.5726, 88.3639), 18f
        )
    )
    val cameraPosition get() = _cameraPosition

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
}