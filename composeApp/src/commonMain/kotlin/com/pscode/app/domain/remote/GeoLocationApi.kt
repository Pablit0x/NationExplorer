package com.pscode.app.domain.remote

import com.pscode.app.data.model.geo_location.GeoLocationDto
import com.pscode.app.utils.Response

interface GeoLocationApi {
    suspend fun getGeoLocationByCountry(cityName: String, countryName : String) : Response<List<GeoLocationDto>>
}