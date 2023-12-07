package com.pscode.app.data.model.weather

import com.pscode.app.domain.model.WeatherOverview
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
        sunriseTime = convertTimestampToHourMinute(this.sunrise.toLong()),
        sunsetTime = convertTimestampToHourMinute(this.sunset.toLong()),
        currentTemperature = this.temp,
        windDegrees = this.wind_degrees,
        windSpeed = this.wind_speed
    )
}

private fun convertTimestampToHourMinute(timestamp: Long): String {
    val instant = Instant.fromEpochSeconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.hour.toString().padStart(2, '0')}:${
        localDateTime.minute.toString().padStart(2, '0')
    }"
}