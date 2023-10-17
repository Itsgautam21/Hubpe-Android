package com.ladecentro.presentation.ui.get_users

import com.ladecentro.domain.model.User

data class UsersState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String = ""
)