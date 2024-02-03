package com.ladecentro.domain.repository

import com.ladecentro.data.remote.dto.SearchDto
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.data.remote.dto.Store

interface LookupRepository {

    suspend fun search(request: SearchRequest): SearchDto

    suspend fun getStore(storeId: String): Store
}