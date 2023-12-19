package com.ladecentro.presentation.ui.address.addresses.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.LocationXX
import com.ladecentro.domain.use_case.GetProfileUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _userLocation = MutableStateFlow(UIStates<List<LocationXX>>())
    val userLocation = _userLocation

    init {
        getUserProfile()
    }

    private fun getUserProfile() {

        viewModelScope.launch {
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
                    }

                    is Error -> {
                        _userLocation.emit(UIStates(isLoading = false, error = it.message))
                    }
                }
            }
        }
    }
}