package com.ladecentro.presentation.ui.address.add

import android.location.Address
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val address: Address? = savedStateHandle["address"]

    var receiverName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var house by mutableStateOf((address?.locality ?: "") + ", " + (address?.premises ?: ""))
    var area by mutableStateOf((address?.thoroughfare ?: "") + ", " + (address?.subLocality ?: ""))
    var city by mutableStateOf(address?.locality ?: "")
    var landmark by mutableStateOf("")

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

//        val addresses = com.ladecentro.data.remote.dto.Address(address?.postalCode!!, house, city,
//            "India", "", "", receiverName, address.adminArea)
//
//        val addressess = CreateAddress(address = addresses,
//            city = City(city),
//            country = CountryXX("India"),
//            descriptor = DescriptorXXXX(address.getAddressLine(0), "Home"),
//            gps = address.latitude.toString() +"," + address.longitude.toString(),
//            id = null,
//            mobileNumber =  phoneNumber,
//            primary = false)
    }
}