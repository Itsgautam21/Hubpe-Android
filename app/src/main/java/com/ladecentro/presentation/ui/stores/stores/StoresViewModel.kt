package com.ladecentro.presentation.ui.stores.stores

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.data.remote.dto.SearchDto
import com.ladecentro.data.remote.dto.SearchRequest
import com.ladecentro.data.remote.dto.Store
import com.ladecentro.domain.model.LocationRequest
import com.ladecentro.domain.model.ProfileRequest
import com.ladecentro.domain.use_case.GetSearchUseCase
import com.ladecentro.domain.use_case.GetStoreSearchPagingUseCase
import com.ladecentro.domain.use_case.GetUpdateProfileUseCase
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
    private val searchUseCase: GetSearchUseCase,
    private val myPreference: MyPreference,
    private val getUpdateProfileUseCase: GetUpdateProfileUseCase,
    private val storeSearchPagingUseCase: GetStoreSearchPagingUseCase
) :
    ViewModel() {

    private var _locationState by mutableStateOf(myPreference.getLocationFromLocal()!!)
    val locationState: LocationRequest get() = _locationState

    private var _storeState by mutableStateOf(UIStates<SearchDto>())
    val storeState: UIStates<SearchDto> get() = _storeState

    private var _favourite by mutableStateOf(false)
    val favourite get() = _favourite

    private val _nonPromotedStores: MutableStateFlow<PagingData<Store>> =
        MutableStateFlow(PagingData.empty())
    val nonPromotedStores: StateFlow<PagingData<Store>> get() = _nonPromotedStores

    private val categoryName = savedStateHandle[Intents.CATEGORY_NAME.name] ?: ""

    fun getLocationFromLocal() {
        _locationState = myPreference.getLocationFromLocal()!!
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

    fun saveFavourites(storeId: String) {

        _favourite = !favourite
        viewModelScope.launch {
            val request = ProfileRequest(
                type = listOf("FAVOURITES"),
                operation = if (favourite) "ADD" else "REMOVE",
                favourites = listOf(storeId)
            )
            getUpdateProfileUseCase(request).collectLatest {
                when (it) {
                    is Error -> {}
                    is Loading -> {}
                    is Success -> {
                        it.data?.let { profile ->
                            myPreference.setProfileToLocal(profile)
                        }
                    }
                }
            }
        }
    }
}