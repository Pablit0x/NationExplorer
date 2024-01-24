package com.pscode.app.domain.model

data class TidbitInfo(
    val id: Int,
    val countryName: String,
    val title: String,
    val description: String
)

data class TidbitData(
    val data: List<TidbitInfo> = emptyList()
)
