package com.pscode.app.domain.repository

import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.domain.model.SixMonthsWeatherOverview
import com.pscode.app.domain.model.WeatherOverview
import com.pscode.app.utils.Response

interface WeatherRepository {
    suspend fun getWeatherByCity(cityName: String): Response<WeatherOverview>

    suspend fun getTemperatureRangePastSixMonths(locationOverview: LocationOverview): Response<SixMonthsWeatherOverview>
}