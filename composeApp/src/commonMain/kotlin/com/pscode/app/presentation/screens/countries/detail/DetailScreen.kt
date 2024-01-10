package com.pscode.app.presentation.screens.countries.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.pscode.app.presentation.composables.TidbitCard
import com.pscode.app.presentation.composables.WeatherCard
import com.pscode.app.presentation.composables.navigateBackOnDrag
import com.pscode.app.presentation.screens.shared.Event
import com.pscode.app.utils.Status
import org.koin.compose.koinInject

class DetailScreen(
    val onShowSnackBar: (String) -> Unit, private val selectedCountry: CountryOverview
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = koinInject<DetailViewModel>()
        val currentWeather by viewModel.currentWeather.collectAsState()
        val isMapVisible by viewModel.isMapVisible.collectAsState()
        val countryGeolocation by viewModel.countryGeolocation.collectAsState()
        val tidbitsList by viewModel.tidbitsList.collectAsState()
        val currentTidbitId by viewModel.currentTidbitId.collectAsState()
        val eventsChannel = viewModel.eventsChannel

        val hasCapital = selectedCountry.capitals.isNotEmpty()
        val navigator = LocalNavigator.currentOrThrow
        val networkStatus by viewModel.connectivityStatus.collectAsState()
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val fetchFailure by viewModel.didFetchFail.collectAsState()
        val scrollState = rememberScrollState()

        LaunchedEffect(eventsChannel) {
            eventsChannel.collect { event ->
                when (event) {
                    is Event.ShowSnackbarMessage -> {
                        onShowSnackBar(event.message)
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.getGeolocationByCountry(countryName = selectedCountry.name)
            viewModel.getWeatherByCity(country = selectedCountry)
            viewModel.getTidbitsByCountry(countryName = selectedCountry.name)
        }

        LaunchedEffect(networkStatus) {
            if (fetchFailure) {
                when (networkStatus) {
                    Status.Available -> {
                        viewModel.getGeolocationByCountry(countryName = selectedCountry.name)
                        viewModel.getWeatherByCity(country = selectedCountry)
                    }

                    else -> {}
                }
            }
        }


        DisposableEffect(Unit) {
            onDispose {
                viewModel.resetViewModel()
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp).verticalScroll(state = scrollState)
                .navigateBackOnDrag(onNavigateBack = { navigator.pop() }),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DetailCountryOverview(
                selectedCountry = selectedCountry,
                hasCapitalCity = hasCapital,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            if (tidbitsList.isNotEmpty()) {
                TidbitCard(
                    currentTidbitId = currentTidbitId,
                    tidbits = tidbitsList,
                    setCurrentTidbitId = { viewModel.setCurrentTidbitId(it) },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
            }

            if (hasCapital) {

                AutoResizedText(
                    text = "${SharedRes.string.weather_in} ${selectedCountry.capitals.first()}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                WeatherCard(
                    weatherInCapital = currentWeather, modifier = Modifier.fillMaxWidth()
                )
            }

            AnimatedVisibility(
                visible = countryGeolocation != null, enter = fadeIn(), exit = fadeOut()
            ) {
                ElevatedButton(
                    onClick = viewModel::showMap, modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Icon(imageVector = Icons.Default.Map, contentDescription = "Show on Map")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = SharedRes.string.show_map,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            if (isMapVisible) {
                ModalBottomSheet(
                    sheetState = bottomSheetState,
                    onDismissRequest = { viewModel.hideMap() }) {
                    countryGeolocation?.let {
                        MapView(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.65f),
                            countryArea = selectedCountry.area,
                            locationOverview = it
                        )
                    }
                }
            }

        }


    }
}