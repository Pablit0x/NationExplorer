package com.pscode.app.domain.repository

import com.pscode.app.data.model.weather.WeatherDto
import com.pscode.app.domain.model.WeatherOverview
import com.pscode.app.utils.Response

interface WeatherRepository {
    suspend fun getWeatherByCity(cityName: String) : Response<WeatherOverview>
}