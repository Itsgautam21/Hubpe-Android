package com.ladecentro.presentation.ui.order.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladecentro.common.Constants
import com.ladecentro.common.Intents
import com.ladecentro.common.Resource
import com.ladecentro.data.remote.dto.orders.OrderStatus
import com.ladecentro.data.remote.dto.orders.UpdateOrderRequest
import com.ladecentro.domain.model.OrderDetails
import com.ladecentro.domain.use_case.GetOrderDetailsUseCase
import com.ladecentro.domain.use_case.GetOrderTrackUseCase
import com.ladecentro.domain.use_case.GetUpdateOrderUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val getOrderTrackUseCase: GetOrderTrackUseCase,
    private val updateOrderUseCase: GetUpdateOrderUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: String = savedStateHandle[Intents.ORDER_ID.name] ?: ""

    private val _orderDetails = MutableStateFlow<UIStates<OrderDetails>>(UIStates())
    val orderDetails: StateFlow<UIStates<OrderDetails>> get() = _orderDetails

    private val _orderTrack = MutableStateFlow<UIStates<List<OrderStatus>>>(UIStates())
    val orderTrack: StateFlow<UIStates<List<OrderStatus>>> get() = _orderTrack

    init {
        getOrder()
        getOrderTrack()
    }

    fun getOrder() {
        viewModelScope.launch {
            getOrderDetailsUseCase(id).collect {
                when (it) {
                    is Resource.Loading -> {
                        _orderDetails.emit(UIStates(isLoading = true))
                    }

                    is Resource.Success -> {
                        _orderDetails.emit(UIStates(isLoading = false, content = it.data))
                    }

                    is Resource.Error -> {
                        _orderDetails.emit(UIStates(isLoading = false, error = it.message))
                    }
                }
            }
        }
    }

    fun getOrderTrack() {

        viewModelScope.launch {
            getOrderTrackUseCase(id).collect {
                when (it) {
                    is Resource.Loading -> {
                        _orderTrack.emit(UIStates(isLoading = true))
                    }

                    is Resource.Success -> {
                        _orderTrack.emit(UIStates(isLoading = false, content = it.data))
                    }

                    is Resource.Error -> {
                        _orderTrack.emit(UIStates(isLoading = false, error = it.message))
                    }
                }
            }
        }
    }

    fun updateOrderRating(rating: String, orderId: String) {

        val ratingRequest = UpdateOrderRequest(Constants.RATING, rating)
        viewModelScope.launch {
            updateOrderUseCase.invoke(ratingRequest, orderId).collect {
                when (it) {
                    is Resource.Success -> {
                        getOrder()
                    }

                    else -> {}
                }
            }
        }
    }
}