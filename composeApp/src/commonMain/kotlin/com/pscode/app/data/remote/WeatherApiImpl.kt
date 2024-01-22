package com.pscode.app.data.remote

import FunApp.composeApp.BuildConfig
import com.pscode.app.data.model.weather.WeatherDto
import com.pscode.app.data.model.weather.historical.temperature.TemperatureHistoricalDto
import com.pscode.app.data.model.weather.historical.wind.WindSpeedHistoricalDto
import com.pscode.app.domain.model.LocationOverview
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
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

class WeatherApiImpl(private val httpClient: HttpClient) : WeatherApi {

    private val baseUrlNinjasWeather = "https://api.api-ninjas.com/v1/weather"
    private val baseUrlOpenMeteo = "https://archive-api.open-meteo.com/v1/archive"

    override suspend fun getWeatherByCity(cityName: String): Response<WeatherDto> {
        return try {
            Response.Success(data = httpClient.get {
                url("$baseUrlNinjasWeather?city=$cityName")
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

    override suspend fun getTemperatureRangePastSixMonths(locationOverview: LocationOverview): Response<TemperatureHistoricalDto> {
        return try {
            val timeZone = TimeZone.currentSystemDefault()
            val dateNow = Clock.System.now().toLocalDateTime(timeZone).date
            val endDate = dateNow.minus(DatePeriod(days = 5))
            val startDate = dateNow.minus(DatePeriod(months = 6, days = 5))
            Response.Success(data = httpClient.get {
                url("$baseUrlOpenMeteo?latitude=${locationOverview.latitude}&longitude=${locationOverview.longitude}&start_date=$startDate&end_date=$endDate&daily=temperature_2m_max&daily=temperature_2m_min")
            }.body<TemperatureHistoricalDto>())
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

    override suspend fun getWindSpeedRangePastSixMonths(locationOverview: LocationOverview): Response<WindSpeedHistoricalDto> {
        return try {
            val timeZone = TimeZone.currentSystemDefault()
            val dateNow = Clock.System.now().toLocalDateTime(timeZone).date
            val endDate = dateNow.minus(DatePeriod(days = 5))
            val startDate = dateNow.minus(DatePeriod(months = 6, days = 5))
            Response.Success(data = httpClient.get {
                url("$baseUrlOpenMeteo?latitude=${locationOverview.latitude}&longitude=${locationOverview.longitude}&start_date=$startDate&end_date=$endDate&daily=wind_speed_10m_max")
            }.body<WindSpeedHistoricalDto>())
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