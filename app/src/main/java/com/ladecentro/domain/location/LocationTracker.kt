package com.ladecentro.domain.location

import android.location.Location
import androidx.activity.result.IntentSenderRequest
import kotlinx.coroutines.flow.Flow

interface LocationTracker {

    fun getCurrentLocation(location : (intentSenderRequest: IntentSenderRequest) -> Unit): Flow<Location>
}