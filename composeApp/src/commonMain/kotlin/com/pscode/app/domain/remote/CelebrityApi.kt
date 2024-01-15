package com.pscode.app.domain.remote

import com.pscode.app.data.model.celebrity.CelebrityDto
import com.pscode.app.utils.Response

interface CelebrityApi {
    suspend fun getCelebritiesByCountryName(countryName: String): Response<CelebrityDto>
}