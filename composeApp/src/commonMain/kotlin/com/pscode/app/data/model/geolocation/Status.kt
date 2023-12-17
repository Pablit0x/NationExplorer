package com.pscode.app.data.model.geolocation


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String
)