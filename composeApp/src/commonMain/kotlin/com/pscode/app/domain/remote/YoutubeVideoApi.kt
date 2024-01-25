package com.pscode.app.domain.remote

import com.pscode.app.data.model.video.YoutubeVideoDto
import com.pscode.app.utils.Response

interface YoutubeVideoApi {
    suspend fun getVideoIdForCountryName(countryName: String) : Response<YoutubeVideoDto>
}