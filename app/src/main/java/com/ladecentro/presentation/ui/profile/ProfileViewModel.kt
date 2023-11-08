package com.ladecentro.presentation.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ladecentro.common.Intents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    var userName by mutableStateOf(savedStateHandle.get<String>(Intents.USER_NAME.name))
    val phoneNumber = savedStateHandle.get<String>(Intents.Phone.name)


}