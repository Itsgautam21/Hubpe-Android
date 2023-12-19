package com.ladecentro.domain.use_case

import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Build.VERSION
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.ladecentro.common.Resource
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.domain.model.PlacesResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MapsUseCase @Inject constructor(private val geocoder: Geocoder) {

    private val _addressFlow: MutableStateFlow<Resource<Address>> = MutableStateFlow(Loading())
    val addressFlow: StateFlow<Resource<Address>> get() = _addressFlow

    operator fun invoke(latLong: LatLng) {
        _addressFlow.value = Loading()
        try {
            if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latLong.latitude, latLong.longitude, 1) { p0 ->
                    _addressFlow.value = Success(p0[0])
                }
            } else {
                val addresses = geocoder.getFromLocation(latLong.latitude, latLong.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    _addressFlow.value = Success(addresses[0])
                } else {
                    _addressFlow.value = Error("Address is null")
                }
            }
        } catch (e: Exception) {
            _addressFlow.value = Error("Address is null")
        }
    }
}

class MapPlacesUseCase @Inject constructor(private val client: PlacesClient) {

    val locationAutofill = mutableStateListOf<PlacesResult>()

    operator fun invoke(query: String) {

        locationAutofill.clear()
        val request = FindAutocompletePredictionsRequest
            .builder()
            .setQuery(query)
            .build()

        client.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                locationAutofill += response.autocompletePredictions.map {
                    PlacesResult(
                        it.getFullText(null).toString(),
                        it.placeId,
                        it.getPrimaryText(null).toString(),
                        it.getSecondaryText(null).toString()
                    )
                }
            }.addOnFailureListener {
                Log.d(">>>> Place", it.message!!)
            }
    }

    fun getCoordinates(result: PlacesResult, latLon: (latLong: LatLng?) -> Unit) {

        val request = FetchPlaceRequest.newInstance(result.placeId, listOf(Place.Field.LAT_LNG))
        client.fetchPlace(request).addOnSuccessListener {
            it?.let {
                latLon(it.place.latLng)
                return@addOnSuccessListener
            }
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }
}