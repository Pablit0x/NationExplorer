package com.pscode.app.data.model.weather.historical.day_light


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyUnits(
    @SerialName("daylight_duration")
    val daylightDuration: String,
    @SerialName("time")
    val time: String
)