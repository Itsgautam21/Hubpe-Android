package com.ladecentro.domain.repository

import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.data.remote.dto.CartResponse
import java.util.Objects

interface CartRepository {

    suspend fun getAllCarts(): List<CartDto>

    suspend fun createCart(cartDto: CartDto, checkout: Boolean? = null, cartId: String? = null): CartResponse

    suspend fun getCart(cartId: String): CartDto?

    suspend fun deleteCartById(cartId: String): Any

    suspend fun deleteAllCarts(): Any
}