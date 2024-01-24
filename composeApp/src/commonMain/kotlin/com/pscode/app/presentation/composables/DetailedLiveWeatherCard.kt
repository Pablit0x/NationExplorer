package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pscode.app.domain.model.WeatherConditions
import com.pscode.app.domain.model.WeatherData
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun DetailedLiveWeatherCard(
    weatherData: WeatherData,
    weatherConditions: WeatherConditions,
    listOfWeatherItems: List<WeatherItemData>,
    modifier: Modifier = Modifier
) {
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

            KamelImage(
                resource = asyncPainterResource(weatherConditions.image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(100.dp).padding(12.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${weatherData.temperature}°C",
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                )

                Text(
                    text = weatherConditions.weatherDesc,
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
            items(items = listOfWeatherItems) { weatherItem ->
                WeatherItem(
                    iconVector = weatherItem.icon,
                    contentDescription = weatherItem.contentDescription,
                    label = weatherItem.label,
                    value = weatherItem.value,
                    unit = weatherItem.unit,
                    labelStyle = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    valueStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Light),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}


@Composable
fun DetailedLiveWeatherLoadingSkeletonCard(modifier: Modifier = Modifier) {
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

            Box(
                modifier = Modifier.size(100.dp).clip(shape = RoundedCornerShape(10))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.height(MaterialTheme.typography.headlineLarge.fontSize.value.dp)
                        .width(100.dp).clip(shape = RoundedCornerShape(10)).shimmerEffect()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.height(MaterialTheme.typography.labelMedium.fontSize.value.dp)
                        .width(80.dp).clip(shape = RoundedCornerShape(10)).shimmerEffect()
                )
            }

        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(count = 6) {
                WeatherItemShimmer(
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}