package com.ladecentro.presentation.ui.profile

import androidx.compose.runtime.State
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
import com.ladecentro.domain.model.ProfileRequest
import com.ladecentro.domain.use_case.GetUpdateProfileUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUpdateProfileUseCase: GetUpdateProfileUseCase,
    private val myPreference: MyPreference,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var phoneNumber: String? = savedStateHandle[Intents.Phone.name]
    var userName: String by mutableStateOf(savedStateHandle[Intents.USER_NAME.name] ?: "")
    var photo: String? by mutableStateOf(savedStateHandle[Intents.USER_PHOTO.name])
    var nameErrorText by mutableStateOf("")
    var isNameError by mutableStateOf(false)

    private val _state = mutableStateOf(UIStates<ProfileDto>())
    val state: State<UIStates<ProfileDto>> get() = _state

    fun updateUser() {
        val request = ProfileRequest(name = userName.trim(), type = listOf("NAME"))
        viewModelScope.launch {
            getUpdateProfileUseCase(request).collect {
                when (it) {
                    is Loading -> {
                        _state.value = UIStates(isLoading = true)
                    }

                    is Success -> {
                        _state.value = UIStates(content = it.data)
                        myPreference.setStoredTag(SharedPreference.PROFILE.name, Gson().toJson(it.data))
                    }

                    is Error -> {
                        _state.value = UIStates(error = it.message)
                    }
                }
            }
        }
    }
}