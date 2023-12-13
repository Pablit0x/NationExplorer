package com.pscode.app.presentation.screens.countries.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.presentation.composables.AutoResizedText
import com.pscode.app.presentation.composables.DetailCountryOverview
import com.pscode.app.presentation.composables.MapView
import com.pscode.app.presentation.composables.WeatherCard
import com.pscode.app.presentation.composables.navigateBackOnDrag
import com.pscode.app.presentation.screens.shared.ErrorEvent
import org.koin.compose.koinInject

class DetailScreen(
    val onShowSnackBar: (String) -> Unit, private val selectedCountry: CountryOverview
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = koinInject<DetailViewModel>()
        val weatherOverview by viewModel.cityWeather.collectAsState()
        val showMap by viewModel.showMap.collectAsState()
        val geoLocation by viewModel.geoLocation.collectAsState()
        val errorsChannel = viewModel.errorEventsChannelFlow
        val hasCapitalCity = selectedCountry.capitals.isNotEmpty()
        val navigator = LocalNavigator.currentOrThrow

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

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
            viewModel.getGeoLocation(countryName = selectedCountry.name)
            selectedCountry.capitals.firstOrNull()?.let { capital ->
                viewModel.getWeatherByCity(cityName = capital)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(12.dp)
                .navigateBackOnDrag(onNavigateBack = { navigator.pop() })
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    DetailCountryOverview(
                        selectedCountry = selectedCountry,
                        hasCapitalCity = hasCapitalCity,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )

                    if (hasCapitalCity) {

                        AutoResizedText(
                            text = "${SharedRes.string.weather_in} ${selectedCountry.capitals.first()}",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 8.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        WeatherCard(
                            weatherInCapital = weatherOverview, modifier = Modifier.fillMaxWidth()
                        )

                        AnimatedVisibility(
                            visible = geoLocation != null, enter = fadeIn(), exit = fadeOut()
                        ) {

                            OutlinedButton(onClick = viewModel::showMap) {
                                Text(text = SharedRes.string.show_map)
                            }

                            if (showMap) {
                                ModalBottomSheet(sheetState = sheetState,
                                    onDismissRequest = { viewModel.hideMap() }) {
                                    MapView(
                                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.65f),
                                        countryArea = selectedCountry.area,
                                        locationOverview = geoLocation!!
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}