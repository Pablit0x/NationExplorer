package com.pscode.app.data.model

import com.pscode.app.domain.model.CountryOverview
import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    val area: Double,
    val capital: List<String> = emptyList(),
    val cca2: String,
    val continents: List<String>,
    val flags: Flags,
    val name: Name,
    val population: Int,
    val timezones: List<String>,
)

fun CountryDto.toCountry(): CountryOverview {
    return CountryOverview(
        name = this.name.common,
        flagUrl = this.flags.png,
        capitals = this.capital,
        population = this.population,
        area = this.area,
        timezones = this.timezones
    )
}
