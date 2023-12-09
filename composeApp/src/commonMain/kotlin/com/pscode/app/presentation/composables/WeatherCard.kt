package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.pscode.app.domain.model.WeatherOverview
import com.pscode.app.images.SharedResImages
import com.pscode.app.strings.SharedResStrings
import io.github.skeptick.libres.compose.painterResource

data class WeatherItemData(
    val icon: Painter, val contentDescription: String, val label: String, val value: String
)

@Composable
fun WeatherCard(weatherInCapital: WeatherOverview?, modifier: Modifier = Modifier) {

    val humidityIcon = painterResource(image = SharedResImages.humidity)
    val sunriseIcon = painterResource(image = SharedResImages.sunrise)
    val sunsetIcon = painterResource(image = SharedResImages.sunset)
    val windSpeedIcon = painterResource(image = SharedResImages.wind_speed)
    val temperatureIcon = painterResource(image = SharedResImages.temperature)
    val cloudsIcon = painterResource(image = SharedResImages.clouds)

    var listOfWeatherItems by remember { mutableStateOf<List<WeatherItemData>>(emptyList()) }

    LaunchedEffect(weatherInCapital) {
        if (weatherInCapital == null) {
            listOfWeatherItems = emptyList()
        } else {
            listOfWeatherItems = listOf(
                WeatherItemData(
                    icon = temperatureIcon,
                    contentDescription = "Temperature",
                    label = SharedResStrings.temperature,
                    value = weatherInCapital.currentTemperature
                ),
                WeatherItemData(
                    icon = cloudsIcon,
                    contentDescription = "Cloudiness",
                    label = SharedResStrings.cloudiness,
                    value = weatherInCapital.cloudCoverPercent
                ),
                WeatherItemData(
                    icon = humidityIcon,
                    contentDescription = "Humidity",
                    label = SharedResStrings.humidity,
                    value = weatherInCapital.humidity
                ),
                WeatherItemData(
                    icon = windSpeedIcon,
                    contentDescription = "Wind Speed",
                    label = SharedResStrings.wind_speed,
                    value = weatherInCapital.windSpeed
                ),
                WeatherItemData(
                    icon = sunriseIcon,
                    contentDescription = "Sunrise",
                    label = SharedResStrings.sunrise,
                    value = weatherInCapital.sunriseTime
                ),
                WeatherItemData(
                    icon = sunsetIcon,
                    contentDescription = "Sunset",
                    label = SharedResStrings.sunset,
                    value = weatherInCapital.sunsetTime
                ),
            )
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (listOfWeatherItems.isEmpty()) items(count = 6) { WeatherItemShimmer() }
        else {
            items(items = listOfWeatherItems) { weatherItem ->
                WeatherItem(
                    iconPainter = weatherItem.icon,
                    contentDescription = weatherItem.contentDescription,
                    label = weatherItem.label,
                    value = weatherItem.value
                )
            }
        }
    }
}
