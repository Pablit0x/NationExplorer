package com.pscode.app.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.WaterDrop

object Constants {
    const val SCORE_KEY = "score"
    const val TIME_KEY = "time"
    const val USERNAME_KEY = "username"
    const val APP_ID = "application-0-mmsnz"
    const val NUMBER_OF_ROUNDS = 10
    const val NUMBER_OF_TIDBITS = 3
    const val DEFAULT_TIME = "00:00:000"
    const val DELAY_MILLIS = 50L
    val Continents = listOf(
        "Asia",
        "Africa",
        "Europe",
        "North America",
        "South America",
        "Oceania",
        "Antarctica"
    )
    val Populations = listOf(500_000L, 10_000_000L, 50_000_000L, 250_000_000L)

    val chartSelection = mapOf(
        "Temperature" to Icons.Default.DeviceThermostat,
        "Wind Speed" to Icons.Default.Air,
        "Day Light" to Icons.Default.LightMode,
        "Rain" to Icons.Default.WaterDrop
    )

}