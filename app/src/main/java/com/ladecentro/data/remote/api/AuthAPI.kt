package com.ladecentro.data.remote.api

import com.ladecentro.common.Constants
import com.ladecentro.data.remote.dto.LogoutRequest
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.data.remote.dto.SendOtpRequest
import com.ladecentro.data.remote.dto.VerifyOptRequest
import com.ladecentro.data.remote.dto.VerifyOtpResponse
import com.ladecentro.domain.model.ProfileRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.*

interface AuthAPI {

    @POST("/v1/send-otp")
    suspend fun sendOpt(@Body optRequest: SendOtpRequest): Response<Objects>

    @POST("/v1/register")
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOptRequest): Response<VerifyOtpResponse>

    @POST("/v1/logoff")
    suspend fun logout(
        @Body request: LogoutRequest,
        @Header(Constants.Authorization) authorization: String?
    ): Response<Objects>

    @POST("/v1/profile")
    suspend fun updateProfile(
        @Body request: ProfileRequest,
        @Header(Constants.Authorization) authorization: String?
    ): Response<ProfileDto>

    @GET("/v1/profile")
    suspend fun getProfile(@Header("user-session-id") authorization: String?): Response<ProfileDto>
}