package com.ladecentro.presentation.common

data class UIStates<T>(
    val isLoading: Boolean = false,
    val content: T? = null,
    val error: String? = null
)
