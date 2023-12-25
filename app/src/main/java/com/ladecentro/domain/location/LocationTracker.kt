package com.ladecentro.domain.location

import android.location.Location
import androidx.activity.result.IntentSenderRequest
import com.ladecentro.common.LocationResource
import kotlinx.coroutines.flow.Flow

interface LocationTracker {

    fun getCurrentLocation(): Flow<LocationResource>
}