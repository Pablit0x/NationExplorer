package com.pscode.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationOverview(
    val name : String,
    val latitude: Double,
    val longitude: Double,
)
