package com.pscode.app.domain.remote

import com.pscode.app.data.model.tidbits.TidbitsDto
import com.pscode.app.utils.Response

interface TidbitsApi {

    suspend fun getTidbitsByCountryName(countryName: String) : Response<TidbitsDto>
}