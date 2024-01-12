package com.ladecentro.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.paging.OrdersPagingSource
import com.ladecentro.data.paging.ProductPagingSource
import com.ladecentro.data.paging.StoresPagingSource
import com.ladecentro.data.remote.dto.Product
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.data.remote.dto.Store
import com.ladecentro.domain.repository.LookupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

class GetStoreSearchPagingUseCase @Inject constructor(private val lookupRepository: LookupRepository) {

    operator fun invoke(searchRequest: SearchRequest): Flow<PagingData<Store>> {

        return Pager(
            config = PagingConfig(pageSize = searchRequest.size, maxSize = 100),
            pagingSourceFactory = {
                StoresPagingSource(lookupRepository, searchRequest)
            }
        ).flow
    }
}

class GetProductSearchPagingUseCase @Inject constructor(private val lookupRepository: LookupRepository) {

    operator fun invoke(searchRequest: SearchRequest): Flow<PagingData<Product>> {

        return Pager(
            config = PagingConfig(pageSize = searchRequest.size, maxSize = 100),
            pagingSourceFactory = {
                ProductPagingSource(lookupRepository, searchRequest)
            }
        ).flow
    }
}