package com.pscode.app.data.model.weather.historical.rain_sum


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyUnits(
    @SerialName("rain_sum")
    val rainSum: String,
    @SerialName("time")
    val time: String
)