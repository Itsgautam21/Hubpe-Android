package com.ladecentro.domain.repository

import com.ladecentro.data.remote.dto.LogoutRequest
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.data.remote.dto.SendOtpRequest
import com.ladecentro.data.remote.dto.VerifyOptRequest
import com.ladecentro.data.remote.dto.VerifyOtpResponse
import java.util.Objects

interface AuthRepository {

    suspend fun sendOtp(sendOtpRequest: SendOtpRequest): Objects?

    suspend fun verifyOpt(request: VerifyOptRequest) : VerifyOtpResponse

    suspend fun logoutUser(request: LogoutRequest): Objects?

    suspend fun userProfile() : ProfileDto
}