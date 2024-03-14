package com.ladecentro.presentation.validation

fun validatePhoneNumber(string: String?) : Boolean {

    return !(string.isNullOrEmpty() || string.length != 10)
}

fun validateOTP(string: String?) : Boolean {

    return !(string.isNullOrEmpty() || string.length != 4)
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