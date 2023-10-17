package com.ladecentro.domain.repository

import com.ladecentro.data.remote.dto.UserDto

interface UserRepository {

    suspend fun getUsers() : List<UserDto>
}