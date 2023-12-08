package com.pscode.app.domain.model

data class WeatherOverview(
    val cloudCoverPercent: Int,
    val perceptibleTemperature: Int,
    val humidity: Int,
    val maxTemp: Int,
    val minTemp: Int,
    val sunriseTime: String,
    val sunsetTime: String,
    val currentTemperature: Int,
    val windDegrees: Int,
    val windSpeed: Double
)