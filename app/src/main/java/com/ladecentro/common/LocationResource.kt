package com.ladecentro.common

import android.location.Location
import androidx.activity.result.IntentSenderRequest

sealed class LocationResource(
    val location: Location? = null,
    val intent: IntentSenderRequest? = null,
    val isLoading: Boolean? = null
) {

    class Loading(isLoading: Boolean?) : LocationResource(isLoading = isLoading)
    class Success(location: Location?) : LocationResource(location = location)
    class Error(intent: IntentSenderRequest?) : LocationResource(intent = intent)
}