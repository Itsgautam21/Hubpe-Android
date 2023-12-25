package com.ladecentro.presentation.ui.address.add

import android.location.Address
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.common.SharedPreference
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.domain.model.City
import com.ladecentro.domain.model.Country
import com.ladecentro.domain.model.Descriptor
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.model.ProfileRequest
import com.ladecentro.domain.use_case.GetUpdateProfileUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val getUpdateProfileUseCase: GetUpdateProfileUseCase,
    private val myPreference: MyPreference,
    private val gson: Gson,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val address: Address = savedStateHandle[Intents.ADDRESS.name]!!

    private val _updateState = MutableStateFlow(UIStates<ProfileDto>())
    val updateState: StateFlow<UIStates<ProfileDto>> get() = _updateState

    var receiverName by mutableStateOf(getProfileFromLocal()?.name ?: "")
    var phoneNumber by mutableStateOf(getProfileFromLocal()?.phone ?: "")
    var house by mutableStateOf(address.featureName ?: "")
    var area by mutableStateOf( address.subLocality ?: "")
    var city by mutableStateOf(address.locality ?: "")
    var landmark by mutableStateOf(address.thoroughfare ?: "")

    var isPhoneError by mutableStateOf(false)
    var isNameError by mutableStateOf(false)
    var isHouseError by mutableStateOf(false)
    var isAreaError by mutableStateOf(false)
    var isCityError by mutableStateOf(false)

    var nameErrorText by mutableStateOf("")
    var phoneErrorText by mutableStateOf("")
    var houseErrorText by mutableStateOf("")
    var areaErrorText by mutableStateOf("")
    var cityErrorText by mutableStateOf("")

    fun addAddress() {

        val request = ProfileRequest(
            type = listOf("LOCATION"),
            operation = "ADD",
            locations = listOf(
                LocationRequest(
                    primary = false,
                    descriptor = Descriptor(
                        name = "Home",
                        longDesc = address.getAddressLine(0)
                    ),
                    address = com.ladecentro.domain.model.Address(
                        name = receiverName,
                        building = house,
                        locality = area,
                        city = address.locality,
                        state = address.adminArea,
                        country = address.countryName,
                        areaCode = address.postalCode
                    ),
                    mobileNumber = phoneNumber,
                    gps = "${address.latitude},${address.longitude}",
                    city = City(name = address.locality),
                    country = Country(
                        name = address.countryName,
                        code = address.countryCode
                    )
                )
            )
        )
        viewModelScope.launch {
            getUpdateProfileUseCase(request).collect {
                when (it) {
                    is Loading -> {
                        _updateState.emit(UIStates(isLoading = true))
                    }

                    is Success -> {
                        _updateState.emit(UIStates(content = it.data))
                    }

                    is Error -> {
                        _updateState.emit(UIStates(error = it.message))
                    }
                }
            }
        }
    }

    private fun getProfileFromLocal(): ProfileDto? {

        val profileJson = myPreference.getStoresTag(SharedPreference.PROFILE.name) ?: return null
        return gson.fromJson(profileJson, ProfileDto::class.java)
    }

    fun setProfileToLocal(profileDto: ProfileDto) {

        myPreference.setStoredTag(SharedPreference.PROFILE.name, gson.toJson(profileDto))
    }
}