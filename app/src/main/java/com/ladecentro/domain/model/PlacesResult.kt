package com.ladecentro.domain.model

data class PlacesResult(
    val address: String,
    val placeId: String,
    val primary: String,
    val secondary: String
)