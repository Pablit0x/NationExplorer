package com.pscode.app.domain.remote

import com.pscode.app.data.model.weather.historical.day_light.DayLightHistoricalDto
import com.pscode.app.data.model.weather.historical.rain_sum.RainSumHistoricalDto
import com.pscode.app.data.model.weather.historical.temperature.TemperatureHistoricalDto
import com.pscode.app.data.model.weather.historical.wind.WindSpeedHistoricalDto
import com.pscode.app.data.model.weather.live.WeatherDto
import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.utils.Response

interface WeatherApi {
    suspend fun getWeatherByCity(cityName: String): Response<WeatherDto>
    suspend fun getTemperatureRangePastSixMonths(locationOverview: LocationOverview): Response<TemperatureHistoricalDto>

    suspend fun getWindSpeedRangePastSixMonths(locationOverview: LocationOverview) : Response<WindSpeedHistoricalDto>

    suspend fun getDayLightDurationRangePastSixMonths(locationOverview: LocationOverview) : Response<DayLightHistoricalDto>

    suspend fun getRainSumRangePastSixMonths(locationOverview: LocationOverview): Response<RainSumHistoricalDto>
}