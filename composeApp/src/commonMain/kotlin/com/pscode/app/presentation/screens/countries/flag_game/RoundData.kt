package com.pscode.app.presentation.screens.countries.flag_game

import com.pscode.app.domain.model.CountryOverview

data class RoundData(val targetCountry: CountryOverview, val options: List<CountryOverview>)
