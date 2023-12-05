package com.pscode.app.presentation.screens.countries.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.pscode.app.domain.repository.CountryRepository
import com.pscode.app.presentation.composables.CountryListItem
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.koin.compose.koinInject

class OverviewScreen(val onShowSnackBar: (String) -> Unit) : Screen {

    @Composable
    override fun Content() {
        val countryRepository = koinInject<CountryRepository>()
        val viewModel = getViewModel(
            Unit,
            viewModelFactory { OverviewViewModel(countryRepository = countryRepository) })
        val state by viewModel.state.collectAsState()
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

        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(items = state.countries) { country ->
                    CountryListItem(
                        countryName = country.name,
                        flagUrl = country.flagUrl
                    )
                }
            }
        }
    }
}