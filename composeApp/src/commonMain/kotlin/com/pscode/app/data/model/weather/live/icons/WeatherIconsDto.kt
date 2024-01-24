package com.pscode.app.data.model.weather.live.icons


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherIconsDto(
    @SerialName("icons")
    val icons: List<Icon>
)