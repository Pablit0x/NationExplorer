package com.pscode.app.data.remote

import FunApp.composeApp.BuildConfig
import com.pscode.app.data.model.geolocation.LocationDto
import com.pscode.app.data.model.tidbits.TidbitsDto
import com.pscode.app.domain.remote.TidbitsApi
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

class TidbitsApiImpl(private val httpClient: HttpClient) : TidbitsApi {

    private val baseUrl = "https://pablit0x.github.io/nation_explorer_tidbits_api/"

    override suspend fun getTidbitsByCountryName(countryName: String): Response<TidbitsDto> {
        return try {
            Response.Success(data = httpClient.get {
                url("${baseUrl}/$countryName.json")
            }.body<TidbitsDto>())
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