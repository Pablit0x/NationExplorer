package com.pscode.app.domain.model

import com.pscode.app.data.model.video.YoutubeVideoDto

data class YoutubeVideoData(
    val country: String, val videoId: String
)

fun YoutubeVideoDto.toYoutubeVideoData(): YoutubeVideoData {
    return YoutubeVideoData(
        country = this.country, videoId = this.videoId
    )
}