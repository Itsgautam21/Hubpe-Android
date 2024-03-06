package com.ladecentro.data.repository

import com.google.gson.Gson
import com.ladecentro.common.Constants.GENERIC_ERROR_MESSAGE
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.data.remote.api.LookupAPI
import com.ladecentro.data.remote.dto.SearchDto
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.data.remote.dto.Store
import com.ladecentro.domain.repository.LookupRepository
import com.orhanobut.logger.Logger
import javax.inject.Inject

class LookupRepositoryImpl @Inject constructor(
    private val lookupAPI: LookupAPI,
    private val gson: Gson,
    myPreference: MyPreference
) : LookupRepository {

    private val authToken = myPreference.getStoresTag(Intents.Token.name)

    override suspend fun search(request: SearchRequest): SearchDto {
        try {
            Logger.i(gson.toJson(request))
            val response = lookupAPI.search(
                authToken,
                request.term,
                request.location,
                request.size,
                request.page,
                request.sector,
                request.isPromoted,
                request.expectedEntity,
                request.storeId,
                request.category
            )
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception(GENERIC_ERROR_MESSAGE)
        } catch (e: Exception) {
            throw Exception(GENERIC_ERROR_MESSAGE)
        }
    }

    override suspend fun getStore(storeId: String): Store {
        try {
            val response = lookupAPI.getStore(
                authToken, storeId
            )
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception(GENERIC_ERROR_MESSAGE)
        } catch (e: Exception) {
            throw Exception(GENERIC_ERROR_MESSAGE)
        }
    }
}