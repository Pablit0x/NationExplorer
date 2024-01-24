package com.pscode.app.domain.repository

import com.pscode.app.domain.model.CelebrityData
import com.pscode.app.utils.Response

interface CelebrityRepository {
    suspend fun getCelebritiesByCountryName(countryName: String): Response<CelebrityData>

}