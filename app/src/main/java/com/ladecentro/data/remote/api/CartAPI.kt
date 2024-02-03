package com.ladecentro.data.remote.api

import android.database.Observable
import com.ladecentro.common.Constants
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.data.remote.dto.CartResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Objects

interface CartAPI {

    @POST("/v1/cart")
    suspend fun createCart(
        @Header(Constants.Authorization) token: String?,
        @Query("checkout") checkout: Boolean? = null,
        @Query("cart-id") cartId: String? = null,
        @Body cartRequest: CartDto? = null
    ): Response<CartResponse>

    @GET("/v1/cart/{cartId}")
    suspend fun getCart(
        @Header(Constants.Authorization) token: String?,
        @Path("cartId") cartId: String
    ): Response<CartDto?>

    @DELETE("/v1/cart/{cartId}")
    suspend fun deleteCartById(
        @Header(Constants.Authorization) token: String?,
        @Path("cartId") cartId: String
    ): Response<Any>

    @DELETE("v1/carts")
    suspend fun deleteAllCarts(
        @Header(Constants.Authorization) token: String?
    ): Response<Any>
}