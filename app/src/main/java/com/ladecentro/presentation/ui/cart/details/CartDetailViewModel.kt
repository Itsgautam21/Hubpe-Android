package com.ladecentro.presentation.ui.cart.details

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
import com.ladecentro.data.remote.dto.Contact
import com.ladecentro.data.remote.dto.Count
import com.ladecentro.data.remote.dto.FulfillmentEndRequest
import com.ladecentro.data.remote.dto.FulfillmentRequest
import com.ladecentro.data.remote.dto.Person
import com.ladecentro.data.remote.dto.Product
import com.ladecentro.data.remote.dto.changeLatLong
import com.ladecentro.data.remote.dto.orders.Order
import com.ladecentro.domain.model.Address
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.use_case.CreateCartUseCase
import com.ladecentro.domain.use_case.DeleteCartByIdUseCase
import com.ladecentro.domain.use_case.GetOrderPulling
import com.ladecentro.presentation.common.UIStates
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val myPreference: MyPreference,
    private val cartUseCase: CreateCartUseCase,
    private val getOrderPulling: GetOrderPulling,
    private val deleteCartByIdUseCase: DeleteCartByIdUseCase
) : ViewModel() {

    private val storeId = savedStateHandle[Intents.STORE_ID.name] ?: ""

    var openSheet by mutableStateOf(false)
    var deliveryOptionsSheet by mutableStateOf(false)
    var userAddress by mutableStateOf(myPreference.getProfileFromLocal()?.locations!!)
    var userLocation by mutableStateOf(myPreference.getLocationFromLocal()!!)

    private var _userCart: UIStates<CartDto?> by mutableStateOf(UIStates())
    val userCart: UIStates<CartDto?> get() = _userCart

    private var _deleteCart by mutableStateOf(UIStates<Any>())
    val deleteCart: UIStates<Any> get() = _deleteCart

    private var _userOrder: UIStates<Order> by mutableStateOf(UIStates())
    val userOrder: UIStates<Order> get() = _userOrder

    var selectFulfillment by mutableStateOf(userCart.content?.fulfillment?.get(0))

    init {
        _userCart = UIStates(content = getCartFromLocal())
        createCartForChangeLocation()
    }

    fun getAddressFromLocal() {
        userAddress = myPreference.getProfileFromLocal()?.locations!!
    }

    private fun getCartFromLocal() = myPreference.getCartFromLocal().find { it.store.id == storeId }

    private suspend fun createCart() {

        getCartRequest()?.let {
            cartUseCase(it).collect { resource ->
                _userCart = when (resource) {
                    is Loading -> userCart.copy(isLoading = true)
                    is Error -> userCart.copy(error = resource.message, isLoading = false)
                    is Success -> {
                        resource.data?.let { cart ->
                            selectFulfillment = cart.fulfillment?.getOrNull(0)
                            val list = myPreference.getCartFromLocal().toMutableList()
                            getCartFromLocal()?.let {
                                list.removeIf { c -> c.store.id == it.store.id }
                            }
                            list.add(cart)
                            myPreference.setCartToLocal(list)
                            UIStates(content = cart, error = cart.error?.type)
                        } ?: userCart
                    }
                }
            }
        }
    }

    fun createCartServer() {
        viewModelScope.launch {
            createCart()
        }
    }

    private fun createCartForChangeLocation() {

        viewModelScope.launch {
            snapshotFlow { userLocation }.collectLatest { location ->
                if (location.mobileNumber != null) {
                    selectAddress(location)
                    createCart()
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
            if (userLocation.mobileNumber != null) {
                createCart()
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
            // TODO ( handle 0 count products)
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
                            location = LocationRequest(
                                gps = userLocation.gps?.changeLatLong(),
                                address = Address(areaCode = userLocation.address?.areaCode)
                            )
                        )
                    )
                )
            )
        } catch (e: Exception) {
            Logger.e(e.message!!)
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
            updateCartForQuantity()
        }
    }

    private fun getOrderRequest(): CartDto? {

        try {
            val phone = userLocation.mobileNumber!!
            val name = userLocation.address?.name!!
            return userCart.content?.copy(
                quote = null,
                fulfillment = listOf(
                    selectFulfillment!!.copy(
                        end = FulfillmentEndRequest(
                            location = userLocation.copy(
                                id = null,
                                descriptor = null,
                                city = null,
                                country = null,
                                gps = userLocation.gps?.changeLatLong()
                            ),
                            contact = Contact(phone),
                            person = Person(name)
                        )
                    )
                )
            )
        } catch (e: Exception) {
            Logger.e(e.message!!)
            return null
        }
    }

    fun createOrder() {

        getOrderRequest()?.let { cart ->
            viewModelScope.launch {
                cart.id?.let { id ->
                    getOrderPulling(cart, true, id).collect {
                        when (it) {
                            is Loading -> _userOrder = userOrder.copy(isLoading = true)
                            is Error -> _userOrder =
                                userOrder.copy(error = it.message, isLoading = false)

                            is Success -> {
                                it.data?.let { order ->
                                    if (order.ondcResponse) {
                                        _userOrder =
                                            userOrder.copy(isLoading = false, content = order)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}