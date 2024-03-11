package com.ladecentro.presentation.ui.favourite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ladecentro.common.Constants.MY_FAVOURITES
import com.ladecentro.common.Intents
import com.ladecentro.common.MyPreference
import com.ladecentro.data.remote.dto.toFavouriteStore
import com.ladecentro.domain.model.FavouriteStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val myPreference: MyPreference
) : ViewModel() {

    var favState: List<FavouriteStore> by mutableStateOf(emptyList())
    val type: String = savedStateHandle[Intents.TYPE_FAV.name] ?: ""

    fun getFavStoresFromLocal() {
        favState =
            if (type == MY_FAVOURITES) myPreference.getProfileFromLocal()!!.favourites
            else {
                myPreference.getProfileFromLocal()!!.history
            }.map { it.toFavouriteStore() }
    }
}