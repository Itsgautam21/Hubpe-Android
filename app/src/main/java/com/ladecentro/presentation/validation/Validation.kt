package com.ladecentro.presentation.validation

fun validatePhoneNumber(string: String?) : Boolean {

    if (string.isNullOrEmpty() || string.length != 10) {
        return false
    }
    return true
}

fun validateOTP(string: String?) : Boolean {

    if (string.isNullOrEmpty() || string.length != 4) {
        return false
    }
    return true
}