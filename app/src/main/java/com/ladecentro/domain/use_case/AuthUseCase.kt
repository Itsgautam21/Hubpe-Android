package com.ladecentro.domain.use_case

import com.ladecentro.common.Resource
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.LogoutRequest
import com.ladecentro.data.remote.dto.ProfileDto
import com.ladecentro.data.remote.dto.SendOtpRequest
import com.ladecentro.data.remote.dto.UpdateProfileRequest
import com.ladecentro.data.remote.dto.VerifyOptRequest
import com.ladecentro.data.remote.dto.VerifyOtpResponse
import com.ladecentro.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class GetAuthUseCase @Inject constructor(private val authRepository: AuthRepository) {

    operator fun invoke(sendOtpRequest: SendOtpRequest): Flow<Resource<Objects>> = flow {
        emit(Loading())
        val user = authRepository.sendOtp(sendOtpRequest)
        emit(Success(user!!))
    }.catch {
        emit(Error(it.message!!))
    }.flowOn(Dispatchers.IO)
}

class GetVerifyUseCase @Inject constructor(private val authRepository: AuthRepository) {

    operator fun invoke(request: VerifyOptRequest): Flow<Resource<VerifyOtpResponse>> = flow {
        emit(Loading())
        val user = authRepository.verifyOpt(request)
        emit(Success(user))
    }.catch {
        emit(Error(it.message!!))
    }.flowOn(Dispatchers.IO)
}

class GetLogoutUseCase @Inject constructor(private val authRepository: AuthRepository) {

    operator fun invoke(request: LogoutRequest): Flow<Resource<Objects?>> = flow {
        emit(Loading())
        val user = authRepository.logoutUser(request)
        emit(Success(user))
    }.catch {
        emit(Error(it.message!!))
    }.flowOn(Dispatchers.IO)
}

class GetProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(): Flow<Resource<ProfileDto>> = flow {
        emit(Loading())
        val user = authRepository.userProfile()
        emit(Success(user))
    }.catch {
        emit(Error(it.message!!))
    }.flowOn(Dispatchers.IO)
}

class GetUpdateProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(request: UpdateProfileRequest): Flow<Resource<ProfileDto>> = flow {
        emit(Loading())
        val user = authRepository.updateUser(request)
        emit(Success(user))
    }.catch {
        emit(Error(it.message!!))
    }.flowOn(Dispatchers.IO)
}