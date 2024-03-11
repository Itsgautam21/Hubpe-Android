package com.ladecentro.presentation.ui.stores.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.data.remote.dto.CartDto
import com.ladecentro.data.remote.dto.Product
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.domain.use_case.GetProductSearchPagingUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val myPreference: MyPreference,
    private val getProductSearchPagingUseCase: GetProductSearchPagingUseCase,
) : ViewModel() {

    private var _cartState by mutableStateOf(UIStates<CartDto?>())
    val cartState: UIStates<CartDto?> get() = _cartState

    private val _productSearch: MutableStateFlow<PagingData<Product>> =
        MutableStateFlow(PagingData.empty())
    val productSearch = _productSearch.asStateFlow()

    var searchText by mutableStateOf("")
    val storeId: String = savedStateHandle[Intents.STORE_ID.name] ?: ""

    init {
        getCartFromLocal()
        getProductByCategory()
    }

    private fun getProductByCategory() {

        viewModelScope.launch {
            snapshotFlow { searchText }.collectLatest { text ->
                if (text.length > 2) {
                    val search = SearchRequest(
                        term = text,
                        size = 20,
                        expectedEntity = "product",
                        storeId = storeId
                    )
                    getProductSearchPagingUseCase(search).cachedIn(viewModelScope)
                        .collectLatest {
                            _productSearch.emit(it)
                        }
                }
            }
        }
    }

    private fun getCartFromLocal(): CartDto? {
        val cart = myPreference.getCartFromLocal().find { it.store.id == storeId }
        _cartState = UIStates(content = cart)
        return cart
    }
}