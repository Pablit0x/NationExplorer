package com.pscode.app.data.remote

import com.pscode.app.data.model.country.CountryDto
import com.pscode.app.data.model.country.toCountry
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.remote.CountryApi
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

class CountryApiImpl(private val httpClient: HttpClient) : CountryApi {

    private val baseUrl = "https://restcountries.com/v3.1/all"

    override suspend fun getAllCountries(): Response<List<CountryOverview>> {
        return try {
            Response.Success(data = httpClient.get {
                url(baseUrl)
            }.body<List<CountryDto>>().sortedBy { it.name.common }.map { it.toCountry() })
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