package com.pscode.app.domain.remote

import com.pscode.app.data.model.weather.historical.day_light.DayLightHistoricalDto
import com.pscode.app.data.model.weather.historical.rain_sum.RainSumHistoricalDto
import com.pscode.app.data.model.weather.historical.temperature.TemperatureHistoricalDto
import com.pscode.app.data.model.weather.historical.wind.WindSpeedHistoricalDto
import com.pscode.app.data.model.weather.live.icons.WeatherIconsDto
import com.pscode.app.data.model.weather.live.weather.WeatherDataDto
import com.pscode.app.domain.model.LocationData
import com.pscode.app.utils.Response

interface WeatherApi {
    suspend fun getTemperatureRangePastSixMonths(locationData: LocationData): Response<TemperatureHistoricalDto>

    suspend fun getWindSpeedRangePastSixMonths(locationData: LocationData): Response<WindSpeedHistoricalDto>

    suspend fun getDayLightDurationRangePastSixMonths(locationData: LocationData): Response<DayLightHistoricalDto>

    suspend fun getRainSumRangePastSixMonths(locationData: LocationData): Response<RainSumHistoricalDto>

    suspend fun getLiveWeatherIcons(): Response<WeatherIconsDto>

    suspend fun getWeatherData(locationData: LocationData): Response<WeatherDataDto>
}