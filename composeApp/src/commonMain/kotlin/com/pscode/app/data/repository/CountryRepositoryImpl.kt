package com.pscode.app.data.repository

import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.remote.CountryApi
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.utils.Response
import io.github.xxfast.kstore.KStore

class CountryRepositoryImpl(
    private val countryApi: CountryApi,
    private val countryOverviewCache: KStore<List<CountryOverview>>
) : CountryRepository {
    override suspend fun getAllCountries(): Response<List<CountryOverview>> {
        val cachedCountries = countryOverviewCache.get()

        if (cachedCountries != null) {
            return Response.Success(data = cachedCountries)
        }

        val countryListResponse = countryApi.getAllCountries()

        if (countryListResponse is Response.Success) countryOverviewCache.set(countryListResponse.data)

        return countryListResponse
    }
}