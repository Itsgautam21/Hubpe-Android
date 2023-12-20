package com.ladecentro.presentation.ui.order.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ladecentro.common.Constants
import com.ladecentro.common.Resource
import com.ladecentro.data.remote.dto.orders.UpdateOrderRequest
import com.ladecentro.domain.model.Orders
import com.ladecentro.domain.use_case.GetOrdersUseCase
import com.ladecentro.domain.use_case.GetUpdateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val ordersUseCase: GetOrdersUseCase,
    private val updateOrderUseCase: GetUpdateOrderUseCase
) : ViewModel() {

    private val _ordersState: MutableStateFlow<PagingData<Orders>> =
        MutableStateFlow(PagingData.empty())
    val ordersState: StateFlow<PagingData<Orders>> get() = _ordersState

    init {
        getAllOrders()
    }

    fun getAllOrders() {
        viewModelScope.launch {
            ordersUseCase.invoke().cachedIn(viewModelScope).collect {
                _ordersState.value = it
            }
        }
    }

    fun updateOrderRating(rating: String, orderId: String) {

        val ratingRequest = UpdateOrderRequest(Constants.RATING, rating)
        viewModelScope.launch {
            updateOrderUseCase.invoke(ratingRequest, orderId).collect {
                when (it) {
                    is Resource.Success -> {
                        getAllOrders()
                    }

                    else -> {}
                }
            }
        }
    }
}