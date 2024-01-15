package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.WeatherOverview

data class WeatherItemData(
    val icon: ImageVector, val contentDescription: String, val label: String, val value: String
)

@Composable
fun WeatherCard(
    capitalName: String, weatherInCapital: WeatherOverview?, modifier: Modifier = Modifier
) {

    var listOfWeatherItems by remember { mutableStateOf<List<WeatherItemData>>(emptyList()) }

    LaunchedEffect(weatherInCapital) {
        if (weatherInCapital == null) {
            listOfWeatherItems = emptyList()
        } else {
            listOfWeatherItems = listOf(
                WeatherItemData(
                    icon = Icons.Default.Thermostat,
                    contentDescription = "Temperature",
                    label = SharedRes.string.temperature,
                    value = weatherInCapital.currentTemperature
                ),
                WeatherItemData(
                    icon = Icons.Default.WbCloudy,
                    contentDescription = "Cloudiness",
                    label = SharedRes.string.cloudiness,
                    value = weatherInCapital.cloudCoverPercent
                ),
                WeatherItemData(
                    icon = Icons.Default.WaterDrop,
                    contentDescription = "Humidity",
                    label = SharedRes.string.humidity,
                    value = weatherInCapital.humidity
                ),
                WeatherItemData(
                    icon = Icons.Default.Air,
                    contentDescription = "Wind Speed",
                    label = SharedRes.string.wind_speed,
                    value = weatherInCapital.windSpeed
                ),
                WeatherItemData(
                    icon = Icons.Default.WbSunny,
                    contentDescription = "Sunrise",
                    label = SharedRes.string.sunrise,
                    value = weatherInCapital.sunriseTime
                ),
                WeatherItemData(
                    icon = Icons.Default.WbTwilight,
                    contentDescription = "Sunset",
                    label = SharedRes.string.sunset,
                    value = weatherInCapital.sunsetTime
                ),
            )
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        AutoResizedText(
            text = SharedRes.string.weather_in.format(capitalName),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            modifier = modifier.height(180.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (listOfWeatherItems.isEmpty()) items(count = 6) { WeatherItemShimmer() }
            else {
                items(items = listOfWeatherItems) { weatherItem ->
                    WeatherItem(
                        iconVector = weatherItem.icon,
                        contentDescription = weatherItem.contentDescription,
                        label = weatherItem.label,
                        value = weatherItem.value
                    )
                }
            }
        }
    }
}
