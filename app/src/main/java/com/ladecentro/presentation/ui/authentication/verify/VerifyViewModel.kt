package com.ladecentro.presentation.ui.authentication.verify

import android.util.Log
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
import com.ladecentro.data.remote.dto.Profile
import com.ladecentro.data.remote.dto.VerifyOptRequest
import com.ladecentro.data.remote.dto.VerifyOtpResponse
import com.ladecentro.domain.use_case.GetVerifyUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val verifyUseCase: GetVerifyUseCase,
    private val myPreference: MyPreference,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(UIStates<VerifyOtpResponse>())
    val state get() = _state

    var otpState by mutableStateOf("")
    val phoneNumber = savedStateHandle.get<String>(Intents.Phone.name)

    fun verifyOTP() {

        val request = VerifyOptRequest(otpState, Profile(phoneNumber!!))
        viewModelScope.launch(Dispatchers.IO) {
            verifyUseCase(request).collect {
                when (it) {
                    is Loading -> {
                        _state.value = UIStates(isLoading = true)
                    }

                    is Success -> {
                        _state.value = UIStates(content = it.data)
                        myPreference.setStoredTag(Intents.Token.name, it.data?.token!!)
                        Log.d("API", it.data.toString())
                    }

                    is Error -> {
                        _state.value = UIStates(error = it.message)
                    }
                }
            }
        }
    }
}