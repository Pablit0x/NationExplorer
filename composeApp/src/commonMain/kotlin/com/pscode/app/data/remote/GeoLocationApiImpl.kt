package com.pscode.app.data.remote

import FunApp.composeApp.BuildConfig
import com.pscode.app.data.model.geo_location.LocationDto
import com.pscode.app.domain.remote.GeoLocationApi
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

class GeoLocationApiImpl(val httpClient: HttpClient) : GeoLocationApi {
    private val baseUrl = "https://api.opencagedata.com/geocode/v1/"

    override suspend fun getGeoLocationByCountry(
        countryName: String
    ): Response<LocationDto> {
        return try {
            Response.Success(data = httpClient.get {
                url("${baseUrl}json?q=URI-ENCODED-$countryName&key=${BuildConfig.GEO_LOCATION_API_KEY}")
            }.body<LocationDto>())
        } catch (e: IOException) {
            Response.Error("Network issue.")
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