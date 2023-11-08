package com.ladecentro.presentation.ui.authentication.login

import java.util.*

data class LoginState(
    val isLoading: Boolean = false,
    val users: Objects? = null,
    val error: String? = null
)
