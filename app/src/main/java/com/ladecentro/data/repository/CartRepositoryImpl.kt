package com.ladecentro.data.repository

import com.google.gson.Gson
import com.ladecentro.common.Constants
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.data.remote.api.CartAPI
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.data.remote.dto.CartResponse
import com.ladecentro.domain.repository.CartRepository
import com.orhanobut.logger.Logger
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartAPI: CartAPI,
    myPreference: MyPreference,
    private val gson: Gson
) : CartRepository {

    private val authToken = myPreference.getStoresTag(Intents.Token.name)

    override suspend fun getAllCarts(): List<CartDto> {

        try {
            Logger.i("get all carts")
            val response = cartAPI.getAllCarts(token = authToken)
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        } catch (e: Exception) {
            Logger.e(e.message!!)
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        }
    }

    override suspend fun createCart(
        cartDto: CartDto,
        checkout: Boolean?,
        cartId: String?
    ): CartResponse {

        try {
            Logger.i("create cart : ${gson.toJson(cartDto)}")
            val response = cartAPI.createCart(
                token = authToken,
                cartRequest = cartDto,
                checkout = checkout,
                cartId = cartId
            )
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        } catch (e: Exception) {
            Logger.e(e.message!!)
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        }
    }

    override suspend fun getCart(cartId: String): CartDto? {

        try {
            Logger.d(">>>> get cart : $cartId")
            val response = cartAPI.getCart(token = authToken, cartId = cartId)
            if (response.isSuccessful) {
                return response.body()
            }
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        } catch (e: Exception) {
            Logger.e(e.message!!)
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        }
    }

    override suspend fun deleteCartById(cartId: String): Any {

        try {
            Logger.d(">>>> delete cart $cartId")
            val response = cartAPI.deleteCartById(token = authToken, cartId = cartId)
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        } catch (e: Exception) {
            Logger.e(e.message!!)
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        }
    }

    override suspend fun deleteAllCarts(): Any {

        try {
            Logger.d("delete all cart")
            val response = cartAPI.deleteAllCarts(token = authToken)
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        } catch (e: Exception) {
            Logger.e(e.message!!)
            throw Exception(Constants.GENERIC_ERROR_MESSAGE)
        }
    }
}