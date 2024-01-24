package com.pscode.app.presentation.screens.countries.detail.states

import com.pscode.app.domain.model.TidbitData

data class TidbitState(
    val currentId: Int = 0,
    val tidbitData: TidbitData = TidbitData(),
    val cardState: CardState = CardState.COLLAPSED,
    val errorMessage: String? = null
)
