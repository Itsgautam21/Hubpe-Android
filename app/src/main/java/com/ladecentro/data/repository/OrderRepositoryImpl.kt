package com.ladecentro.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.data.paging.OrdersPagingSource
import com.ladecentro.data.remote.api.OrderAPI
import com.ladecentro.data.remote.dto.orders.UpdateOrderRequest
import com.ladecentro.domain.model.Orders
import com.ladecentro.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import java.util.*
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderAPI: OrderAPI,
    myPreference: MyPreference
) :
    OrderRepository {

    private val authToken = myPreference.getStoresTag(Intents.Token.name)

    override fun getAllOrders(): Flow<PagingData<Orders>> {
        return Pager(
            config = PagingConfig(pageSize = 5, maxSize = 100),
            pagingSourceFactory = {
                OrdersPagingSource(orderAPI, authToken)
            }
        ).flow
    }

    override suspend fun updateOrders(
        updateOrderRequest: UpdateOrderRequest,
        orderId: String
    ): Objects {
        try {
            val response = orderAPI.updateOrder(updateOrderRequest, orderId, authToken)
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception("Something Went Wrong!")
        } catch (e: IOException) {
            throw Exception("Something Went Wrong!")
        } catch (e: Exception) {
            throw Exception("Something Went Wrong!")
        }
    }
}