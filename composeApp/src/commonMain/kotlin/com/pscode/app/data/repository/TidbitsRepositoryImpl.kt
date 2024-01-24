package com.pscode.app.data.repository

import com.pscode.app.data.model.tidbits.toListOfTidbitsOverview
import com.pscode.app.domain.model.TidbitData
import com.pscode.app.domain.remote.TidbitsApi
import com.pscode.app.domain.repository.TidbitsRepository
import com.pscode.app.utils.Response

class TidbitsRepositoryImpl(private val tidbitsApi: TidbitsApi) : TidbitsRepository {

    override suspend fun getTidbitsByCountryName(countryName: String): Response<List<TidbitData>> {

        val result =
            tidbitsApi.getTidbitsByCountryName(countryName = countryName.filterNot { it.isWhitespace() })

        return when (result) {
            is Response.Success -> {
                Response.Success(data = result.data.toListOfTidbitsOverview())
            }

            is Response.Error -> {
                Response.Error(message = result.message)
            }
        }
    }
}