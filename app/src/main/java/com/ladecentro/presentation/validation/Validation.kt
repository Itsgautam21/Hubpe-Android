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

fun validateName(string: String?) : String? {
    if (string.isNullOrEmpty()) {
        return "Required field!"
    } else if (string.length > 50) {
        return "Name cannot be more 50 characters!"
    }
    return null
}

fun validateRequiredField(string: String?): String? {
    if (string.isNullOrEmpty()) {
        return "Required Field!"
    }
    return null
}