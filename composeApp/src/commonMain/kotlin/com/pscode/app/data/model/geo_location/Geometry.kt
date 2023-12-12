package com.pscode.app.data.model.geo_location


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lng")
    val lng: Double
)