package com.pscode.app.domain.model

data class WeatherOverview(
    val cloudCoverPercent: String,
    val perceptibleTemperature: String,
    val humidity: String,
    val maxTemp: String,
    val minTemp: String,
    val sunriseTime: String,
    val sunsetTime: String,
    val currentTemperature: String,
    val windDegrees: Int,
    val windSpeed: String
)