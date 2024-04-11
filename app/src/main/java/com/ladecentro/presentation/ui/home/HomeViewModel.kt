package com.ladecentro.presentation.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladecentro.common.Constants.ERROR_TAG
import com.ladecentro.common.LocationResource
import com.ladecentro.common.MyPreference
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
    private val myPreference: MyPreference
) : ViewModel() {

    private val _profileState = MutableStateFlow(UIStates<ProfileDto>())
    val profileState: StateFlow<UIStates<ProfileDto>> get() = _profileState

    private val _location: MutableStateFlow<LocationResource> =
        MutableStateFlow(LocationResource.Loading(null))
    val location: StateFlow<LocationResource> get() = _location

    private var _locationAddress: LocationRequest? by mutableStateOf(myPreference.getLocationFromLocal())
    val locationAddress: LocationRequest? get() = _locationAddress

    private var _cartSize by mutableIntStateOf(0)
    val cartSize get() = _cartSize

    var openBottomSheet by mutableStateOf(false)

    init {
        userProfile()
    }

    private fun userProfile() {

        viewModelScope.launch {
            myPreference.getProfileFromLocal()?.let {
                _profileState.emit(UIStates(content = it))
                return@launch
            }
            getProfileUseCase().collect {
                when (it) {
                    is Loading -> {
                        _profileState.emit(UIStates(isLoading = true))
                    }

                    is Success -> {
                        _profileState.emit(UIStates(isLoading = false, content = it.data))
                        myPreference.setProfileToLocal(it.data!!)
                    }

                    is Error -> {
                        _profileState.emit(UIStates(isLoading = false, error = it.message))
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
                    is LocationResource.Loading -> {
                        Log.d(">>> Location", "Location Loading!!")
                    }
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
                    is Loading -> {
                        Log.d(">>> Logoff", "Logoff!!")
                    }
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
        val localProfile = myPreference.getProfileFromLocal()
        viewModelScope.launch {
            localProfile?.let {
                _profileState.emit(UIStates(content = it))
            }
        }
    }

    fun getLocationFromLocal() {
        _locationAddress = myPreference.getLocationFromLocal()
    }

    fun getCartFromLocal() {
        _cartSize = myPreference.getCartFromLocal().size
    }
}