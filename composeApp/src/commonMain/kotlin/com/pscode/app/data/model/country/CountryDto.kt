package com.pscode.app.data.model.country

import com.pscode.app.domain.model.CountryOverview
import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    val area: Double,
    val capital: List<String> = emptyList(),
    val cca2: String,
    val continents: List<String>,
    val currencies: Currencies = Currencies(),
    val languages: Languages = Languages(),
    val maps: Maps,
    val flags: Flags,
    val name: Name,
    val startOfWeek: String,
    val population: Int,
    val timezones: List<String>
)

fun CountryDto.toCountry(): CountryOverview {
    return CountryOverview(
        name = this.name.common,
        flagUrl = this.flags.png,
        capitals = this.capital,
        population = this.population,
        area = this.area,
        continents = this.continents,
        timezones = this.timezones,
        languages = this.languages.toNonEmptyList(),
        currency = this.currencies.toNonEmptyCurrencyNamesList(),
        startOfWeek = this.startOfWeek
    )
}
