package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pscode.app.domain.model.WeatherData
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun DetailedLiveWeatherCard(
    weatherData: WeatherData?,
    listOfWeatherItems: List<WeatherItemData>?,
    modifier: Modifier = Modifier
) {

    if (weatherData != null) {
        if (weatherData.weatherConditions != null) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            KamelImage(
                                resource = asyncPainterResource(weatherData.weatherConditions.image),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(100.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.height(100.dp).weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${weatherData.temperature}°C",
                                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            )

                            Text(
                                text = weatherData.weatherConditions.weatherDesc,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                    }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 2),
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (listOfWeatherItems == null) items(count = 6) {
                        WeatherItemShimmer(
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    else {
                        items(items = listOfWeatherItems) { weatherItem ->
                            WeatherItem(
                                iconVector = weatherItem.icon,
                                contentDescription = weatherItem.contentDescription,
                                label = weatherItem.label,
                                value = weatherItem.value,
                                unit = weatherItem.unit,
                                labelStyle = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                valueStyle = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}