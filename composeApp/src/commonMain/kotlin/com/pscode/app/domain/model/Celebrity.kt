package com.pscode.app.domain.model

data class Celebrity(
    val id: Int,
    val countryName: String,
    val name: String,
    val description: String,
    val imageUrl: String
)

data class CelebrityData(
    val data: List<Celebrity> = emptyList()
)
