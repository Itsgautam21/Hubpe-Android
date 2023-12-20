package com.ladecentro.presentation.ui.authentication.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladecentro.data.remote.dto.Profile
import com.ladecentro.data.remote.dto.SendOtpRequest
import com.ladecentro.domain.use_case.GetAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authUserCase: GetAuthUseCase) : ViewModel() {

    var phoneState by mutableStateOf("")

    fun sendOTP() {

        val request = SendOtpRequest("SEND", Profile(phoneState))
        Log.d("LoginViewModel", request.toString())

        viewModelScope.launch {
            authUserCase(request).collect()
        }
    }
}