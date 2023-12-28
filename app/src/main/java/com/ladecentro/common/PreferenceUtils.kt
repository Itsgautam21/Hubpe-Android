package com.ladecentro.common

import com.google.gson.Gson
import com.ladecentro.domain.model.LocationRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceUtils @Inject constructor(
    private val myPreference: MyPreference,
    private val gson: Gson
) {

    fun getLocationFromLocal(): LocationRequest {
        val locationJSON = myPreference.getStoresTag(SharedPreference.LOCATION.name)
        return gson.fromJson(locationJSON, LocationRequest::class.java)
    }
}