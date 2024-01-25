package com.pscode.app.presentation.screens.countries.detail.states

import com.pscode.app.domain.model.YoutubeVideoData

data class YoutubeVideoState(
    var youtubeVideoData: YoutubeVideoData? = null,
    val cardState: CardState = CardState.COLLAPSED,
)
