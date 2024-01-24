package com.pscode.app.data.remote

import com.pscode.app.data.model.weather.historical.day_light.DayLightHistoricalDto
import com.pscode.app.data.model.weather.historical.rain_sum.RainSumHistoricalDto
import com.pscode.app.data.model.weather.historical.temperature.TemperatureHistoricalDto
import com.pscode.app.data.model.weather.historical.wind.WindSpeedHistoricalDto
import com.pscode.app.data.model.weather.live.icons.WeatherIconsDto
import com.pscode.app.data.model.weather.live.weather.WeatherDataDto
import com.pscode.app.domain.model.LocationData
import com.pscode.app.domain.remote.WeatherApi
import com.pscode.app.utils.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

class WeatherApiImpl(private val httpClient: HttpClient) : WeatherApi {

    private val baseUrlOpenMeteoHistorical = "https://archive-api.open-meteo.com/v1/archive"
    private val baseUrlOpenMeteoLive = "https://api.open-meteo.com/v1/"
    private val baseUrlIcons = "https://pablit0x.github.io/nation_explorer_weather_icons/"

    override suspend fun getTemperatureRangePastSixMonths(locationData: LocationData): Response<TemperatureHistoricalDto> {
        return try {
            val timeZone = TimeZone.currentSystemDefault()
            val dateNow = Clock.System.now().toLocalDateTime(timeZone).date
            val endDate = dateNow.minus(DatePeriod(days = 5))
            val startDate = dateNow.minus(DatePeriod(months = 6, days = 5))
            Response.Success(data = httpClient.get {
                url("$baseUrlOpenMeteoHistorical?latitude=${locationData.latitude}&longitude=${locationData.longitude}&start_date=$startDate&end_date=$endDate&daily=temperature_2m_max&daily=temperature_2m_min")
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

    override suspend fun getWindSpeedRangePastSixMonths(locationData: LocationData): Response<WindSpeedHistoricalDto> {
        return try {
            val timeZone = TimeZone.currentSystemDefault()
            val dateNow = Clock.System.now().toLocalDateTime(timeZone).date
            val endDate = dateNow.minus(DatePeriod(days = 5))
            val startDate = dateNow.minus(DatePeriod(months = 6, days = 5))
            Response.Success(data = httpClient.get {
                url("$baseUrlOpenMeteoHistorical?latitude=${locationData.latitude}&longitude=${locationData.longitude}&start_date=$startDate&end_date=$endDate&daily=wind_speed_10m_max")
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

    override suspend fun getDayLightDurationRangePastSixMonths(locationData: LocationData): Response<DayLightHistoricalDto> {
        return try {
            val timeZone = TimeZone.currentSystemDefault()
            val dateNow = Clock.System.now().toLocalDateTime(timeZone).date
            val endDate = dateNow.minus(DatePeriod(days = 5))
            val startDate = dateNow.minus(DatePeriod(months = 6, days = 5))
            Response.Success(data = httpClient.get {
                url("$baseUrlOpenMeteoHistorical?latitude=${locationData.latitude}&longitude=${locationData.longitude}&start_date=$startDate&end_date=$endDate&daily=daylight_duration")
            }.body<DayLightHistoricalDto>())
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

    override suspend fun getRainSumRangePastSixMonths(locationData: LocationData): Response<RainSumHistoricalDto> {
        return try {
            val timeZone = TimeZone.currentSystemDefault()
            val dateNow = Clock.System.now().toLocalDateTime(timeZone).date
            val endDate = dateNow.minus(DatePeriod(days = 5))
            val startDate = dateNow.minus(DatePeriod(months = 6, days = 5))
            Response.Success(data = httpClient.get {
                url("$baseUrlOpenMeteoHistorical?latitude=${locationData.latitude}&longitude=${locationData.longitude}&start_date=$startDate&end_date=$endDate&daily=rain_sum")
            }.body<RainSumHistoricalDto>())
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

    override suspend fun getLiveWeatherIcons(): Response<WeatherIconsDto> {
        return try {
            Response.Success(data = httpClient.get {
                url("$baseUrlIcons/icons.json")
            }.body<WeatherIconsDto>())
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

    override suspend fun getWeatherData(locationData: LocationData): Response<WeatherDataDto> {
        return try {
            Response.Success(data = httpClient.get {
                url("${baseUrlOpenMeteoLive}forecast?latitude=${locationData.latitude}&longitude=${locationData.longitude}&hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl,visibility,cloud_cover,apparent_temperature")
            }.body<WeatherDataDto>())
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