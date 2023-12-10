package com.pscode.app.presentation.screens.countries.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.presentation.composables.AutoResizedText
import com.pscode.app.presentation.composables.DetailCountryOverview
import com.pscode.app.presentation.composables.WeatherCard
import com.pscode.app.presentation.screens.shared.ErrorEvent
import org.koin.compose.koinInject

class DetailScreen(
    val onShowSnackBar: (String) -> Unit, private val selectedCountry: CountryOverview
) : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinInject<DetailViewModel>()
        val weatherOverview by viewModel.cityWeather.collectAsState()
        val errorsChannel = viewModel.errorEventsChannelFlow
        val hasCapitalCity = selectedCountry.capitals.isNotEmpty()

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

        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    DetailCountryOverview(
                        selectedCountry = selectedCountry,
                        hasCapitalCity = hasCapitalCity,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                    )

                    if (hasCapitalCity) {

                        AutoResizedText(
                            text = "${SharedRes.string.weather_in} ${selectedCountry.capitals.first()}",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 8.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        WeatherCard(
                            weatherInCapital = weatherOverview, modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}