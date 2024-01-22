package com.pscode.app.data.model.weather.historical.rain_sum


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Daily(
    @SerialName("rain_sum")
    val rainSum: List<Double>,
    @SerialName("time")
    val time: List<String>
)