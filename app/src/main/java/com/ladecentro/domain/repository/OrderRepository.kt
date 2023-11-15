package com.ladecentro.domain.repository

import androidx.paging.PagingData
import com.ladecentro.data.remote.dto.orders.UpdateOrderRequest
import com.ladecentro.domain.model.Orders
import kotlinx.coroutines.flow.Flow
import java.util.*

interface OrderRepository {

    fun getAllOrders(): Flow<PagingData<Orders>>

    suspend fun updateOrders(updateOrderRequest: UpdateOrderRequest, orderId: String): Objects
}