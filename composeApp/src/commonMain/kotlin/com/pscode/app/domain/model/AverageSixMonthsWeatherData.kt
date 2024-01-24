package com.pscode.app.domain.model

data class SixMonthsWeatherData(
    val monthAverages: List<MonthlyAverage> = emptyList()
)

data class MonthlyAverage(
    val month: String,
    val averageValue: Double
)
