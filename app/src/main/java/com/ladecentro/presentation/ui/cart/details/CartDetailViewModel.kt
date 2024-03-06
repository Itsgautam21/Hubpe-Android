package com.ladecentro.presentation.ui.cart.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.BillingRequest
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.data.remote.dto.Count
import com.ladecentro.data.remote.dto.FulfillmentEndRequest
import com.ladecentro.data.remote.dto.FulfillmentRequest
import com.ladecentro.data.remote.dto.Product
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.use_case.CreateCartUseCase
import com.ladecentro.domain.use_case.DeleteCartByIdUseCase
import com.ladecentro.domain.use_case.GetCartUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val myPreference: MyPreference,
    private val cartUseCase: CreateCartUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val deleteCartByIdUseCase: DeleteCartByIdUseCase
) : ViewModel() {

    private val storeId = savedStateHandle[Intents.STORE_ID.name] ?: ""

    var openSheet by mutableStateOf(false)
    var deliveryOptionsSheet by mutableStateOf(false)
    var userAddress by mutableStateOf(myPreference.getProfileFromLocal()?.locations!!)
    var userLocation by mutableStateOf(myPreference.getLocationFromLocal()!!)
    private var incrementState by mutableStateOf(false)

    private var _userCart: UIStates<CartDto?> by mutableStateOf(UIStates())
    val userCart: UIStates<CartDto?> get() = _userCart

    private var _deleteCart by mutableStateOf(UIStates<Any>())
    val deleteCart: UIStates<Any> get() = _deleteCart

    var selectFulfillment by mutableStateOf(userCart.content?.fulfillment?.get(0))

    init {
        _userCart = UIStates(content = getCartFromLocal())
        createCart()
        updateCartForQuantity()
    }

    fun getAddressFromLocal() {
        userAddress = myPreference.getProfileFromLocal()?.locations!!
    }

    private fun getCartFromLocal() = myPreference.getCartFromLocal().find { it.store.id == storeId }

    private fun createCart() {

        viewModelScope.launch {
            snapshotFlow { userLocation }.collectLatest { location ->
                if (location.mobileNumber != null) {
                    selectAddress(location)
                    getCartRequest()?.let {
                        cartUseCase(it).collect { resource ->
                            when (resource) {
                                is Loading -> {
                                    _userCart = userCart.copy(isLoading = true)
                                }

                                is Success -> {
                                    Log.d(DEBUG_TAG, resource.data!!.cartId)
                                    getCartDetails(resource.data.cartId)
                                }

                                is Error -> {
                                    _userCart = userCart.copy(error = resource.message, isLoading = false)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun selectAddress(location: LocationRequest) {
        userAddress = userAddress
            .map { add -> add.copy(selected = false) }
            .map { add -> if (location.id == add.id) add.copy(selected = true) else add }

    }

    private fun updateCartForQuantity() {

        viewModelScope.launch {
            snapshotFlow { incrementState }.collectLatest {
                if (userLocation.mobileNumber != null) {
                    getCartRequest()?.let {
                        cartUseCase(it).collect { resource ->
                            when (resource) {
                                is Loading -> {}
                                is Success -> {
                                    Log.d(DEBUG_TAG, resource.data!!.cartId)
                                    getCartDetails(resource.data.cartId)
                                }

                                is Error -> {}
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getCartDetails(cartId: String) {

        viewModelScope.launch {
            getCartUseCase(cartId).collect { resource ->
                when (resource) {
                    is Loading -> {
                        _userCart = userCart.copy(isLoading = true)
                    }

                    is Success -> {
                        resource.data?.let { cart ->
                            selectFulfillment = cart.fulfillment?.getOrNull(0)
                            _userCart = UIStates(content = cart, error = cart.error?.type)
                            val list = myPreference.getCartFromLocal().toMutableList()
                            getCartFromLocal()?.let {
                                list.removeIf { c -> c.store.id == it.store.id }
                            }
                            list.add(cart)
                            myPreference.setCartToLocal(list)
                        }
                    }

                    is Error -> {
                        _userCart = userCart.copy(error = resource.message, isLoading = false)
                    }
                }
            }
        }
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

    private fun getCartRequest(): CartDto? {

        try {
            val phone = userLocation.mobileNumber!!
            val name = userLocation.address?.name!!
//            if (userCart.content?.items?.find { item -> item.quantity.selected.count <= 0 } != null) {
//                return null
//            }
            return userCart.content?.copy(
                billing = BillingRequest(
                    name = name,
                    phone = phone,
                    address = userLocation.address!!
                ),
                fulfillment = listOf(
                    FulfillmentRequest(
                        end = FulfillmentEndRequest(
                            location = userLocation.copy(
                                descriptor = null,
                                city = null,
                                country = null
                            )
                        )
                    )
                )
            )
        } catch (e: Exception) {
            Log.e(ERROR_TAG, e.message!!)
            return null
        }
    }

    fun updateCart(product: Product, operation: String) {

        getCartFromLocal()?.let { cart ->
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
            val carts = myPreference.getCartFromLocal().toMutableList()
            carts.removeIf { it.store.id == cart.store.id }
            _userCart = UIStates(
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
            incrementState = !incrementState
        }
    }

    companion object {

        private const val TAG = "CartDetailViewModel"
        private const val DEBUG_TAG = ">>>> $TAG DEBUG"
        private const val ERROR_TAG = ">>>> $TAG ERROR"
    }
}