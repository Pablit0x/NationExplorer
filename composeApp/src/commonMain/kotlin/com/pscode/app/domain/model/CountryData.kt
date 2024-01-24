package com.pscode.app.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable


@Immutable
@Serializable
data class CountryData(
    val name: String,
    val flagUrl: String,
    val capitals: List<String>,
    val population: Int,
    val area: Double,
    val continents: List<String>,
    val timezones: List<String>,
    val languages: List<String>,
    val currency: List<String>,
    val startOfWeek: String,
    val isFavourite: Boolean = false
)

data class CountriesData(
    val data: List<CountryData> = emptyList()
)
