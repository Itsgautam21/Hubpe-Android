package com.ladecentro.data.repository

import com.ladecentro.common.Constants.GENERIC_ERROR_MESSAGE
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.data.remote.api.LookupAPI
import com.ladecentro.data.remote.dto.SearchDto
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.data.remote.dto.Store
import com.ladecentro.domain.repository.LookupRepository
import javax.inject.Inject

class LookupRepositoryImpl @Inject constructor(
    private val lookupAPI: LookupAPI,
    myPreference: MyPreference
) : LookupRepository {

    private val authToken = myPreference.getStoresTag(Intents.Token.name)

    override suspend fun search(request: SearchRequest): SearchDto {
        try {
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