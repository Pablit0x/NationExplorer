package com.pscode.app.data.model.weather.live.weather


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hourly(
    @SerialName("pressure_msl")
    val pressureMsl: List<Double>,
    @SerialName("relativehumidity_2m")
    val relativehumidity2m: List<Int>,
    @SerialName("temperature_2m")
    val temperature2m: List<Double>,
    @SerialName("time")
    val time: List<String>,
    @SerialName("weathercode")
    val weathercode: List<Int>,
    @SerialName("windspeed_10m")
    val windspeed10m: List<Double>,
    @SerialName("visibility")
    val visibility: List<Double>,
    @SerialName("apparent_temperature")
    val feelsLike: List<Double>,
    @SerialName("cloud_cover")
    val cloudiness: List<Int>
)