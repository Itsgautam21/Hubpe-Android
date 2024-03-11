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
import com.ladecentro.data.remote.dto.toProductDetail
import com.ladecentro.domain.model.ItemDetails
import com.ladecentro.domain.model.ProfileRequest
import com.ladecentro.domain.use_case.DeleteCartByIdUseCase
import com.ladecentro.domain.use_case.GetSearchUseCase
import com.ladecentro.domain.use_case.GetStoreUseCase
import com.ladecentro.domain.use_case.GetUpdateProfileUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val storeUseCase: GetStoreUseCase,
    private val searchUseCase: GetSearchUseCase,
    private val myPreference: MyPreference,
    private val deleteCartByIdUseCase: DeleteCartByIdUseCase,
    private val getUpdateProfileUseCase: GetUpdateProfileUseCase
) : ViewModel() {

    private var _store by mutableStateOf(UIStates<Store>())
    val store: UIStates<Store> get() = _store

    private var _products by mutableStateOf(UIStates<List<ItemDetails>>())
    val products: UIStates<List<ItemDetails>> get() = _products

    private var _productDetails by mutableStateOf(UIStates<List<Product>>())
    private val productDetail: UIStates<List<Product>> get() = _productDetails

    private var _cartState by mutableStateOf(UIStates<CartDto?>())
    val cartState: UIStates<CartDto?> get() = _cartState

    private var _favourite by mutableStateOf(false)
    val favourite get() = _favourite

    private var _deleteCart by mutableStateOf(UIStates<Any>())
    val storeId: String = savedStateHandle[Intents.STORE_ID.name] ?: ""

    init {
        getStores()
        getCartFromLocal()
        getStoreProducts()
        saveRecentlyViewed()
        getFavouriteFromLocal()
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
                    is Success -> {
                        _productDetails = UIStates(content = it.data?.products)
                        val productList = it.data?.products?.map { pr ->
                            pr.toProductDetail().copy(quantity = getItemCount(pr.id))
                        }
                        UIStates(content = productList)
                    }

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

    private fun getFavouriteFromLocal() {
        _favourite = myPreference.getProfileFromLocal()!!.favourites.stream()
            .anyMatch { f -> f.id == storeId }
    }

    private fun deleteCartById(cartId: String) {

        viewModelScope.launch {
            deleteCartByIdUseCase(cartId).collectLatest {
                _deleteCart = when (it) {
                    is Loading -> UIStates(isLoading = true)
                    is Success -> UIStates(content = it.data)
                    is Error -> UIStates(error = it.message)
                }
            }
        }
    }

    fun getItemCount(itemId: String): Int {

        cartState.content?.let { cart ->
            val product = cart.items.find { it.id == itemId }
            product?.let {
                return it.quantity.selected.count
            }
            return 0
        }
        return 0
    }

    fun updateQuantityForItem(item: List<ItemDetails>) {
        val itemMap = item.groupBy { p -> p.id }.mapValues { p -> p.value[0] }
        _products = _products.copy(content =
        _products.content?.map { p ->
            if (p.id == itemMap[p.id]?.id) p.copy(
                quantity = itemMap[p.id]?.quantity ?: 0
            ) else p.copy(quantity = 0)
        })
    }

    fun createCart(itemDetails: ItemDetails, quantity: Int) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val product = productDetail.content?.find { pd -> pd.id == itemDetails.id }!!
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
                    cart.items.removeIf { it.id == product.id }
                    if (quantity > 0) {
                        product.quantity.selected = Count(quantity)
                        cart.items.add(product)
                    }
                }
                updateQuantityForItem(cart.items.map { it.toProductDetail() })
                updateCart(cart)
            }
        }
    }

    private fun updateCart(cart: CartDto) {

        val carts = myPreference.getCartFromLocal().toMutableList()
        carts.removeIf { it.store.id == cart.store.id }
        _cartState = _cartState.copy(
            content = if (cart.items.isNotEmpty()) {
                carts.add(cart)
                cart
            } else {
                if (cart.id != null) {
                    deleteCartById(cart.id)
                } else {
                    _deleteCart = UIStates(content = "")
                }
                null
            }
        )
        myPreference.setCartToLocal(carts)
    }

    private fun saveRecentlyViewed() {

        viewModelScope.launch {
            val request = ProfileRequest(
                type = listOf("HISTORY"),
                operation = "ADD",
                history = listOf(storeId)
            )
            getUpdateProfileUseCase(request).collect {
                when (it) {
                    is Error -> {}
                    is Loading -> {}
                    is Success -> {
                        it.data?.let { profile ->
                            myPreference.setProfileToLocal(profile)
                        }
                    }
                }
            }
        }
    }

    fun saveFavourites() {

        _favourite = !favourite
        viewModelScope.launch {
            val request = ProfileRequest(
                type = listOf("FAVOURITES"),
                operation = if (favourite) "ADD" else "REMOVE",
                favourites = listOf(storeId)
            )
            getUpdateProfileUseCase(request).collectLatest {
                when (it) {
                    is Error -> {}
                    is Loading -> {}
                    is Success -> {
                        it.data?.let { profile ->
                            myPreference.setProfileToLocal(profile)
                        }
                    }
                }
            }
        }
    }
}