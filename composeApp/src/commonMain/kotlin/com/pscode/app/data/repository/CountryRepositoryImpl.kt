package com.pscode.app.data.repository

import com.pscode.app.data.cache.CountryCache
import com.pscode.app.domain.model.Country
import com.pscode.app.domain.remote.CountryApi
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.utils.Response
import io.github.xxfast.kstore.KStore

class CountryRepositoryImpl(
    private val countryApi : CountryApi,
//    private val countryCache: KStore<List<Country>>
) : CountryRepository {
    override suspend fun getAllCountries(): Response<List<Country>> {
//        val cachedCountries = countryCache.get()

//        if(cachedCountries != null){
//            return Response.Success(data = cachedCountries)
//        }

        val countryListResponse = countryApi.getAllCountries()

//        if(countryListResponse is Response.Success) countryCache.set(countryListResponse.data)

        return countryListResponse
    }
}