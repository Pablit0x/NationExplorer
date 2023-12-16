package com.pscode.app.data.remote

import FunApp.composeApp.BuildConfig
import com.pscode.app.data.model.weather.WeatherDto
import com.pscode.app.domain.remote.WeatherApi
import com.pscode.app.utils.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CancellationException

class WeatherApiImpl(private val httpClient: HttpClient) : WeatherApi {

    private val baseUrl = "https://api.api-ninjas.com/v1/weather"

    override suspend fun getWeatherByCity(cityName: String): Response<WeatherDto> {
        return try {
            Response.Success(data = httpClient.get {
                url("$baseUrl?city=$cityName")
                header("X-Api-Key", BuildConfig.WEATHER_API_KEY)
            }.body<WeatherDto>())
        } catch (e: IOException) {
            Response.Error("Network issue")
        } catch (e: ClientRequestException) {
            Response.Error("Invalid request.")
        } catch (e: ServerResponseException) {
            Response.Error("Server unavailable.")
        } catch (e: HttpRequestTimeoutException) {
            Response.Error("Request timed out.")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Response.Error("Unexpected issue occurred.")
        }
    }
}