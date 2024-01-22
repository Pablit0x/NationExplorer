package com.pscode.app.data.model.weather.historical.wind


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyUnits(
    @SerialName("time")
    val time: String,
    @SerialName("wind_speed_10m_max")
    val windSpeed10mMax: String
)