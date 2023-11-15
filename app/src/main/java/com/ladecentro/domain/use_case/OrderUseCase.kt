package com.ladecentro.domain.use_case

import androidx.paging.PagingData
import com.ladecentro.common.Resource
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.orders.UpdateOrderRequest
import com.ladecentro.domain.model.Orders
import com.ladecentro.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    operator fun invoke(): Flow<PagingData<Orders>> = orderRepository.getAllOrders()
}

class GetUpdateOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    operator fun invoke(request: UpdateOrderRequest, orderId: String): Flow<Resource<Objects>> =
        flow {
            try {
                emit(Loading())
                val orders = orderRepository.updateOrders(request, orderId)
                emit(Success(orders))
            } catch (e: HttpException) {
                emit(Error("HTTP ERROR"))
            } catch (e: IOException) {
                emit(Error("IO Error"))
            }
        }
}