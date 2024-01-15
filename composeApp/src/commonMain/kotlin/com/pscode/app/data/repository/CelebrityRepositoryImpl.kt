package com.pscode.app.data.repository

import com.pscode.app.data.model.celebrity.toListOfCelebrityOverview
import com.pscode.app.data.model.tidbits.toListOfTidbitsOverview
import com.pscode.app.domain.model.CelebrityOverview
import com.pscode.app.domain.remote.CelebrityApi
import com.pscode.app.domain.repository.CelebrityRepository
import com.pscode.app.utils.Response

class CelebrityRepositoryImpl(private val celebrityApi: CelebrityApi) : CelebrityRepository {
    override suspend fun getCelebritiesByCountryName(countryName: String): Response<List<CelebrityOverview>> {
        val result = celebrityApi.getCelebritiesByCountryName(countryName = countryName)

        return when (result) {
            is Response.Success -> {
                Response.Success(data = result.data.toListOfCelebrityOverview())
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }
}