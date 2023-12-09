package com.pscode.app.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.pscode.app.images.SharedResImages
import io.github.skeptick.libres.compose.painterResource


@Composable
fun WeatherCard(modifier: Modifier = Modifier) {

    val humidityIcon = painterResource(image = SharedResImages.humidity)
    val sunriseIcon = painterResource(image = SharedResImages.sunrise)
    val sunsetIcon = painterResource(image = SharedResImages.sunset)
    val windSpeedIcon = painterResource(image = SharedResImages.wind_speed)

    AnimatedBorderCard(
        modifier = modifier,
        shape = RoundedCornerShape(percent = 15),
        borderWidth = 3.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = humidityIcon,
                contentDescription = "Humidity",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp).padding(16.dp)
            )


            Image(
                painter = sunriseIcon,
                contentDescription = "Sunrise",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp).padding(16.dp)
            )

            Image(
                painter = sunsetIcon,
                contentDescription = "Sunset",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp).padding(16.dp)
            )

            Image(
                painter = windSpeedIcon,
                contentDescription = "Wind speed",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp).padding(16.dp)
            )
        }
    }
}