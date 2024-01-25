package com.pscode.app.domain.model

import com.pscode.app.data.model.weather.live.weather.WeatherDataDto
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

data class WeatherData(
    val temperature: Double,
    val hPa: Double,
    val weatherConditions: WeatherConditions?,
    val humidity: Int,
    val windSpeed: Double,
    val visibility: Double,
    val feelsLike: Double,
    val cloudiness: Int,
    val date: LocalDateTime
)

private data class IndexedWeatherData(
    val index: Int, val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(weatherConditions: List<WeatherConditions>): Map<Int, List<WeatherData>> {
    return this.hourly.time.mapIndexed { index, time ->
        val temperature = this.hourly.temperature2m[index]
        val weatherCode = this.hourly.weathercode[index]
        val windSpeed = this.hourly.windspeed10m[index]
        val pressure = this.hourly.pressureMsl[index]
        val humidity = this.hourly.relativehumidity2m[index]
        val visibility = this.hourly.visibility[index]
        val feelsLike = this.hourly.feelsLike[index]
        val cloudiness = this.hourly.cloudiness[index]
        IndexedWeatherData(
            index = index, data = WeatherData(
                date = LocalDateTime.parse(time),
                temperature = temperature,
                hPa = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                visibility = visibility,
                feelsLike = feelsLike,
                cloudiness = cloudiness,
                weatherConditions = weatherCode.toWeatherConditions(weatherConditions = weatherConditions)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues { it ->
        it.value.map { it.data }
    }
}

fun WeatherDataDto.toWeatherInfo(weatherConditions: List<WeatherConditions>): WeatherInfo {
    val weatherDataMap = this.toWeatherDataMap(weatherConditions = weatherConditions)
    val timeZone = TimeZone.currentSystemDefault()
    val nowInstant = Clock.System.now()
    val now = nowInstant.toLocalDateTime(timeZone = timeZone)

    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else nowInstant.plus(DateTimePeriod(hours = 1), timeZone).toLocalDateTime(timeZone = timeZone).hour
        it.date.hour == hour
    }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap, currentWeatherData = currentWeatherData
    )
}


fun Int.toWeatherConditions(weatherConditions: List<WeatherConditions>): WeatherConditions? {
    val result = when (this) {
        0 -> weatherConditions.find { it.name == "ClearSky" }
        1 -> weatherConditions.find { it.name == "MainlyClear" }
        2 -> weatherConditions.find { it.name == "PartlyCloudy" }
        3 -> weatherConditions.find { it.name == "Overcast" }
        45 -> weatherConditions.find { it.name == "Foggy" }
        48 -> weatherConditions.find { it.name == "DepositingRimeFog" }
        51 -> weatherConditions.find { it.name == "LightDrizzle" }
        53 -> weatherConditions.find { it.name == "ModerateDrizzle" }
        55 -> weatherConditions.find { it.name == "DenseDrizzle" }
        56 -> weatherConditions.find { it.name == "LightFreezingDrizzle" }
        57 -> weatherConditions.find { it.name == "DenseFreezingDrizzle" }
        61 -> weatherConditions.find { it.name == "SlightRain" }
        63 -> weatherConditions.find { it.name == "ModerateRain" }
        65 -> weatherConditions.find { it.name == "HeavyRain" }
        66 -> weatherConditions.find { it.name == "LightFreezingDrizzle" }
        67 -> weatherConditions.find { it.name == "HeavyFreezingRain" }
        71 -> weatherConditions.find { it.name == "SlightSnowFall" }
        73 -> weatherConditions.find { it.name == "ModerateSnowFall" }
        75 -> weatherConditions.find { it.name == "HeavySnowFall" }
        77 -> weatherConditions.find { it.name == "SnowGrains" }
        80 -> weatherConditions.find { it.name == "SlightRainShowers" }
        81 -> weatherConditions.find { it.name == "ModerateRainShowers" }
        82 -> weatherConditions.find { it.name == "ViolentRainShowers" }
        85 -> weatherConditions.find { it.name == "SlightSnowShowers" }
        86 -> weatherConditions.find { it.name == "HeavySnowShowers" }
        95 -> weatherConditions.find { it.name == "ModerateThunderstorm" }
        96 -> weatherConditions.find { it.name == "SlightHailThunderstorm" }
        99 -> weatherConditions.find { it.name == "HeavyHailThunderstorm" }
        else -> weatherConditions.find { it.name == "ClearSky" }
    }

    return result
}