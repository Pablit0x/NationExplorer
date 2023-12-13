package com.pscode.app.data.repository

import com.pscode.app.data.model.geo_location.toLocationOverview
import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.domain.remote.GeoLocationApi
import com.pscode.app.domain.repository.GeoLocationRepository
import com.pscode.app.utils.Response

class GeoLocationRepositoryImpl(private val geoLocationApi: GeoLocationApi) :
    GeoLocationRepository {
    override suspend fun getGeoLocationByCountry(countryName: String): Response<LocationOverview> {
        val result = geoLocationApi.getGeoLocationByCountry(countryName = countryName)

        return when (result) {
            is Response.Success -> {
                if (result.data.results.isEmpty()) {
                    Response.Error(message = "No results")
                } else {
                    Response.Success(data = result.data.toLocationOverview())
                }
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }
}