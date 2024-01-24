package com.pscode.app.domain.repository

import com.pscode.app.domain.model.LocationData
import com.pscode.app.utils.Response

interface GeolocationRepository {
    suspend fun getGeolocationByCountry(countryName: String): Response<LocationData>
}