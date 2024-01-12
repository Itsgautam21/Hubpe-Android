package com.ladecentro.presentation.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ladecentro.common.MyPreference
import com.ladecentro.common.SharedPreference
import com.ladecentro.common.SharedPreference.SEARCH
import com.ladecentro.data.remote.dto.Product
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.data.remote.dto.Store
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.use_case.GetProductSearchPagingUseCase
import com.ladecentro.domain.use_case.GetStoreSearchPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val myPreference: MyPreference,
    private val getStoreSearchPagingUseCase: GetStoreSearchPagingUseCase,
    private val getProductSearchPagingUseCase: GetProductSearchPagingUseCase,
    private val gson: Gson
) : ViewModel() {

    private val _storeSearch: MutableStateFlow<PagingData<Store>> =
        MutableStateFlow(PagingData.empty())
    val storeSearch = _storeSearch.asStateFlow()

    private val _productSearch: MutableStateFlow<PagingData<Product>> =
        MutableStateFlow(PagingData.empty())
    val productSearch = _productSearch.asStateFlow()

    private val _pastSearches = mutableStateOf(getPastSearchFromPreference())
    val pastSearch get() = _pastSearches

    var searchText by mutableStateOf(value = "")
    var isFocusState by mutableStateOf(value = true)

    init {
        searchProduct()
        searchStore()
    }

    private fun searchStore() {

        viewModelScope.launch {
            snapshotFlow { searchText }
                .collectLatest { text ->
                    if (text.length > 2) {
                        val search = SearchRequest(
                            term = text,
                            location = getLocationFromLocal()?.gps,
                            size = 20,
                            expectedEntity = "store"
                        )
                        getStoreSearchPagingUseCase(search).cachedIn(viewModelScope).collectLatest {
                            _storeSearch.emit(it)
                        }
                    }
                }
        }
    }

    private fun searchProduct() {

        viewModelScope.launch {
            snapshotFlow { searchText }
                .collectLatest { text ->
                    if (text.length > 2) {
                        val search = SearchRequest(
                            term = text,
                            location = getLocationFromLocal()?.gps,
                            size = 20,
                            expectedEntity = "product"
                        )
                        getProductSearchPagingUseCase(search).cachedIn(viewModelScope)
                            .collectLatest {
                                _productSearch.emit(it)
                            }
                    }
                }
        }
    }

    private fun getPastSearchFromPreference(): List<String> {

        val searchJson = myPreference.getStoresTag(SEARCH.name)
        if (searchJson.isNullOrBlank()) {
            return emptyList()
        }
        return gson.fromJson(searchJson, object : TypeToken<List<String>>() {}.type)
    }

    fun addPastSearch() {
        val searches = pastSearch.value.toMutableList()
        searchText.trim().let {
            searches.removeIf { text -> text == it }
            searches.add(it)
        }
        pastSearch.value = searches
    }

    fun removePastSearch(value: String) {
        val search = pastSearch.value.toMutableList()
        search.remove(value)
        pastSearch.value = search
    }

    fun setPastSearchToPreference() {
        myPreference.setStoredTag(SEARCH.name, gson.toJson(pastSearch.value))
    }

    private fun getLocationFromLocal(): LocationRequest? {

        val locationJson = myPreference.getStoresTag(SharedPreference.LOCATION.name)
        if (locationJson.isNullOrBlank()) {
            return null
        }
        return gson.fromJson(locationJson, LocationRequest::class.java)
    }
}