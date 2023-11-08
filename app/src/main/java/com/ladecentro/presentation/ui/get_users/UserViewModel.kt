package com.ladecentro.presentation.ui.get_users

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ladecentro.common.Resource.Error
import com.ladecentro.common.Resource.Loading
import com.ladecentro.common.Resource.Success
import com.ladecentro.domain.use_case.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val usersUseCase: GetUsersUseCase): ViewModel() {

    private val _state = mutableStateOf(UsersState())
    val state: State<UsersState> = _state

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            usersUseCase().collect {
                when(it) {
                    is Loading -> {
                        _state.value = UsersState(isLoading = true)
                    }
                    is Success -> {
                        _state.value = UsersState(users = it.data!!)
                        Log.d("API", it.data.toString())
                    }
                    is Error -> {
                        _state.value = UsersState(error = it.message!!)
                    }
                }
            }
        }
    }
}