package com.pscode.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: String,
    val flagUrl: String
)
