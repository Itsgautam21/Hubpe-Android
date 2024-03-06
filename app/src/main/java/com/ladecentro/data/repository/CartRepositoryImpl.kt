package com.ladecentro.data.repository

import android.util.Log
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

    override suspend fun createCart(cartDto: CartDto): CartResponse {

        try {
            Log.i("create cart", gson.toJson(cartDto))
            val response = cartAPI.createCart(token = authToken, cartRequest = cartDto)
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
            Logger.d(cartId)
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
            Logger.d(cartId)
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

    companion object {

        private const val TAG = "CartRepositoryImpl"
        private const val DEBUG_TAG = ">>>> $TAG DEBUG"
        private const val ERROR_TAG = ">>>> $TAG ERROR"
    }
}