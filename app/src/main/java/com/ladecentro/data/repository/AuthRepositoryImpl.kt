package com.ladecentro.data.repository

import android.util.Log
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.data.remote.api.AuthAPI
import com.ladecentro.data.remote.dto.LogoutRequest
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.data.remote.dto.SendOtpRequest
import com.ladecentro.data.remote.dto.UpdateProfileRequest
import com.ladecentro.data.remote.dto.VerifyOptRequest
import com.ladecentro.data.remote.dto.VerifyOtpResponse
import com.ladecentro.domain.repository.AuthRepository
import java.io.IOException
import java.util.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authAPI: AuthAPI, myPreference: MyPreference
) : AuthRepository {

    private val authToken = myPreference.getStoresTag(Intents.Token.name)

    override suspend fun sendOtp(sendOtpRequest: SendOtpRequest): Objects? {

        try {
            val response = authAPI.sendOpt(sendOtpRequest)
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception("Something Went Wrong!")
        } catch (e: IOException) {
            throw Exception("Something Went Wrong!")
        } catch (e: Exception) {
            throw Exception("Something Went Wrong!")
        }
    }

    override suspend fun verifyOpt(request: VerifyOptRequest): VerifyOtpResponse {

        try {
            val response = authAPI.verifyOtp(request)
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception("Something Went Wrong!")
        } catch (e: IOException) {
            throw Exception("Something Went Wrong!")
        } catch (e: Exception) {
            throw Exception("Something Went Wrong!")
        }
    }

    override suspend fun logoutUser(request: LogoutRequest): Objects? {

        try {
            val response = authAPI.logout(request, authToken)
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception("Something Went Wrong!")
        } catch (e: IOException) {
            throw Exception("Something Went Wrong!")
        } catch (e: Exception) {
            throw Exception("Something Went Wrong!")
        }
    }

    override suspend fun userProfile(): ProfileDto {

        try {
            val response = authAPI.getProfile(authToken)
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception("Something Went Wrong!")
        } catch (e: IOException) {
            throw Exception("Something Went Wrong!")
        } catch (e: Exception) {
            throw Exception("Something Went Wrong!")
        }
    }

    override suspend fun updateUser(request: UpdateProfileRequest): ProfileDto {
        try {
            val response = authAPI.updateProfile(request, authToken)
            Log.d("response body", response.body()?.toString() ?: "")
            if (response.isSuccessful) {
                return response.body()!!
            }
            Log.d("error body", response.errorBody()?.toString() ?: "")
            throw Exception("Something Went Wrong!")
        } catch (e: IOException) {
            throw Exception("Something Went Wrong!")
        } catch (e: Exception) {
            throw Exception("Something Went Wrong!")
        }
    }
}