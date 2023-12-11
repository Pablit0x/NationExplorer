package com.pscode.app.data.remote

import FunApp.composeApp.BuildConfig
import com.pscode.app.data.model.geo_location.GeoLocationDto
import com.pscode.app.data.model.weather.WeatherDto
import com.pscode.app.di.httpClient
import com.pscode.app.domain.model.GeoLocationOverview
import com.pscode.app.domain.remote.GeoLocationApi
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

class GeoLocationApiImpl(val httpClient: HttpClient) : GeoLocationApi {
    private val baseUrl = "https://api.api-ninjas.com/v1/geocoding"

    override suspend fun getGeoLocationByCountry(
        cityName: String,
        countryName: String
    ): Response<List<GeoLocationDto>> {
        return try {
            Response.Success(data = httpClient.get {
                url("$baseUrl?city=$cityName&country=$countryName")
                header("X-Api-Key", BuildConfig.WEATHER_API_KEY)
            }.body<List<GeoLocationDto>>())
        } catch (e: IOException) {
            Response.Error("Network error: ${e.message}")
        } catch (e: ClientRequestException) {
            Response.Error("Client request error: ${e.response.status.description}")
        } catch (e: ServerResponseException) {
            Response.Error("Server response error: ${e.response.status.description}")
        } catch (e: HttpRequestTimeoutException) {
            Response.Error("Request timeout: ${e.message}")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Response.Error("Unexpected error: ${e.message}")
        }
    }
}