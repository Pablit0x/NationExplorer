package com.pscode.app.data.model.country

import kotlinx.serialization.Serializable

@Serializable
data class Maps(
    val googleMaps: String,
    val openStreetMaps: String
)