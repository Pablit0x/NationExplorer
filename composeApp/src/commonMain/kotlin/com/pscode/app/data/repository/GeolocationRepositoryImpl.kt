package com.pscode.app.data.repository

import com.pscode.app.data.model.geolocation.toLocationOverview
import com.pscode.app.domain.model.LocationOverview
import com.pscode.app.domain.remote.GeolocationApi
import com.pscode.app.domain.repository.GeolocationRepository
import com.pscode.app.utils.Response
import io.github.xxfast.kstore.KStore

class GeolocationRepositoryImpl(
    private val geolocationApi: GeolocationApi,
    private val geolocationCache: KStore<List<LocationOverview>>
) : GeolocationRepository {
    override suspend fun getGeolocationByCountry(countryName: String): Response<LocationOverview> {

        val geolocationCacheList = geolocationCache.get()
        val cachedLocationOverview = geolocationCacheList?.find { it.name == countryName }

        if (cachedLocationOverview != null) {
            return Response.Success(cachedLocationOverview)
        }

        val result = geolocationApi.getGeolocationByCountry(countryName = countryName)

        return when (result) {
            is Response.Success -> {
                if (result.data.results.isEmpty()) {
                    Response.Error(message = "No results")
                } else {
                    val locationOverview = result.data.toLocationOverview(countryName = countryName)
                    if (geolocationCacheList != null) {
                        geolocationCache.set(geolocationCacheList + locationOverview)
                    } else {
                        geolocationCache.set(listOf(locationOverview))
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