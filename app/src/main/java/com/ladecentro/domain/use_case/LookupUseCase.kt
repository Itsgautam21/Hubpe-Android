package com.ladecentro.domain.use_case

import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.domain.repository.LookupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(private val lookupRepository: LookupRepository) {

    operator fun invoke(searchRequest: SearchRequest) = flow {
        emit(Loading())
        val search = lookupRepository.search(searchRequest)
        emit(Success(search))
    }.catch {
        emit(Error(it.message!!))
    }.flowOn(Dispatchers.IO)
}