package com.ladecentro.presentation.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladecentro.common.Intents
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.data.remote.dto.UpdateProfileRequest
import com.ladecentro.domain.use_case.GetUpdateProfileUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUpdateProfileUseCase: GetUpdateProfileUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var userName by mutableStateOf(savedStateHandle.get<String>(Intents.USER_NAME.name))
    val phoneNumber = savedStateHandle.get<String>(Intents.Phone.name)

    private val _state = mutableStateOf(UIStates<ProfileDto>())
    val state get() = _state

    fun updateUser() {
        val request = UpdateProfileRequest(userName!!.trim(), listOf("NAME"))
        viewModelScope.launch {
            getUpdateProfileUseCase(request).collect {
                when (it) {
                    is Loading -> {
                        _state.value = UIStates(isLoading = true)
                    }

                    is Success -> {
                        _state.value = UIStates(content = it.data)
                    }

                    is Error -> {
                        _state.value = UIStates(error = it.message)
                    }
                }
            }
        }
    }
}