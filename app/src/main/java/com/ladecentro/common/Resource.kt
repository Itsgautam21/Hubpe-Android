package com.ladecentro.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}

/*
sealed class Resource<T, E>(val data: T? = null, val message: E? = null) {

    class Success<T, E>(data: T) : Resource<T, E>(data)
    class Error<T, E>(message: E, data: T? = null) : Resource<T, E>(data, message)
    class Loading<T, E>(data: T? = null) : Resource<T, E>(data)
}
*/
