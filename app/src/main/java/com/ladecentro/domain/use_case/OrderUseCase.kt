package com.ladecentro.domain.use_case

import androidx.paging.PagingData
import com.ladecentro.common.Resource
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.data.remote.dto.orders.Order
import com.ladecentro.data.remote.dto.orders.OrderStatus
import com.ladecentro.data.remote.dto.orders.UpdateOrderRequest
import com.ladecentro.data.remote.dto.orders.toOrderDetails
import com.ladecentro.domain.model.OrderDetails
import com.ladecentro.domain.model.Orders
import com.ladecentro.domain.repository.CartRepository
import com.ladecentro.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Objects
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    operator fun invoke(): Flow<PagingData<Orders>> =
        orderRepository.getAllOrders().flowOn(Dispatchers.IO)
}

class GetUpdateOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    operator fun invoke(request: UpdateOrderRequest, orderId: String): Flow<Resource<Objects>> =
        flow {
            emit(Loading())
            val orders = orderRepository.updateOrders(request, orderId)
            emit(Success(orders))
        }.catch {
            emit(Error(it.message!!))
        }.flowOn(Dispatchers.IO)
}

class GetOrderDetailsUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    operator fun invoke(orderId: String): Flow<Resource<OrderDetails>> =
        flow {
            emit(Loading())
            val orders = orderRepository.getOrderById(orderId)
            emit(Success(orders.toOrderDetails()))
        }.catch {
            emit(Error(it.message!!))
        }.flowOn(Dispatchers.IO)
}

class GetOrderTrackUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    operator fun invoke(orderId: String): Flow<Resource<List<OrderStatus>>> =
        flow {
            emit(Loading())
            val orders = orderRepository.getOrderTrack(orderId)
            emit(Success(orders))
        }.catch {
            emit(Error(it.message!!))
        }.flowOn(Dispatchers.IO)
}

class GetOrderPulling @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
) {
    operator fun invoke(
        cartDto: CartDto,
        checkout: Boolean = true,
        cartId: String
    ): Flow<Resource<Order>> =

        flow {
            emit(Loading())
            val orderJob = coroutineScope {
                async { cartRepository.createCart(cartDto, checkout, cartId) }
            }
            val orderId = orderJob.await().orderId
            var order: Order
            var count = 1
            do {
                delay(count.toLong().times(other = 1000L))
                order = orderRepository.getOrderById(orderId)
                emit(Success(order))
                count++
            } while (!order.ondcResponse && count < 6)
            if (!order.ondcResponse) {
                emit(Error(message = "Server isn't Responding"))
            }
        }.catch {
            emit(Error(it.message!!))
        }.flowOn(Dispatchers.IO)
}
