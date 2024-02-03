package com.pscode.app.data.model.tidbits


import com.pscode.app.domain.model.TidbitData
import com.pscode.app.domain.model.TidbitInfo
import com.pscode.app.domain.model.YoutubeVideoData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TidbitsDto(
    @SerialName("name") val name: String,
    @SerialName("videoId") val videoId: String? = null,
    @SerialName("tidbits") val tidbits: List<Tidbit>
)

fun TidbitsDto.toTidbitData(): TidbitData {
    return TidbitData(data = this.tidbits.map { tidbit ->
        TidbitInfo(
            countryName = name,
            id = tidbit.id,
            title = tidbit.title,
            description = tidbit.description
        )
    })
}


fun TidbitsDto.toYoutubeVideoData(): YoutubeVideoData? {
    return if (this.videoId == null) {
        null
    } else {
        YoutubeVideoData(country = this.name, videoId = videoId)
    }
}
