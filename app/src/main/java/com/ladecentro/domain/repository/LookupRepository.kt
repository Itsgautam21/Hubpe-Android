package com.ladecentro.domain.repository

import com.ladecentro.data.remote.dto.SearchDto
import com.ladecentro.data.remote.dto.SearchRequest

interface LookupRepository {

    suspend fun search(request: SearchRequest): SearchDto
}