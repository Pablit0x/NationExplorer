package com.pscode.app.domain.remote

import com.pscode.app.data.model.geo_location.LocationDto
import com.pscode.app.utils.Response

interface GeoLocationApi {
    suspend fun getGeoLocationByCountry(countryName: String): Response<LocationDto>
}