package com.ladecentro.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ladecentro.common.Constants
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.data.paging.OrdersPagingSource
import com.ladecentro.data.remote.api.OrderAPI
import com.ladecentro.data.remote.dto.orders.Order
import com.ladecentro.data.remote.dto.orders.OrderStatus
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
) : OrderRepository {

    private val authToken = myPreference.getStoresTag(Intents.Token.name)

    override fun getAllOrders(): Flow<PagingData<Orders>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
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
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        } catch (e: IOException) {
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        } catch (e: Exception) {
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        }
    }

    override suspend fun getOrderById(orderId: String): Order {

        try {
            val response = orderAPI.getOrderById(orderId, authToken)
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        } catch (e: IOException) {
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        } catch (e: Exception) {
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        }
    }

    override suspend fun getOrderTrack(orderId: String): List<OrderStatus> {

        try {
            val response = orderAPI.getOrderTrack(orderId, authToken)
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        } catch (e: IOException) {
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        } catch (e: Exception) {
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        }
    }
}