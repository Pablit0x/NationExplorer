package com.pscode.app.data.repository

import com.pscode.app.data.model.geo_location.toGeoLocationOverview
import com.pscode.app.domain.model.GeoLocationOverview
import com.pscode.app.domain.remote.GeoLocationApi
import com.pscode.app.domain.repository.GeoLocationRepository
import com.pscode.app.utils.Response

class GeoLocationRepositoryImpl(private val geoLocationApi: GeoLocationApi) :
    GeoLocationRepository {
    override suspend fun getGeoLocationByCountry(
        cityName: String, countryName: String
    ): Response<GeoLocationOverview> {
        val result =
            geoLocationApi.getGeoLocationByCountry(cityName = cityName, countryName = countryName)

        return when (result) {
            is Response.Success -> {
                val geoLocationItem = result.data.firstOrNull()
                if (geoLocationItem != null) {
                    Response.Success(data = geoLocationItem.toGeoLocationOverview())
                } else {
                    Response.Error(message = "No results")
                }
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }
}