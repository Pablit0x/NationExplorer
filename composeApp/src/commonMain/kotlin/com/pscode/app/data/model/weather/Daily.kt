package com.pscode.app.data.model.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Daily(
    @SerialName("temperature_2m_max")
    val temperature2mMax: List<Double>,
    @SerialName("temperature_2m_min")
    val temperature2mMin: List<Double>,
    @SerialName("time")
    val time: List<String>
)