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

    override suspend fun toggleFavourites(country: CountryOverview?): Response<List<CountryOverview>> {

        val cachedCountries =
            countryOverviewCache.get() ?: return Response.Error("Country cache is null")

        val updatedList = cachedCountries.let { countries ->
            countries.map { cachedCountry ->
                if (cachedCountry.name == country?.name) {
                    cachedCountry.copy(
                        isFavourite = !cachedCountry.isFavourite
                    )
                } else {
                    cachedCountry
                }
            }
        }

        countryOverviewCache.update { updatedList }

        return Response.Success(data = updatedList)
    }

}