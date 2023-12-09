package com.pscode.app.data.model.weather

import com.pscode.app.domain.model.WeatherOverview
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

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

fun WeatherDto.toWeatherOverview(): WeatherOverview {
    return WeatherOverview(
        cloudCoverPercent = formatPercentageNumber(number = this.cloud_pct),
        perceptibleTemperature = formatCelsius(degrees = this.feels_like),
        humidity = formatPercentageNumber(number = this.humidity),
        maxTemp = formatCelsius(degrees = this.max_temp),
        minTemp = formatCelsius(degrees = this.min_temp),
        sunriseTime = convertTimestampToHourMinute(timestamp = this.sunrise.toLong()),
        sunsetTime = convertTimestampToHourMinute(timestamp = this.sunset.toLong()),
        currentTemperature = formatCelsius(degrees = this.temp),
        windDegrees = this.wind_degrees,
        windSpeed = convertMinPerKmToKmPerHr(minPerKm = this.wind_speed)
    )
}

private fun convertTimestampToHourMinute(timestamp: Long): String {
    val instant = Instant.fromEpochSeconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.hour.toString().padStart(2, '0')}:${
        localDateTime.minute.toString().padStart(2, '0')
    }"
}

private fun formatPercentageNumber(number: Int): String {
    return "${number}%"
}

private fun convertMinPerKmToKmPerHr(minPerKm: Double): String {
    val kmPerMin = 1 / minPerKm
    val kmPerHour = kmPerMin * 60
    return "${kmPerHour.roundToInt()}km/h"
}

private fun formatCelsius(degrees: Int): String {
    return "${degrees}ºC"
}