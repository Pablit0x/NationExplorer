package com.pscode.app.presentation.screens.countries.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import com.pscode.app.domain.repository.WeatherRepository
import com.pscode.app.presentation.screens.shared.ErrorEvent
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.compose.koinInject

class DetailScreen(val onShowSnackBar: (String) -> Unit, private val selectedCountry: CountryOverview) : Screen {
    @Composable
    override fun Content() {
        val weatherRepository = koinInject<WeatherRepository>()
        val viewModel = getViewModel(Unit, viewModelFactory { DetailViewModel(weatherRepository = weatherRepository) })
        val errorsChannel = viewModel.errorEventsChannelFlow
        val weatherOverview by viewModel.cityWeather.collectAsState()

        LaunchedEffect(errorsChannel) {
            errorsChannel.collect { event ->
                when (event) {
                    is ErrorEvent.ShowSnackbarMessage -> {
                        onShowSnackBar(event.message)
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            KamelImage(
                resource = asyncPainterResource(selectedCountry.flagUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 200.dp, height = 120.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = selectedCountry.name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )


            OutlinedCard(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(
                    text = "Capitals: ${selectedCountry.capitals}",
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Area: ${selectedCountry.area} km²", modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Population: ${selectedCountry.population}",
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Timezones: ${selectedCountry.timezones}",
                    modifier = Modifier.padding(8.dp)
                )
            }

            Text(text = SharedRes.string.weather, style = MaterialTheme.typography.headlineLarge)

            Button(onClick = {
                viewModel.getWeatherByCity(selectedCountry.capitals.first())
            }){
                Text(text = "Press me please!")
            }
            Text(text = weatherOverview.toString())

        }
    }
}