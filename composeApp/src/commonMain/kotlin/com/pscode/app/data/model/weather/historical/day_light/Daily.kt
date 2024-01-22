package com.pscode.app.data.model.weather.historical.day_light


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Daily(
    @SerialName("daylight_duration")
    val daylightDuration: List<Double>,
    @SerialName("time")
    val time: List<String>
)