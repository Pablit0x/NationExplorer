package com.pscode.app.presentation.screens.countries.flag_game.game

import com.pscode.app.domain.model.CountryData

data class RoundData(
    val targetCountry: CountryData, val options: List<CountryData>
)
