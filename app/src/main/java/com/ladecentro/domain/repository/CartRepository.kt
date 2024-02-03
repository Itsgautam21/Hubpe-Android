package com.ladecentro.domain.repository

import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.data.remote.dto.CartResponse
import java.util.Objects

interface CartRepository {

    suspend fun createCart(cartDto: CartDto): CartResponse

    suspend fun getCart(cartId: String): CartDto?

    suspend fun deleteCartById(cartId: String): Any

    suspend fun deleteAllCarts(): Any
}