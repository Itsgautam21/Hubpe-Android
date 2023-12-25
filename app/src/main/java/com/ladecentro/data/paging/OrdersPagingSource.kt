package com.ladecentro.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ladecentro.data.remote.api.OrderAPI
import com.ladecentro.data.remote.dto.orders.toOrders
import com.ladecentro.domain.model.Orders

class OrdersPagingSource(
    private val orderAPI: OrderAPI, private val authToken: String?
) : PagingSource<Int, Orders>() {

    override fun getRefreshKey(state: PagingState<Int, Orders>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Orders> {
        return try {
            val position = params.key ?: 1
            val response = orderAPI.getOrders(position.minus(1), 20, authToken)
            val orders = response.body()
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = orders!!.toOrders(),
                    prevKey = if (position == 1) null else position.minus(1),
                    nextKey = if (orders.orders.isEmpty()) null else position.plus(1)
                )
            } else {
                LoadResult.Error(Exception("Something went wrong!"))
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}