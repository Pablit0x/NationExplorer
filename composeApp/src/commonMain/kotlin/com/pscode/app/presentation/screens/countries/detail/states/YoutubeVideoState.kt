package com.pscode.app.presentation.screens.countries.detail.states

data class YoutubeVideoState(
    var videoId: String? = null,
    val cardState: CardState = CardState.COLLAPSED,
    val errorMessage: String? = null,
)
