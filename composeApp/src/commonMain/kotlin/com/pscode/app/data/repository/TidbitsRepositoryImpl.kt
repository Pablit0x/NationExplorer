package com.pscode.app.data.repository

import com.pscode.app.data.model.tidbits.toTidbitData
import com.pscode.app.data.model.tidbits.toYoutubeVideoData
import com.pscode.app.domain.model.TidbitData
import com.pscode.app.domain.model.YoutubeVideoData
import com.pscode.app.domain.remote.TidbitsApi
import com.pscode.app.domain.repository.TidbitsRepository
import com.pscode.app.utils.Response

class TidbitsRepositoryImpl(private val tidbitsApi: TidbitsApi) : TidbitsRepository {

    override suspend fun getTidbitsByCountryName(countryName: String): Response<Pair<TidbitData, YoutubeVideoData?>> {

        val result =
            tidbitsApi.getTidbitsByCountryName(countryName = countryName.filterNot { it.isWhitespace() })

        when (result) {
            is Response.Success -> {

                val tidbitData = result.data.toTidbitData()
                val youtubeVideoData = result.data.toYoutubeVideoData()

                return Response.Success(data = Pair(first = tidbitData, second = youtubeVideoData))
            }

            is Response.Error -> {
                return Response.Error(message = result.message)
            }
        }
    }
}