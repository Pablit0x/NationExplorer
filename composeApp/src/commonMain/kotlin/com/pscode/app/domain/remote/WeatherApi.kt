package com.pscode.app.domain.remote

import com.pscode.app.data.model.weather.WeatherDto
import com.pscode.app.utils.Response

interface WeatherApi {
    suspend fun getWeatherByCity(cityName: String): Response<WeatherDto>
}