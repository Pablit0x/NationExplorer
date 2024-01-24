package com.pscode.app.domain.repository

import com.pscode.app.domain.model.TidbitData
import com.pscode.app.utils.Response

interface TidbitsRepository {

    suspend fun getTidbitsByCountryName(countryName: String): Response<List<TidbitData>>

}