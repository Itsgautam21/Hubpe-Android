package com.ladecentro.presentation.ui.location.select

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.ladecentro.domain.model.PlacesResult
import com.ladecentro.domain.use_case.MapPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val placesUseCase: MapPlacesUseCase) :
    ViewModel() {

    val locationAutofill: List<PlacesResult>
        get() = placesUseCase.locationAutofill.toList()

    private val _searchState = mutableStateOf("")
    val searchState: State<String> get() = _searchState

    private var job: Job? = null

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

    fun setSearchState(value: String) {
        _searchState.value = value
    }
}