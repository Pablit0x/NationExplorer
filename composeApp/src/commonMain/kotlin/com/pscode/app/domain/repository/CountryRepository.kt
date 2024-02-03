package com.pscode.app.domain.repository

import com.pscode.app.domain.model.CountryData
import com.pscode.app.utils.Response

interface CountryRepository {
    suspend fun getAllCountries(): Response<List<CountryData>>
    suspend fun toggleFavourites(countryName: String): Response<List<CountryData>>

    suspend fun getCountryByName(countryName: String): Response<CountryData>
}