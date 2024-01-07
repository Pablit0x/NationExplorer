package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.presentation.composables.AlphabeticalScroller
import com.pscode.app.presentation.composables.AutoResizedText
import com.pscode.app.presentation.composables.CountryListItem
import com.pscode.app.presentation.composables.LetterHeader
import com.pscode.app.presentation.screens.countries.detail.DetailScreen
import com.pscode.app.presentation.screens.shared.ErrorEvent
import kotlinx.coroutines.launch

class OverviewScreen(
    val onShowSnackBar: (String) -> Unit,
    private val viewModel: OverviewViewModel,
    private val lazyListState: LazyListState
) : Screen {

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val isLoading by viewModel.isLoading.collectAsState()
        val isSearching by viewModel.isSearching.collectAsState()
        val filterWidgetState by viewModel.filterWidgetState.collectAsState()
        val countries by viewModel.countries.collectAsState()
        val filterItems by viewModel.filterItems.collectAsState()

        val groupedCountries = countries.groupBy { it.name.first() }
        val errorsChannel = viewModel.errorEventsChannelFlow

        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()

        val sheetState = rememberModalBottomSheetState()


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
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (isLoading || isSearching) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxHeight().fillMaxWidth(0.9f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

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
                                    countryOverview = country, onCountryClick = { selectedCountry ->
                                        viewModel.setSelectedCountryName(countryName = selectedCountry.name)
                                        navigator.push(
                                            item = DetailScreen(
                                                onShowSnackBar = { errorMsg ->
                                                    onShowSnackBar(errorMsg)
                                                }, selectedCountry = selectedCountry
                                            )
                                        )
                                    }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                                )
                            }
                        }
                    }

                    AlphabeticalScroller(
                        onLetterClick = { clickedLetter ->
                            val index = getScrollIndex(groupedCountries, clickedLetter)
                            scope.launch {
                                lazyListState.animateScrollToItem(index)
                            }
                        }, modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(top = 40.dp)
                    )
                }

                if (filterWidgetState == FilterWidgetState.OPEN) {
                    ModalBottomSheet(sheetState = sheetState,
                        onDismissRequest = { viewModel.onFilterWidgetStateChange(newState = FilterWidgetState.CLOSED) }) {

                        AutoResizedText(
                            text = SharedRes.string.filter_results,
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(count = 3),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalItemSpacing = 4.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(filterItems) { continent ->
                                ElevatedFilterChip(selected = continent.isSelected,
                                    onClick = { viewModel.updateFilterItemSelected(continent.label) },
                                    label = {
                                        AutoResizedText(
                                            text = continent.label,
                                            style = MaterialTheme.typography.labelMedium,
                                            modifier = Modifier.padding(4.dp),
                                        )
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getScrollIndex(
    groupedCountries: Map<Char, List<CountryOverview>>, letter: Char
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
