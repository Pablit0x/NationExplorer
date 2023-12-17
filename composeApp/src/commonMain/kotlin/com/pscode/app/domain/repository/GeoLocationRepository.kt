package com.pscode.app.domain.repository

import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.utils.Response

interface GeoLocationRepository {
    suspend fun getGeoLocationByCountry(countryName: String): Response<LocationOverview>
}