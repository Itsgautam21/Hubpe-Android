package com.ladecentro.domain.use_case

import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetAllCartsUseCase @Inject constructor(private val cartRepository: CartRepository) {

    operator fun invoke() = flow {
        emit(Loading())
        val cartResponse = cartRepository.getAllCarts()
        emit(Success(cartResponse))
    }.catch {
        emit(Error(it.message!!))
    }.flowOn(Dispatchers.IO)
}

class CreateCartUseCase @Inject constructor(private val cartRepository: CartRepository) {

    operator fun invoke(cartDto: CartDto) = flow {
        emit(Loading())
        val cartJob = coroutineScope {
            async { cartRepository.createCart(cartDto) }
        }
        val cartId = cartJob.await().cartId
        var cart: CartDto?
        var count = 1
        do {
            delay(count.toLong().times(other = 1000L))
            cart = cartRepository.getCart(cartId)
            emit(Success(cart))
            count++
        } while (cart == null && count < 6)
        if (cart == null) {
            emit(Error(message = "Server isn't Responding"))
        }
    }.catch {
        emit(Error(it.message!!))
    }.flowOn(Dispatchers.IO)
}

class DeleteCartByIdUseCase @Inject constructor(private val cartRepository: CartRepository) {

    operator fun invoke(cartId: String) = flow {
        emit(Loading())
        val cartResponse = cartRepository.deleteCartById(cartId)
        emit(Success(cartResponse))
    }.catch {
        emit(Error(it.message!!))
    }.flowOn(Dispatchers.IO)
}

class DeleteAllCartsUseCase @Inject constructor(private val cartRepository: CartRepository) {

    operator fun invoke() = flow {
        emit(Loading())
        val cartResponse = cartRepository.deleteAllCarts()
        emit(Success(cartResponse))
    }.catch {
        emit(Error(it.message!!))
    }.flowOn(Dispatchers.IO)
}