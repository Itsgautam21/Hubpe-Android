package com.ladecentro.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladecentro.common.Resource
import com.ladecentro.data.remote.dto.LogoutRequest
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.domain.use_case.GetLogoutUseCase
import com.ladecentro.domain.use_case.GetProfileUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLogoutUseCase: GetLogoutUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UIStates<ProfileDto>())
    val state get() = _state

    init {
        userProfile()
    }

    private fun userProfile() {

        viewModelScope.launch(Dispatchers.IO) {

            getProfileUseCase().collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.emit(UIStates(isLoading = true))
                    }

                    is Resource.Success -> {
                        _state.emit(UIStates(isLoading = false, content = it.data))
                    }

                    is Resource.Error -> {
                        _state.emit(UIStates(isLoading = false, error = it.message))
                    }
                }

            }
        }
    }

    fun userLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            getLogoutUseCase(LogoutRequest("LOGOFF")).collect()
        }
    }
}