package com.pscode.app.domain.remote

import com.pscode.app.data.model.geolocation.LocationDto
import com.pscode.app.utils.Response

interface GeolocationApi {
    suspend fun getGeolocationByCountry(countryName: String): Response<LocationDto>
}