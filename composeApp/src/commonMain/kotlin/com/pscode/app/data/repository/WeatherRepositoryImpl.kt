package com.pscode.app.data.repository


import com.pscode.app.data.model.weather.toWeatherOverview
import com.pscode.app.domain.model.WeatherOverview
import com.pscode.app.domain.remote.WeatherApi
import com.pscode.app.domain.repository.WeatherRepository
import com.pscode.app.utils.Response

class WeatherRepositoryImpl(private val weatherApi: WeatherApi) : WeatherRepository {
    override suspend fun getWeatherByCity(cityName: String): Response<WeatherOverview> {
        val result = weatherApi.getWeatherByCity(cityName = cityName)

        return when (result) {
            is Response.Success -> {
                Response.Success(data = result.data.toWeatherOverview())
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }
}