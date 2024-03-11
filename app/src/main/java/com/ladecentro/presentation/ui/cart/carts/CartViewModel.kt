package com.ladecentro.presentation.ui.cart.carts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladecentro.common.MyPreference
import com.ladecentro.common.Resource
import com.ladecentro.common.SharedPreference
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.domain.use_case.DeleteAllCartsUseCase
import com.ladecentro.domain.use_case.DeleteCartByIdUseCase
import com.ladecentro.domain.use_case.GetAllCartsUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val myPreference: MyPreference,
    private val getAllCartsUseCase: GetAllCartsUseCase,
    private val deleteCartByIdUseCase: DeleteCartByIdUseCase,
    private val deleteAllCartsUseCase: DeleteAllCartsUseCase
) : ViewModel() {

    private var _userCart: UIStates<List<CartDto>> by mutableStateOf(UIStates())
    val userCart get() = _userCart

    private var _deleteCart by mutableStateOf(UIStates<Any>())

    init {
        getAllCarts()
    }

    fun getCartFromLocal() {
        _userCart = _userCart.copy(content = myPreference.getCartFromLocal())
    }

    private fun mergeCarts(carts: List<CartDto>): List<CartDto> {

        val localCart = myPreference.getCartFromLocal().toMutableList()
        val localCartMap = localCart.groupBy { it.store.id }.mapValues { c -> c.value.getOrNull(0) }
        val uCart = carts.filter { c -> localCartMap[c.store.id] == null && c.ondcResponse == true }
        localCart.addAll(uCart)
        myPreference.setCartToLocal(localCart)
        return localCart
    }

    private fun getAllCarts() {

        viewModelScope.launch {
            getAllCartsUseCase().collect {
                _userCart = when (it) {
                    is Resource.Loading -> UIStates(isLoading = true)
                    is Resource.Success -> UIStates(content = mergeCarts(it.data!!))
                    is Resource.Error -> UIStates(error = it.message)
                }
            }
        }
    }

    private fun deleteCartById(cartId: String) {

        viewModelScope.launch {
            deleteCartByIdUseCase(cartId).collectLatest {
                _deleteCart = when (it) {
                    is Resource.Loading -> UIStates(isLoading = true)
                    is Resource.Success -> UIStates(content = it.data)
                    is Resource.Error -> UIStates(error = it.message)
                }
            }
        }
    }

    private fun deleteAllCartsFromServer() {

        viewModelScope.launch {
            deleteAllCartsUseCase().collect {}
        }
    }

    fun deleteSingleCart(cartDto: CartDto) {

        cartDto.id?.let {
            deleteCartById(it)
        }
        val carts = myPreference.getCartFromLocal().toMutableList()
        carts.removeIf { it.store.id == cartDto.store.id }
        myPreference.setCartToLocal(carts)
        getCartFromLocal()
    }

    fun deleteAllCarts() {
        deleteAllCartsFromServer()
        myPreference.removeStoredTag(SharedPreference.CART.name)
        getCartFromLocal()
    }
}