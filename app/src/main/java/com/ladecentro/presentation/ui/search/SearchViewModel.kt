package com.ladecentro.presentation.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ladecentro.common.MyPreference
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.common.SharedPreference
import com.ladecentro.common.SharedPreference.SEARCH
import com.ladecentro.data.remote.dto.SearchDto
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.use_case.GetSearchUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val myPreference: MyPreference,
    private val gson: Gson
) : ViewModel() {

    private val _searchState = MutableStateFlow<UIStates<SearchDto>>(UIStates(isLoading = true))
    val searchState: StateFlow<UIStates<SearchDto>> get() = _searchState

    private val _pastSearches = mutableStateOf(getPastSearchFromPreference())
    val pastSearch get() = _pastSearches

    var searchText by mutableStateOf(value = "")
    var isFocusState by mutableStateOf(value = true)

    init {
        searchStoreProduct()
    }

    private fun searchStoreProduct() {

        viewModelScope.launch {
            snapshotFlow { searchText }
                .collectLatest { text ->
                    if (text.length > 2) {
                        val search = SearchRequest(
                            term = text,
                            location = getLocationFromLocal()?.gps,
                            page = 0,
                            size = 50
                        )
                        getSearchUseCase(search).collect {
                            when (it) {
                                is Loading -> {
                                    _searchState.emit(UIStates(isLoading = true))
                                }

                                is Success -> {
                                    _searchState.emit(UIStates(content = it.data))
                                    addPastSearch()
                                }

                                is Error -> {
                                    _searchState.emit(UIStates(error = it.message))
                                }
                            }
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

    private fun addPastSearch() {
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