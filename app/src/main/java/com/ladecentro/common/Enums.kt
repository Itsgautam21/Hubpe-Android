package com.ladecentro.common

enum class Intents {
    Phone, Token, USER_NAME, ORDER_ID
}

enum class SharedPreference {
    PROFILE
}

enum class OrderStatus(val value: String) {

    PENDING("Pending"),
    NEW("New"),
    CREATED("Created"),
    ACCEPTED("Accepted"),
    IN_PROGRESS("In-progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    companion object {

        fun fromValue(value: String): OrderStatus {
            for (i in OrderStatus.values()) {
                if (i.value.equals(value, true)) {
                    return i
                }
            }
            throw RuntimeException("Unexpected value! : $value")
        }
    }
}