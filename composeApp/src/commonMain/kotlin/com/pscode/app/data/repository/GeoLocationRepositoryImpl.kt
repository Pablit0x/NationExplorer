package com.pscode.app.data.repository

import com.pscode.app.data.model.geo_location.toLocationOverview
import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.domain.remote.GeoLocationApi
import com.pscode.app.domain.repository.GeoLocationRepository
import com.pscode.app.utils.Response
import io.github.xxfast.kstore.KStore

class GeoLocationRepositoryImpl(
    private val geoLocationApi: GeoLocationApi,
    private val geoLocationCache: KStore<List<LocationOverview>>
) : GeoLocationRepository {
    override suspend fun getGeoLocationByCountry(countryName: String): Response<LocationOverview> {

        val geoLocationCacheList = geoLocationCache.get()
        val cachedLocationOverview = geoLocationCacheList?.find { it.name == countryName }

        if (cachedLocationOverview != null) {
            return Response.Success(cachedLocationOverview)
        }

        val result = geoLocationApi.getGeoLocationByCountry(countryName = countryName)

        return when (result) {
            is Response.Success -> {
                if (result.data.results.isEmpty()) {
                    Response.Error(message = "No results")
                } else {
                    val locationOverview = result.data.toLocationOverview(countryName = countryName)
                    if (geoLocationCacheList != null) {
                        geoLocationCache.set(geoLocationCacheList + locationOverview)
                    } else {
                        geoLocationCache.set(listOf(locationOverview))
                    }
                    Response.Success(data = locationOverview)
                }
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }
}