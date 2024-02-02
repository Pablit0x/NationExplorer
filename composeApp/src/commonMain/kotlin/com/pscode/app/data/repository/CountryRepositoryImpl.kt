package com.pscode.app.data.repository

import com.pscode.app.domain.model.CountryData
import com.pscode.app.domain.remote.CountryApi
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.utils.Response
import io.github.xxfast.kstore.KStore

class CountryRepositoryImpl(
    private val countryApi: CountryApi, private val countryDataCache: KStore<List<CountryData>>
) : CountryRepository {
    override suspend fun getAllCountries(): Response<List<CountryData>> {
        val cachedCountries = countryDataCache.get()

        if (cachedCountries != null) {
            return Response.Success(data = cachedCountries)
        }

        val countryListResponse = countryApi.getAllCountries()

        if (countryListResponse is Response.Success) countryDataCache.set(countryListResponse.data)

        return countryListResponse
    }

    override suspend fun toggleFavourites(country: String): Response<List<CountryData>> {

        val cachedCountries =
            countryDataCache.get() ?: return Response.Error("Country cache is null")

        val updatedList = cachedCountries.let { countries ->
            countries.map { cachedCountry ->
                if (cachedCountry.name == country) {
                    cachedCountry.copy(
                        isFavourite = !cachedCountry.isFavourite
                    )
                } else {
                    cachedCountry
                }
            }
        }

        countryDataCache.update { updatedList }

        return Response.Success(data = updatedList)
    }

    override suspend fun getCountryByName(countryName: String): Response<CountryData> {

        val cachedCountries =
            countryDataCache.get() ?: return Response.Error("Country cache is null")

        return Response.Success(data = cachedCountries.first { it.name == countryName })

    }

}