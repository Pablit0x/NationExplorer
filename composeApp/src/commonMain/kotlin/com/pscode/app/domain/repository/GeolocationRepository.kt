package com.pscode.app.domain.repository

import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.utils.Response

interface GeolocationRepository {
    suspend fun getGeolocationByCountry(countryName: String): Response<LocationOverview>
}