package com.ladecentro.data.repository

import com.ladecentro.data.remote.api.UserAPI
import com.ladecentro.data.remote.dto.UserDto
import com.ladecentro.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userAPI: UserAPI) : UserRepository {

    override suspend fun getUsers(): List<UserDto> {

        return userAPI.getUsers()
    }
}