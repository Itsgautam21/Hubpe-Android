package com.ladecentro.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ladecentro.data.remote.dto.Product
import com.ladecentro.data.remote.dto.SearchDto
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.domain.repository.LookupRepository
import javax.inject.Inject

class ProductPagingSource @Inject constructor(
    private val lookupRepository: LookupRepository,
    private val request: SearchRequest
) : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {

        return try {
            val position = params.key ?: 1
            request.page = position.minus(1)
            val searchResult: SearchDto = lookupRepository.search(request)
            LoadResult.Page(
                data = searchResult.products,
                prevKey = if (position == 1) null else position.minus(1),
                nextKey = if (searchResult.products.isEmpty()) null else position.plus(1)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}