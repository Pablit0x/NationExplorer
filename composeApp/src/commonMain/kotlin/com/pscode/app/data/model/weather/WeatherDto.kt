package com.pscode.app.data.model.weather

import com.pscode.app.domain.model.WeatherOverview
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val cloud_pct: Int,
    val feels_like: Int,
    val humidity: Int,
    val max_temp: Int,
    val min_temp: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Int,
    val wind_degrees: Int,
    val wind_speed: Double
)

fun WeatherDto.toWeatherOverview() : WeatherOverview {
    return WeatherOverview(
        cloudCoverPercent = this.cloud_pct,
        perceptibleTemperature =  this.feels_like,
        humidity = this.humidity,
        maxTemp = this.max_temp,
        minTemp = this.min_temp,
        sunriseTimestamp = this.sunrise,
        sunsetTimestamp = this.sunset,
        currentTemperature = this.temp,
        windDegrees = this.wind_degrees,
        windSpeed = this.wind_speed
    )
}