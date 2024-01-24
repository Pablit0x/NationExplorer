package com.pscode.app.domain.repository

import com.pscode.app.domain.model.LocationData
import com.pscode.app.domain.model.SixMonthsWeatherOverview
import com.pscode.app.domain.model.WeatherConditions
import com.pscode.app.domain.model.WeatherInfo
import com.pscode.app.utils.Response

interface WeatherRepository {
    suspend fun getTemperatureRangePastSixMonths(locationData: LocationData): Response<SixMonthsWeatherOverview>

    suspend fun getWindSpeedRangePastSixMonths(locationData: LocationData): Response<SixMonthsWeatherOverview>
    suspend fun getDayLightRangePastSixMonths(locationData: LocationData): Response<SixMonthsWeatherOverview>

    suspend fun getRainSumRangePastSixMonths(locationData: LocationData): Response<SixMonthsWeatherOverview>
    suspend fun getWeatherIcons(): Response<List<WeatherConditions>>

    suspend fun getWeatherData(locationData: LocationData): Response<WeatherInfo>
}