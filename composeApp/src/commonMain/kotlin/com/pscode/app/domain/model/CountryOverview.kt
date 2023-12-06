package com.pscode.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CountryOverview(
    val name: String,
    val flagUrl: String,
    val capitals : List<String>,
    val population: Int,
    val area: Double,
    val timezones : List<String>
)
