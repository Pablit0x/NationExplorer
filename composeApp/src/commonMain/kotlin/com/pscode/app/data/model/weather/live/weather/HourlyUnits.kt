package com.pscode.app.data.model.weather.live.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyUnits(
    @SerialName("pressure_msl")
    val pressureMsl: String,
    @SerialName("relativehumidity_2m")
    val relativehumidity2m: String,
    @SerialName("temperature_2m")
    val temperature2m: String,
    @SerialName("time")
    val time: String,
    @SerialName("weathercode")
    val weathercode: String,
    @SerialName("windspeed_10m")
    val windspeed10m: String,
    @SerialName("visibility")
    val visibility : String,
    @SerialName("apparent_temperature")
    val feelsLike : String,
    @SerialName("cloud_cover")
    val cloudiness: String
)