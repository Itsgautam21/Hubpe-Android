package com.ladecentro.data.location

import android.annotation.SuppressLint
import android.app.Application
import android.os.Looper
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest.Builder
import com.google.android.gms.location.Priority
import com.ladecentro.common.LocationResource
import com.ladecentro.domain.location.LocationTracker
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentLocationTracker @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Flow<LocationResource> {

        return callbackFlow {

            launch { send(LocationResource.Loading(true)) }
            val locationRequest = LocationRequest
                .Builder(Priority.PRIORITY_HIGH_ACCURACY, 500)
                .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                .setMinUpdateIntervalMillis(500)
                .setMinUpdateDistanceMeters(1f)
                .setWaitForAccurateLocation(true)
                .build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    p0.locations.lastOrNull()?.let {
                        launch { send(LocationResource.Success(it)) }
                    }
                    fusedLocationProviderClient.removeLocationUpdates(this)
                }
            }
            val builder = Builder().addLocationRequest(locationRequest)
            LocationServices
                .getSettingsClient(application)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener {
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper()
                    )
                }
                .addOnFailureListener { ex ->
                    if (ex is ResolvableApiException) {
                        try {
                            val intentSenderRequest: IntentSenderRequest =
                                IntentSenderRequest.Builder(ex.resolution).build()
                            launch {
                                send(LocationResource.Error(intentSenderRequest))
                            }
                        } catch (exception: Exception) {
                            Log.d("TAG", "enableLocationSettings: $exception")
                        }
                    }
                }
            awaitClose {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }
        }.catch {

        }
    }
}