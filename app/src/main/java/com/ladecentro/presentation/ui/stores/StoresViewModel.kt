package com.ladecentro.presentation.ui.stores

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ladecentro.common.Intents
import com.ladecentro.common.PreferenceUtils
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.SearchDto
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.data.remote.dto.Store
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.use_case.GetSearchUseCase
import com.ladecentro.domain.use_case.GetStoreSearchPagingUseCase
import com.ladecentro.presentation.common.UIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoresViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val preferenceUtils: PreferenceUtils,
    private val searchUseCase: GetSearchUseCase,
    private val storeSearchPagingUseCase: GetStoreSearchPagingUseCase
) :
    ViewModel() {

    private var _locationState by mutableStateOf(preferenceUtils.getLocationFromLocal())
    val locationState: LocationRequest get() = _locationState

    private var _storeState by mutableStateOf(UIStates<SearchDto>())
    val storeState: UIStates<SearchDto> get() = _storeState

    private val _nonPromotedStores: MutableStateFlow<PagingData<Store>> =
        MutableStateFlow(PagingData.empty())
    val nonPromotedStores: StateFlow<PagingData<Store>> get() = _nonPromotedStores

    private val categoryName = savedStateHandle[Intents.CATEGORY_NAME.name] ?: ""

    fun getLocationFromLocal() {
        _locationState = preferenceUtils.getLocationFromLocal()
    }

    init {
        getStoresBySector()
        getNonPromotedStores()
    }

    private fun getNonPromotedStores() {

        val request = SearchRequest(
            page = 0,
            size = 10,
            location = locationState.gps,
            expectedEntity = "store",
            isPromoted = false,
            sector = categoryName
        )
        viewModelScope.launch {
            storeSearchPagingUseCase(request).collectLatest {
                _nonPromotedStores.emit(it)
            }
        }
    }

    private fun getStoresBySector() {

        val request = SearchRequest(
            page = 0,
            size = 50,
            location = locationState.gps,
            expectedEntity = "store",
            sector = categoryName
        )

        viewModelScope.launch {
            searchUseCase(request).collect {
                _storeState = when (it) {
                    is Loading -> UIStates(isLoading = true)
                    is Success -> UIStates(content = it.data)
                    is Error -> UIStates(error = it.message)
                }
            }
        }
    }
}