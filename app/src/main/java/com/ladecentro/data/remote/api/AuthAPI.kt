package com.ladecentro.data.remote.api

import com.ladecentro.data.remote.dto.LogoutRequest
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.data.remote.dto.SendOtpRequest
import com.ladecentro.data.remote.dto.VerifyOptRequest
import com.ladecentro.data.remote.dto.VerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.Objects

interface AuthAPI {

    @POST("/v1/send-otp")
    suspend fun sendOpt(@Body optRequest: SendOtpRequest): Response<Objects>

    @POST("/v1/register")
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOptRequest): Response<VerifyOtpResponse>

    @POST("/v1/logoff")
    suspend fun logout(
        @Body request: LogoutRequest,
        @Header("user-session-id") authorization: String?
    ): Response<Objects>

    @GET("/v1/profile")
    suspend fun getProfile(@Header("user-session-id") authorization: String?) : Response<ProfileDto>
}