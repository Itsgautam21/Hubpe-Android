package com.ladecentro.presentation.ui.address.addresses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ladecentro.common.MyPreference
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.common.SharedPreference
import com.ladecentro.data.remote.dto.Location
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.use_case.GetProfileUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val myPreference: MyPreference,
    private val gson: Gson
) : ViewModel() {

    private val _userLocation = MutableStateFlow(UIStates<List<Location>>())
    val userLocation: StateFlow<UIStates<List<Location>>> = _userLocation

    val location: LocationRequest = gson.fromJson(
        myPreference.getStoresTag(SharedPreference.LOCATION.name),
        LocationRequest::class.java
    )

    init {
        getUserProfile()
    }

    fun getUserProfile() {

        viewModelScope.launch {
            getProfileFromLocal()?.let { data ->
                _userLocation.emit(UIStates(content = data.locations))
                return@launch
            }
            getProfileUseCase.invoke().collect {
                when (it) {
                    is Loading -> {
                        _userLocation.emit(UIStates(isLoading = true))
                    }

                    is Success -> {
                        _userLocation.emit(
                            UIStates(
                                isLoading = false,
                                content = it.data?.locations
                            )
                        )
                        myPreference.setStoredTag(
                            SharedPreference.PROFILE.name,
                            gson.toJson(it.data)
                        )
                    }

                    is Error -> {
                        _userLocation.emit(UIStates(isLoading = false, error = it.message))
                    }
                }
            }
        }
    }

    private fun getProfileFromLocal(): ProfileDto? {
        val profileJson = myPreference.getStoresTag(SharedPreference.PROFILE.name)
        if (profileJson.isNullOrBlank()) {
            return null
        }
        return gson.fromJson(profileJson, ProfileDto::class.java)
    }
}