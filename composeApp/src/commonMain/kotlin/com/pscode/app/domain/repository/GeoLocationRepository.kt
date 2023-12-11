package com.pscode.app.domain.repository

import com.pscode.app.domain.model.GeoLocationOverview
import com.pscode.app.utils.Response

interface GeoLocationRepository {

    suspend fun getGeoLocationByCountry(cityName: String, countryName: String) : Response<GeoLocationOverview>
}