package com.ladecentro.data.remote.api

import com.ladecentro.common.Constants.Authorization
import com.ladecentro.data.remote.dto.SearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface LookupAPI {

    @GET("/v1/search")
    suspend fun search(
        @Header(Authorization) token: String?,
        @Query("term") term: String?,
        @Query("location") location: String?,
        @Query("size") size: Int?,
        @Query("page") page: Int?
    ): Response<SearchDto>
}