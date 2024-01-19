package com.pscode.app.domain.model

data class SixMonthsWeatherOverview(
    val monthAverages: List<MonthlyAverage>
)

data class MonthlyAverage(
    val month: String,
    val averageTemperature: Double
)
