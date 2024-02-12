package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CountryData
import com.pscode.app.presentation.composables.AlphabeticalScroller
import com.pscode.app.presentation.composables.CountryListItem
import com.pscode.app.presentation.composables.FullScreenLoadingIndicator
import com.pscode.app.presentation.composables.LetterHeader
import com.pscode.app.presentation.composables.isScrollingUp
import com.pscode.app.presentation.screens.countries.detail.DetailScreen
import com.pscode.app.presentation.screens.countries.flag_game.game.FlagGameScreen
import com.pscode.app.presentation.screens.countries.overview.states.WidgetState
import com.pscode.app.presentation.screens.shared.Event
import com.pscode.app.presentation.theme.LocalWindowSize
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class OverviewScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = koinInject<OverviewViewModel>()

        val isLoading by viewModel.isLoading.collectAsState()
        val searchText by viewModel.searchText.collectAsState()

        val windowSize = LocalWindowSize.current


        val countries by viewModel.countries.collectAsState()
        val filterState by viewModel.filterState.collectAsState()
        val searchState by viewModel.searchState.collectAsState()

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val listState = rememberLazyListState()
        val snackBarHostState = remember { SnackbarHostState() }


        val groupedCountries = countries.groupBy { it.name.first() }
        val eventsChannel = viewModel.eventsChannel

        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        LaunchedEffect(Unit) {
            viewModel.getAllCountries()
        }


        LaunchedEffect(eventsChannel) {
            eventsChannel.collect { event ->
                when (event) {
                    is Event.ShowSnackbarMessage -> {
                        scope.launch {
                            snackBarHostState.showSnackbar(message = event.message)
                        }
                    }

                    else -> {}
                }
            }
        }

        Scaffold(
            topBar = {
                OverviewScreenMainTopBar(scrollBehavior = scrollBehavior,
                    searchWidgetState = searchState.widgetState,
                    searchTextState = searchText,
                    isFiltering = filterState.isFiltering,
                    onFilterClicked = { viewModel.updateFilterWidgetState(newState = WidgetState.OPEN) },
                    onCloseSearchClicked = { viewModel.updateSearchWidgetState(newState = WidgetState.CLOSED) },
                    onSearchTriggered = { viewModel.updateSearchWidgetState(newState = WidgetState.OPEN) },
                    onSearchTextChange = { updatedSearchText -> viewModel.updateSearchText(text = updatedSearchText) })
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        navigator.navigate(screen = FlagGameScreen())
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Games, contentDescription = "Play games"
                        )
                    },
                    text = {
                        Text(text = SharedRes.string.play_game)
                    },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    expanded = listState.isScrollingUp(),
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection)
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                if (isLoading || searchState.isSearching) {
                    FullScreenLoadingIndicator()
                } else {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxHeight().fillMaxWidth(0.9f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {

                            item {
                                Button(onClick = {} ){
                                    Text(text = windowSize.name)
                                }
                            }

                            groupedCountries.forEach { (letter, countries) ->
                                stickyHeader {
                                    LetterHeader(
                                        letter,
                                        modifier = Modifier.fillMaxWidth().height(40.dp)
                                            .background(color = MaterialTheme.colorScheme.surface)
                                            .padding(vertical = 4.dp, horizontal = 8.dp)
                                    )
                                }

                                items(items = countries, key = { it.name }) { country ->
                                    CountryListItem(
                                        countryData = country,
                                        onCountryClick = { selectedCountry ->
                                            navigator.navigate(
                                                screen = DetailScreen(
                                                    selectedCountryName = selectedCountry.name
                                                )
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        AlphabeticalScroller(
                            onLetterClick = { clickedLetter ->
                                val index = getScrollIndex(groupedCountries, clickedLetter)
                                scope.launch {
                                    listState.animateScrollToItem(index)
                                }
                            },
                            modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(top = 40.dp)
                        )
                    }

                    FilterBottomSheet(sheetState = sheetState,
                        filterState = filterState,
                        onUpdateFilterWidgetState = { updatedFilterWidgetState ->
                            viewModel.updateFilterWidgetState(updatedFilterWidgetState)
                        },
                        onUpdateContinentFilterItem = { continentItemLabel ->
                            viewModel.updateContinentFilterItem(
                                label = continentItemLabel
                            )
                        },
                        onUpdatePopulationFilterItem = { populationItemLabel ->
                            viewModel.updatePopulationFilterItem(
                                label = populationItemLabel
                            )
                        },
                        onToggleFavouriteOnly = { viewModel.toggleFavouriteOnly() },
                        onResetAllFilters = { viewModel.resetAllFilters() })
                }
            }
        }
    }
}

private fun getScrollIndex(
    groupedCountries: Map<Char, List<CountryData>>, letter: Char
): Int {
    var index = 0
    for ((key, value) in groupedCountries) {
        if (key == letter) {
            break
        }
        index += 1 + value.size
    }
    return index
}

private fun Navigator.navigate(screen: Screen) {
    if (this.lastItem is OverviewScreen) {
        this.push(item = screen)
    }
}
