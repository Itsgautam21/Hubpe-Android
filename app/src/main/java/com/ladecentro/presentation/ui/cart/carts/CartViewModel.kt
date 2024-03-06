package com.ladecentro.presentation.ui.cart.carts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ladecentro.common.MyPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val myPreference: MyPreference
) : ViewModel() {

    private var _userCart by mutableStateOf(myPreference.getCartFromLocal())
    val userCart get() = _userCart

    fun setUserCart() {
        _userCart = myPreference.getCartFromLocal()
    }
}