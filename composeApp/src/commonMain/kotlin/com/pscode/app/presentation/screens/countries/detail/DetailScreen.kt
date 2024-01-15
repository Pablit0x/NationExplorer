package com.pscode.app.presentation.screens.countries.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.presentation.composables.DetailedCountryOverviewCard
import com.pscode.app.presentation.composables.MapBottomSheet
import com.pscode.app.presentation.composables.ShowMapButton
import com.pscode.app.presentation.composables.TidbitCard
import com.pscode.app.presentation.composables.WeatherCard
import com.pscode.app.presentation.composables.navigateBackOnDrag
import com.pscode.app.presentation.screens.shared.Event
import com.pscode.app.utils.Status
import org.koin.compose.koinInject

class DetailScreen(private val selectedCountry: CountryOverview) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = koinInject<DetailViewModel>()

        val currentWeather by viewModel.currentWeather.collectAsState()
        val countryGeolocation by viewModel.countryGeolocation.collectAsState()
        val tidbitsList by viewModel.tidbitsList.collectAsState()
        val celebritiesList by viewModel.celebritiesList.collectAsState()
        val currentTidbitId by viewModel.currentTidbitId.collectAsState()
        val hasCapital = selectedCountry.capitals.isNotEmpty()
        val isMapVisible by viewModel.isMapVisible.collectAsState()
        val isCountryFavourite by viewModel.isCountryFavourite.collectAsState()
        val fetchFailure by viewModel.didFetchFail.collectAsState()
        val networkStatus by viewModel.connectivityStatus.collectAsState()

        val displayShowMapButton by remember { derivedStateOf { countryGeolocation != null && networkStatus == Status.Available } }

        val navigator = LocalNavigator.currentOrThrow
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        val eventsChannel = viewModel.eventsChannel

        val scrollState = rememberScrollState()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val snackBarHostState = remember { SnackbarHostState() }


        LaunchedEffect(eventsChannel) {
            eventsChannel.collect { event ->
                when (event) {
                    is Event.ShowSnackbarMessage -> {
                        snackBarHostState.showSnackbar(message = event.message)
                    }

                    is Event.ShowSnackbarMessageWithAction -> {
                        val result = snackBarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.actionLabel,
                            duration = SnackbarDuration.Short
                        )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                event.action()
                            }

                            SnackbarResult.Dismissed -> {}
                        }

                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.getGeolocationByCountry(countryName = selectedCountry.name)
            viewModel.getWeatherByCity(country = selectedCountry)
            viewModel.getTidbitsByCountry(countryName = selectedCountry.name)
            viewModel.getCelebritiesByCountry(countryName = selectedCountry.name)
            viewModel.setFavouriteStatus(isFavourite = selectedCountry.isFavourite)
        }

        LaunchedEffect(networkStatus) {
            when (networkStatus) {
                Status.Available -> {
                    if (fetchFailure) {
                        viewModel.getGeolocationByCountry(countryName = selectedCountry.name)
                        viewModel.getWeatherByCity(country = selectedCountry)
                        viewModel.getTidbitsByCountry(countryName = selectedCountry.name)
                        viewModel.getCelebritiesByCountry(countryName = selectedCountry.name)
                    }
                }

                Status.Unavailable -> {
                    snackBarHostState.showSnackbar(
                        SharedRes.string.no_internet, withDismissAction = true
                    )
                }

                else -> {}
            }
        }


        DisposableEffect(Unit) {
            onDispose {
                viewModel.resetViewModel()
            }
        }

        Scaffold(
            topBar = {
                DetailScreenTopBar(
                    navigator = navigator,
                    onToggleFavourite = { viewModel.toggleCountryFavourite(country = selectedCountry) },
                    isCountryFavourite = isCountryFavourite,
                    title = selectedCountry.name,
                    scrollBehavior = scrollBehavior
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).padding(12.dp).fillMaxSize()
                    .verticalScroll(state = scrollState)
                    .navigateBackOnDrag(onNavigateBack = { navigator.pop() }),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

//                if (celebritiesList.isNotEmpty()) {
//                    KamelImage(
//                        resource = asyncPainterResource(celebritiesList.first().imageUrl),
//                        contentDescription = null
//                    )
//                }

                DetailedCountryOverviewCard(
                    selectedCountry = selectedCountry,
                    hasCapitalCity = hasCapital,
                    modifier = Modifier.fillMaxWidth()
                )

                TidbitCard(
                    currentTidbitId = currentTidbitId,
                    tidbits = tidbitsList,
                    setCurrentTidbitId = { viewModel.setCurrentTidbitId(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                if (hasCapital) {
                    WeatherCard(
                        capitalName = selectedCountry.capitals.first(),
                        weatherInCapital = currentWeather,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                ShowMapButton(visible = displayShowMapButton, onClick = viewModel::showMap)

                MapBottomSheet(
                    isMapVisible = isMapVisible,
                    bottomSheetState = bottomSheetState,
                    countryArea = selectedCountry.area,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.65f),
                    locationOverview = countryGeolocation,
                    hideMap = viewModel::hideMap
                )
            }
        }
    }
}