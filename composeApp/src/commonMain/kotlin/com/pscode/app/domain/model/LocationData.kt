package com.pscode.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationData(
    val name: String,
    val latitude: Double,
    val longitude: Double,
)
