package com.ladecentro.common

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.domain.model.LocationRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreference @Inject constructor(
    @ApplicationContext context: Context,
    private val gson: Gson
) {

    private val preference =
        context.getSharedPreferences(Constants.PREFERENCE_TAG, Context.MODE_PRIVATE)

    fun getStoresTag(key: String): String? {
        return preference.getString(key, null)
    }

    fun setStoredTag(key: String, query: String) {
        preference.edit().putString(key, query).apply()
    }

    fun removeStoredTag(key: String) {
        preference.edit().remove(key).apply()
    }

    fun removeAllTags() {
        preference.edit().clear().apply()
    }

    /**
     * Profile Data
     */
    fun getProfileFromLocal(): ProfileDto? {
        val profileJson = getStoresTag(SharedPreference.PROFILE.name) ?: return null
        return gson.fromJson(profileJson, ProfileDto::class.java)
    }

    fun setProfileToLocal(profileDto: ProfileDto) {
        setStoredTag(SharedPreference.PROFILE.name, gson.toJson(profileDto))
    }

    /**
     * Location Data
     */
    fun setLocationToLocal(locationRequest: LocationRequest) {
        setStoredTag(SharedPreference.LOCATION.name, gson.toJson(locationRequest))
    }

    fun getLocationFromLocal(): LocationRequest? {
        val locationJSON = getStoresTag(SharedPreference.LOCATION.name) ?: return null
        return gson.fromJson(locationJSON, LocationRequest::class.java)
    }

    /**
     * Cart Data
     */
    fun setCartToLocal(cartDto: List<CartDto>) {
        setStoredTag(SharedPreference.CART.name, gson.toJson(cartDto))
    }
    fun getCartFromLocal(): List<CartDto> {
        val cartJson = getStoresTag(SharedPreference.CART.name)
        if (cartJson.isNullOrBlank()) {
            return listOf()
        }
        return gson.fromJson(cartJson, object : TypeToken<List<CartDto>>() {}.type)
    }
}