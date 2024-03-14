package com.ladecentro.data.remote.api

import com.ladecentro.common.Constants.AUTHORIZATION
import com.ladecentro.data.remote.dto.SearchDto
import com.ladecentro.data.remote.dto.Store
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface LookupAPI {

    @GET("/v1/search")
    suspend fun search(
        @Header(AUTHORIZATION) token: String?,
        @Query("term") term: String?,
        @Query("location") location: String?,
        @Query("size") size: Int?,
        @Query("page") page: Int?,
        @Query("sector") sector: String?,
        @Query("is_promoted") isPromoted: Boolean?,
        @Query("expected_entity") expectedEntity: String?,
        @Query("store_id") storeId: String?,
        @Query("category") category: List<String>?,
    ): Response<SearchDto>

    @GET("/v1/stores/{storeId}")
    suspend fun getStore(
        @Header("token") token: String?,
        @Path("storeId") storeId: String
    ): Response<Store>
}