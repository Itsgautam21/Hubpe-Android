package com.ladecentro.presentation.ui.stores.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.data.remote.dto.Count
import com.ladecentro.data.remote.dto.Product
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.data.remote.dto.Store
import com.ladecentro.domain.use_case.GetSearchUseCase
import com.ladecentro.domain.use_case.GetStoreUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeUseCase: GetStoreUseCase,
    private val searchUseCase: GetSearchUseCase,
    private val myPreference: MyPreference,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _store by mutableStateOf(UIStates<Store>())
    val store: UIStates<Store> get() = _store

    private var _products by mutableStateOf(UIStates<List<Product>>())
    val products: UIStates<List<Product>> get() = _products

    private var _cartState by mutableStateOf(UIStates<CartDto?>())
    val cartState: UIStates<CartDto?> get() = _cartState

    val storeId: String = savedStateHandle[Intents.STORE_ID.name] ?: ""

    init {
        getCartFromLocal()
        getStores()
        getStoreProducts()
    }

    private fun getStores() {

        val storeId = storeId
        viewModelScope.launch {
            storeUseCase(storeId).collect {
                _store = when (it) {
                    is Loading -> UIStates(isLoading = true)
                    is Success -> UIStates(content = it.data)
                    is Error -> UIStates(error = it.message)
                }
            }
        }
    }

    private fun getStoreProducts() {

        val request = SearchRequest(
            page = 0,
            size = 200,
            storeId = storeId,
            expectedEntity = "product"
        )
        viewModelScope.launch {
            searchUseCase(request).collect {
                _products = when (it) {
                    is Loading -> UIStates(isLoading = true)
                    is Success -> UIStates(content = it.data?.products)
                    is Error -> UIStates(error = it.message)
                }
            }
        }
    }

    fun getCartFromLocal(): CartDto? {
        val cart = myPreference.getCartFromLocal().find { it.store.id == storeId }
        _cartState = UIStates(content = cart)
        return cart
    }

    fun getItemCount(cart: CartDto, itemId: String): Int {

        val product = cart.items.find { it.id == itemId }
        product?.let {
            return it.quantity.selected.count
        }
        return 0
    }

    fun createCart(product: Product, operation: String) {

        var cart = getCartFromLocal()
        if (cart == null) {
            product.quantity.selected = Count(1)
            cart = CartDto(
                operation = "ITEM",
                type = "REGULAR",
                store = store.content!!,
                items = mutableListOf(product)
            )
        } else {
            val item = cart.items.find { it.id == product.id }
            item?.let {
                var count = it.quantity.selected.count
                if (operation == "+") {
                    count = count.plus(1)
                } else if (operation == "-") {
                    count = count.minus(1)
                }
                if (count > 0) {
                    it.quantity.selected = Count(count)
                } else {
                    val items = cart.items
                    items.remove(it)
                }
            }
            if (item == null) {
                product.quantity.selected = Count(1)
                cart.items.add(product)
            }
        }
        val carts = myPreference.getCartFromLocal().toMutableList()
        carts.removeIf { it.store.id == cart.store.id }
        _cartState = _cartState.copy(
            content = if (cart.items.isNotEmpty()) {
                carts.add(cart)
                cart
            } else {
                null
            }
        )
        myPreference.setCartToLocal(carts)
    }
}