package com.pscode.app.data.model.video


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YoutubeVideoDto(
    @SerialName("country")
    val country: String,
    @SerialName("videoId")
    val videoId: String
)