package com.pscode.app.presentation.screens.countries.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.pscode.app.presentation.composables.DetailCountryOverview
import com.pscode.app.presentation.composables.WeatherOverview
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
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            DetailCountryOverview(
                selectedCountry = selectedCountry, modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = SharedRes.string.weather,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary
            )

            WeatherOverview(
                weatherInCapitalCity = weatherOverview,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}