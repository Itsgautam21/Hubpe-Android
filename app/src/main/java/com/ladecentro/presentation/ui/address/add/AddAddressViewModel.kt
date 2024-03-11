package com.ladecentro.presentation.ui.address.add

import android.location.Address
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.Location
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.domain.model.City
import com.ladecentro.domain.model.Country
import com.ladecentro.domain.model.Descriptor
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.model.ProfileRequest
import com.ladecentro.domain.use_case.GetUpdateProfileUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val getUpdateProfileUseCase: GetUpdateProfileUseCase,
    private val myPreference: MyPreference,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val address: Address = savedStateHandle[Intents.ADDRESS.name]!!
    private val prevAddress: Location? = savedStateHandle[Intents.UPDATE_ADDRESS.name]

    private var _updateState by mutableStateOf(UIStates<ProfileDto>())
    val updateState: UIStates<ProfileDto> get() = _updateState

    private val profileData by lazy { myPreference.getProfileFromLocal() }

    var receiverName by mutableStateOf(
        value = prevAddress?.address?.name ?: profileData?.name ?: ""
    )
    var phoneNumber by mutableStateOf(
        value = prevAddress?.mobileNumber ?: profileData?.phone ?: ""
    )
    var house by mutableStateOf("")
    var area by mutableStateOf(address.subLocality ?: "")
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

    val addressTypes = listOf("Home", "Office", "Hotel", "Other")
    var selectedItem by mutableStateOf(addressTypes[0])

    fun addAddress() {

        val request = ProfileRequest(
            type = listOf("LOCATION"),
            operation = if (prevAddress == null) "ADD" else "REPLACE",
            locations = listOf(
                LocationRequest(
                    id = prevAddress?.id,
                    primary = false,
                    descriptor = Descriptor(
                        name = selectedItem,
                        longDesc = "$house, ${address.getAddressLine(0)}"
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
                _updateState = when (it) {
                    is Loading -> UIStates(isLoading = true)
                    is Success -> UIStates(content = it.data)
                    is Error -> UIStates(error = it.message)
                }
            }
        }
    }

    fun setProfileToLocal(profileDto: ProfileDto) = myPreference.setProfileToLocal(profileDto)
}