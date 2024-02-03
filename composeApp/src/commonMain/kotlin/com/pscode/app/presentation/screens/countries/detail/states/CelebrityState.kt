package com.pscode.app.presentation.screens.countries.detail.states

import com.pscode.app.domain.model.CelebrityData

data class CelebrityState(
    val celebrityData: CelebrityData = CelebrityData(),
    val cardState: CardState = CardState.COLLAPSED,
    val errorMessage: String? = null
)
