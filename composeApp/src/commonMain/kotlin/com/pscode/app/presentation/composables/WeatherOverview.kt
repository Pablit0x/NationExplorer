package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pscode.app.domain.model.WeatherOverview

@Composable
fun WeatherOverview(
    weatherInCapitalCity: WeatherOverview?,
    modifier: Modifier = Modifier
) {
    if (weatherInCapitalCity == null) {
        WeatherOverviewShimmerItem(
            modifier = modifier
                .height(220.dp)
                .clip(shape = RoundedCornerShape(percent = 5))
                .shimmerEffect()
        )
    } else {
        ElevatedCard(modifier = modifier) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Temperature: ${weatherInCapitalCity.currentTemperature}℃",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = "Max Temperature: ${weatherInCapitalCity.maxTemp}℃",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = "Min Temperature: ${weatherInCapitalCity.minTemp}℃",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = "Cloud Cover Percentage: ${weatherInCapitalCity.cloudCoverPercent}%",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = "Humidity: ${weatherInCapitalCity.humidity}%",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = "Sunrise: ${weatherInCapitalCity.sunriseTime}",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = "Sunset: ${weatherInCapitalCity.sunsetTime}",
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun WeatherOverviewShimmerItem(modifier: Modifier = Modifier) {
    Box(modifier = modifier)
}