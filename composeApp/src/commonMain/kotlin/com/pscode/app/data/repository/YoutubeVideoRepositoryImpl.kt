package com.pscode.app.data.repository

import com.pscode.app.domain.model.YoutubeVideoData
import com.pscode.app.domain.model.toYoutubeVideoData
import com.pscode.app.domain.remote.YoutubeVideoApi
import com.pscode.app.domain.repository.YoutubeVideoRepository
import com.pscode.app.utils.Response

class YoutubeVideoRepositoryImpl(private val youtubeVideoApi: YoutubeVideoApi) : YoutubeVideoRepository {
    override suspend fun getVideoIdByCountry(countryName: String): Response<YoutubeVideoData> {
        val result = youtubeVideoApi.getVideoIdForCountryName(countryName = countryName)

        return when (result) {
            is Response.Success -> {
                Response.Success(
                    data = result.data.toYoutubeVideoData()
                )
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }
}