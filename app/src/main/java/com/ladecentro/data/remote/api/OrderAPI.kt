package com.ladecentro.data.remote.api

import com.ladecentro.common.Constants
import com.ladecentro.data.remote.dto.orders.Order
import com.ladecentro.data.remote.dto.orders.OrderStatus
import com.ladecentro.data.remote.dto.orders.OrdersDTO
import com.ladecentro.data.remote.dto.orders.UpdateOrderRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Objects

interface OrderAPI {

    @GET("/v1/orders")
    suspend fun getOrders(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header(Constants.Authorization) authorization: String?
    ): Response<OrdersDTO>

    @POST("/v1/orders/{id}")
    suspend fun updateOrder(
        @Body request: UpdateOrderRequest,
        @Path("id") orderId: String,
        @Header(Constants.Authorization) authorization: String?
    ): Response<Objects>

    @GET("/v1/orders/{id}")
    suspend fun getOrderById(
        @Path("id") orderId: String,
        @Header(Constants.Authorization) authorization: String?
    ): Response<Order>

    @GET("/v1/order-track/{id}")
    suspend fun getOrderTrack(
        @Path("id") orderId: String,
        @Header(Constants.Authorization) authorization: String?
    ): Response<List<OrderStatus>>
}