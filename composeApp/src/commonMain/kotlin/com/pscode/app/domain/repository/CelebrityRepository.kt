package com.pscode.app.domain.repository

import com.pscode.app.domain.model.CelebrityOverview
import com.pscode.app.domain.model.TidbitOverview
import com.pscode.app.utils.Response

interface CelebrityRepository {
    suspend fun getCelebritiesByCountryName(countryName: String) : Response<List<CelebrityOverview>>

}