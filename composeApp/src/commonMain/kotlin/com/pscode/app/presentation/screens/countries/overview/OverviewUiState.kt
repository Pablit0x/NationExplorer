package com.pscode.app.presentation.screens.countries.overview

import com.pscode.app.domain.model.Country

data class OverviewUiState(
    val isLoading : Boolean = false,
    val countries : List<Country> = emptyList()
)


