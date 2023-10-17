package com.ladecentro.data.remote.api

import com.ladecentro.data.remote.dto.UserDto
import retrofit2.http.GET

interface UserAPI {

    @GET("/users")
    suspend fun getUsers() : List<UserDto>
}