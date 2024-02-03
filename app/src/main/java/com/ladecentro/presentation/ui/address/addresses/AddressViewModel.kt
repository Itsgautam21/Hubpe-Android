package com.ladecentro.presentation.ui.address.addresses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ladecentro.common.MyPreference
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.common.SharedPreference
import com.ladecentro.data.remote.dto.Location
import com.ladecentro.data.remote.dto.mapToLocationRequest
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.model.ProfileRequest
import com.ladecentro.domain.use_case.GetProfileUseCase
import com.ladecentro.domain.use_case.GetUpdateProfileUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getUpdateProfileUseCase: GetUpdateProfileUseCase,
    private val myPreference: MyPreference,
    private val gson: Gson
) : ViewModel() {

    private var _userLocation by mutableStateOf(UIStates<List<Location>>())
    val userLocation: UIStates<List<Location>> get() = _userLocation

    val location: LocationRequest = gson.fromJson(
        myPreference.getStoresTag(SharedPreference.LOCATION.name),
        LocationRequest::class.java
    )

    private val profileData by lazy { myPreference.getProfileFromLocal() }

    init {
        getUserProfile()
    }

    fun getUserProfile() {

        viewModelScope.launch {
            profileData?.let { data ->
                _userLocation = UIStates(content = data.locations)
                return@launch
            }
            getProfileUseCase.invoke().collect {
                _userLocation = when (it) {
                    is Loading -> UIStates(isLoading = true)
                    is Error -> UIStates(error = it.message)
                    is Success -> {
                        myPreference.setStoredTag(
                            SharedPreference.PROFILE.name,
                            gson.toJson(it.data)
                        )
                        UIStates(content = it.data?.locations)
                    }
                }
            }
        }
    }

    fun deleteAddress(location: Location) {

        val request = ProfileRequest(
            type = listOf("LOCATION"),
            operation = "REMOVE",
            locations = listOf(location.mapToLocationRequest())
        )

        viewModelScope.launch {
            getUpdateProfileUseCase(request).collect {
                _userLocation = when (it) {
                    is Loading -> UIStates(isLoading = true)
                    is Success -> {
                        myPreference.setProfileToLocal(it.data!!)
                        UIStates(content = it.data.locations)
                    }

                    is Error -> UIStates(error = it.message)
                }
            }
        }
    }
}