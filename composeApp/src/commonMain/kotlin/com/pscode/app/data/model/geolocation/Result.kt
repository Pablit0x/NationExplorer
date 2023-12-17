package com.pscode.app.data.model.geolocation


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("confidence")
    val confidence: Int,
    @SerialName("geometry")
    val geometry: Geometry
)