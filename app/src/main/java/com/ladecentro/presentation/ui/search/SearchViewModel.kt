package com.ladecentro.presentation.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val myPreference: MyPreference,
    private val gson: Gson
) : ViewModel() {

    private val _searchState = MutableStateFlow<UIStates<SearchDto>>(UIStates())
    val searchState: StateFlow<UIStates<SearchDto>> get() = _searchState

    private val _pastSearches = mutableStateOf(getPastSearchFromPreference())
    val pastSearch get() = _pastSearches

    var searchText = mutableStateOf("")
    var saveSearch by mutableStateOf<String?>(null)
    private var job: Job? = null

    fun searchStoreProduct() {

        val search = SearchRequest(
            term = searchText.value,
            location = getLocationFromLocal()?.gps,
            page = 0,
            size = 50
        )
        job?.cancel()
        saveSearch = searchText.value
        job = viewModelScope.launch {
            getSearchUseCase(search).collect {
                when (it) {
                    is Loading -> {
                        _searchState.emit(UIStates(isLoading = true))
                    }

                    is Success -> {
                        _searchState.emit(UIStates(content = it.data))
                    }

                    is Error -> {
                        _searchState.emit(UIStates(error = it.message))
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

    fun setPastSearchToPreference(searches: List<String>) {
        myPreference.setStoredTag(SEARCH.name, gson.toJson(searches))
    }

    fun getLocationFromLocal(): LocationRequest? {
        val locationJson = myPreference.getStoresTag(SharedPreference.LOCATION.name)
        if (locationJson.isNullOrBlank()) {
            return null
        }
        return gson.fromJson(locationJson, LocationRequest::class.java)
    }
}