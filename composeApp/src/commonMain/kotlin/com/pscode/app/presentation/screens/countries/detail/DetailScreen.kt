package com.pscode.app.presentation.screens.countries.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.presentation.screens.shared.ErrorEvent
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.koinInject

class DetailScreen(val onShowSnackBar: (String) -> Unit, private val selectedCountry: CountryOverview) : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinInject<DetailViewModel>()
        val weatherOverview by viewModel.cityWeather.collectAsState()
        val errorsChannel = viewModel.errorEventsChannelFlow

        LaunchedEffect(errorsChannel) {
            errorsChannel.collect { event ->
                when (event) {
                    is ErrorEvent.ShowSnackbarMessage -> {
                        onShowSnackBar(event.message)
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            selectedCountry.capitals.firstOrNull()?.let { capital ->
                viewModel.getWeatherByCity(cityName = capital)
            }
        }


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = selectedCountry.name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            KamelImage(
                resource = asyncPainterResource(selectedCountry.flagUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 200.dp, height = 120.dp)
            )

            OutlinedCard(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(
                    text = "Capitals: ${selectedCountry.capitals}",
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "Area: ${selectedCountry.area} km²", modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "Population: ${selectedCountry.population}",
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "Timezones: ${selectedCountry.timezones}",
                    modifier = Modifier.padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = SharedRes.string.weather,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )


            weatherOverview?.let { weatherInCapital ->

                val sunriseTime =
                    convertTimestampToHourMinute(weatherInCapital.sunriseTimestamp.toLong())
                val sunsetTime =
                    convertTimestampToHourMinute(weatherInCapital.sunsetTimestamp.toLong())

                ElevatedCard(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(
                        text = "Temperature: ${weatherInCapital.currentTemperature}℃",
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = "Max Temperature: ${weatherInCapital.maxTemp}℃",
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = "Min Temperature: ${weatherInCapital.minTemp}℃",
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = "Cloud Cover Percentage: ${weatherInCapital.cloudCoverPercent}%",
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = "Humidity: ${weatherInCapital.humidity}%",
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(text = "Sunrise: $sunriseTime", modifier = Modifier.padding(4.dp))
                    Text(text = "Sunset: $sunsetTime", modifier = Modifier.padding(4.dp))
                }
            }
        }
    }

    private fun convertTimestampToHourMinute(timestamp: Long): String {
        val instant = Instant.fromEpochSeconds(timestamp)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.hour.toString().padStart(2, '0')}:${
            localDateTime.minute.toString().padStart(2, '0')
        }"
    }
}