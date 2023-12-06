package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.pscode.app.domain.model.Country
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.presentation.composables.AlphabeticalScroller
import com.pscode.app.presentation.composables.CountryListItem
import com.pscode.app.presentation.composables.LetterHeader
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class OverviewScreen(val onShowSnackBar: (String) -> Unit) : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val countryRepository = koinInject<CountryRepository>()
        val viewModel = getViewModel(Unit,
            viewModelFactory { OverviewViewModel(countryRepository = countryRepository) })
        val state by viewModel.state.collectAsState()
        val groupedCountries = state.countries.groupBy { it.name.first() }
        val errorsChannel = viewModel.errorEventsChannelFlow
        val scope = rememberCoroutineScope()
        val lazyListState = rememberLazyListState()

        LaunchedEffect(errorsChannel) {
            errorsChannel.collect { event ->
                when (event) {
                    is ErrorEvent.ShowSnackbarMessage -> {
                        onShowSnackBar(event.message)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
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
                            modifier = Modifier.fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.surface)
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }

                    items(items = countries) { country ->
                        CountryListItem(
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 4.dp),
                            countryName = country.name,
                            flagUrl = country.flagUrl
                        )
                    }
                }
            }

            AlphabeticalScroller(
                onLetterClick = {clickedLetter ->
                    val index = getScrollIndex(groupedCountries, clickedLetter)
                    scope.launch {
                        lazyListState.animateScrollToItem(index)
                    }
                }, modifier = Modifier.fillMaxHeight().fillMaxWidth()
            )
        }
    }

    private fun getScrollIndex(groupedCountries: Map<Char, List<Country>>, letter: Char): Int {
        var index = 0
        for ((key, value) in groupedCountries) {
            if (key == letter) {
                break
            }
            index += 1 + value.size
        }
        return index
    }

}