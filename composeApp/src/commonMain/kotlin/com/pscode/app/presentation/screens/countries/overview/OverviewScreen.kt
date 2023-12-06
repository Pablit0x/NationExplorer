package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.presentation.composables.AlphabeticalScroller
import com.pscode.app.presentation.composables.CountryListItem
import com.pscode.app.presentation.composables.LetterHeader
import com.pscode.app.presentation.screens.countries.detail.DetailScreen
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class OverviewScreen(val onShowSnackBar: (String) -> Unit) : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val countryRepository = koinInject<CountryRepository>()
        val viewModel = getViewModel(
            Unit,
            viewModelFactory { OverviewViewModel(countryRepository = countryRepository) })
        val isLoading by viewModel.isLoading.collectAsState()
        val isSearching by viewModel.isSearching.collectAsState()
        val countries by viewModel.countries.collectAsState()
        val searchText by viewModel.searchText.collectAsState()

        val groupedCountries = countries.groupBy { it.name.first() }
        val errorsChannel = viewModel.errorEventsChannelFlow

        val navigator = LocalNavigator.currentOrThrow

        val scope = rememberCoroutineScope()
        val lazyListState = rememberLazyListState()
        val showSearch = lazyListState.isScrollingUp()

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

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                AnimatedVisibility(visible = showSearch) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { viewModel.onSearchTextChange(it) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        },
                        trailingIcon = {
                            if (searchText.isNotEmpty()) {
                                IconButton(onClick = { viewModel.onSearchTextChange("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Close, contentDescription = null
                                    )
                                }
                            }
                        },
                        placeholder = {
                            Text(text = SharedRes.string.search)
                        },
                        shape = RoundedCornerShape(percent = 30),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (isSearching) {
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
                                        modifier = Modifier.fillMaxWidth()
                                            .background(color = MaterialTheme.colorScheme.surface)
                                            .padding(vertical = 4.dp, horizontal = 8.dp)
                                    )
                                }

                                items(items = countries) { country ->
                                    CountryListItem(
                                        countryOverview = country,
                                        onCountryClick = { selectedCountry ->
                                            navigator.push(item = DetailScreen(selectedCountry = selectedCountry))
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
                                    lazyListState.animateScrollToItem(index)
                                }
                            }, modifier = Modifier.fillMaxHeight().fillMaxWidth()
                        )
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

    @Composable
    private fun LazyListState.isScrollingUp(): Boolean {
        var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
        var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
        return remember(this) {
            derivedStateOf {
                if (previousIndex != firstVisibleItemIndex) {
                    previousIndex > firstVisibleItemIndex
                } else {
                    previousScrollOffset >= firstVisibleItemScrollOffset
                }.also {
                    previousIndex = firstVisibleItemIndex
                    previousScrollOffset = firstVisibleItemScrollOffset
                }
            }
        }.value
    }

}