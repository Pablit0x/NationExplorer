package com.pscode.app.domain.repository

import com.pscode.app.domain.model.YoutubeVideoData
import com.pscode.app.utils.Response

interface YoutubeVideoRepository {
    suspend fun getVideoIdByCountry(countryName : String) : Response<YoutubeVideoData>
}