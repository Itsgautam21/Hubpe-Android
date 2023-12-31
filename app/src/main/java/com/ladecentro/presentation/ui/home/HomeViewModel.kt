package com.ladecentro.presentation.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ladecentro.common.Constants.ERROR_TAG
import com.ladecentro.common.LocationResource
import com.ladecentro.common.MyPreference
import com.ladecentro.common.PreferenceUtils
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.common.SharedPreference
import com.ladecentro.data.remote.dto.LogoutRequest
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.domain.location.LocationTracker
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.use_case.GetLogoutUseCase
import com.ladecentro.domain.use_case.GetProfileUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLogoutUseCase: GetLogoutUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val locationTracker: LocationTracker,
    private val myPreference: MyPreference,
    private val preferenceUtils: PreferenceUtils,
) : ViewModel() {

    private val _state = MutableStateFlow(UIStates<ProfileDto>())
    val state: StateFlow<UIStates<ProfileDto>> get() = _state

    private val _location: MutableStateFlow<LocationResource> =
        MutableStateFlow(LocationResource.Loading(null))
    val location: StateFlow<LocationResource> get() = _location

    private var _locationAddress: LocationRequest? by mutableStateOf(preferenceUtils.getLocationFromLocal())
    val locationAddress: LocationRequest? get() = _locationAddress

    private var localProfile: String? = myPreference.getStoresTag(SharedPreference.PROFILE.name)

    var openBottomSheet by mutableStateOf(false)

    init {
        userProfile()
    }

    private fun userProfile() {

        viewModelScope.launch {
            if (localProfile != null) {
                setUserProfileFromPreference()
                return@launch
            }
            getProfileUseCase().collect {
                when (it) {
                    is Loading -> {
                        _state.emit(UIStates(isLoading = true))
                    }

                    is Success -> {
                        _state.emit(UIStates(isLoading = false, content = it.data))
                        myPreference.setStoredTag(
                            SharedPreference.PROFILE.name,
                            Gson().toJson(it.data)
                        )
                    }

                    is Error -> {
                        _state.emit(UIStates(isLoading = false, error = it.message))
                    }
                }
            }
        }
    }

    fun getUserLocation() {
        viewModelScope.launch {
            delay(50)
            locationTracker.getCurrentLocation().collect {
                when (it) {
                    is LocationResource.Loading -> {}
                    is LocationResource.Success -> {
                        _location.emit(LocationResource.Success(it.location))
                    }

                    is LocationResource.Error -> {
                        _location.emit(LocationResource.Error(it.intent))
                    }
                }
            }
        }
    }

    fun userLogout(clearUI: () -> Unit) {
        viewModelScope.launch {
            getLogoutUseCase(LogoutRequest("LOGOFF")).collect {
                when (it) {
                    is Loading -> {}
                    is Success -> {
                        myPreference.removeAllTags()
                        clearUI()
                    }

                    is Error -> {
                        Log.d(
                            ERROR_TAG,
                            myPreference.getStoresTag(SharedPreference.PROFILE.name).toString()
                        )
                    }
                }
            }
        }
    }

    fun setUserProfileFromPreference() {
        localProfile = myPreference.getStoresTag(SharedPreference.PROFILE.name)
        viewModelScope.launch {
            localProfile?.let {
                _state.emit(UIStates(content = Gson().fromJson(it, ProfileDto::class.java)))
            }
        }
    }

    fun getLocationFromLocal() {
        _locationAddress = preferenceUtils.getLocationFromLocal()
    }
}