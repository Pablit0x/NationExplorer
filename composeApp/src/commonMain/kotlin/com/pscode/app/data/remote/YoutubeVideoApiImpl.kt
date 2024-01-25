package com.pscode.app.data.remote

import com.pscode.app.data.model.video.YoutubeVideoDto
import com.pscode.app.domain.remote.YoutubeVideoApi
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

class YoutubeVideoApiImpl(private val httpClient: HttpClient) : YoutubeVideoApi {
    val baseUrl = "https://pablit0x.github.io/nation_explorer_videos"

    override suspend fun getVideoIdForCountryName(countryName: String): Response<YoutubeVideoDto> {
        return try {
            Response.Success(data = httpClient.get {
                url("${baseUrl}/$countryName.json")
            }.body<YoutubeVideoDto>())
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