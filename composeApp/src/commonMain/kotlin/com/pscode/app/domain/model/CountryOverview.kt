package com.pscode.app.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable


@Immutable
@Serializable
data class CountryOverview(
    val name: String,
    val flagUrl: String,
    val capitals: List<String>,
    val population: Int,
    val area: Double,
    val timezones: List<String>,
    val languages: List<String>,
    val currency: List<String>,
    val startOfWeek: String
)
